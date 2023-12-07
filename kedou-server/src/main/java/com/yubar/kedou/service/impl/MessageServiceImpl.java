package com.yubar.kedou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.context.BaseContext;
import com.yubar.kedou.domain.dto.MessageDTO;
import com.yubar.kedou.domain.dto.MessageMQDTO;
import com.yubar.kedou.domain.po.Message;
import com.yubar.kedou.domain.po.Session;
import com.yubar.kedou.domain.po.User;
import com.yubar.kedou.domain.vo.MessageVO;
import com.yubar.kedou.domain.vo.SessionVO;
import com.yubar.kedou.event.MessageReceiveEvent;
import com.yubar.kedou.exception.MessageException;
import com.yubar.kedou.mapper.SessionMapper;
import com.yubar.kedou.mapper.UserMapper;
import com.yubar.kedou.service.MessageService;
import com.yubar.kedou.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
* @author 兰豹基
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-12-04 20:39:33
*/
@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{
    @Autowired
    SessionMapper sessionMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserMapper userMapper;

    /**
     * 发送消息
     * @param messageDTO
     */
    @Override
    public MessageVO sendMessage(MessageDTO messageDTO) {
        if(BaseContext.getCurrentId().equals(messageDTO.getReceiver()))
            throw new MessageException(MessageConstant.MESSAGE_MYSELF);
        // 检查有无已经存在的会话
        MessageMQDTO message = new MessageMQDTO();
        if(messageDTO.getSession() != null){
            // 有则归入之前的会话
            message.setSession(messageDTO.getSession());
        }else{
            // 无则新建会话
            Session newSession = createNewSession(messageDTO.getReceiver());
            message.setSession(newSession.getId());
        }
        message.setCreateUser(BaseContext.getCurrentId());
        message.setReceiver(messageDTO.getReceiver());
        message.setContent(messageDTO.getContent());
        message.setVideoId(messageDTO.getVideoId());
        message.setCreateTime(LocalDateTime.now());
        // 将消息推送至消息队列
        rabbitTemplate.convertAndSend("kedou.message",messageDTO.getReceiver().toString(),message);
        return BeanUtil.copyProperties(message, MessageVO.class);
    }


    /**
     * 接收消息,存储至数据库，让用户登陆时可以获取历史聊天信息(即漫游支持,如果用户一直未读信息，就不会被漫游支持)
     * @param messageReceiveEvent
     * @return
     */
    @EventListener
    public void receiveMessage(MessageReceiveEvent messageReceiveEvent) {
        if(messageReceiveEvent.getSource() instanceof MessageMQDTO messageMQDTO){
            log.info("用户{}：收到消息:{},发送者:{}",messageMQDTO.getReceiver(),messageMQDTO.getContent(),messageMQDTO.getCreateUser());
            Message message = BeanUtil.copyProperties(messageMQDTO, Message.class);
            save(message);
        }
    }

    /**
     * 新建或者获取会话
     * @param userId
     * @return
     */
    @Override
    public SessionVO createOrGetSession(Long userId) {
        Session session = createNewSession(userId);
        SessionVO sessionVO = new SessionVO();
        sessionVO.setSession(session.getId());
        sessionVO.setUserId(userId);
        // 查询聊天用户相关信息
        User user = userMapper.selectById(userId);
        sessionVO.setNickname(user.getNickname());
        sessionVO.setProfile(user.getProfile());
        // 查询聊天记录
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<Message>()
                .eq(Message::getSession, session.getId());
        List<Message> messageList = list(wrapper);
        sessionVO.setMessageList(BeanUtil.copyToList(messageList, MessageVO.class));
        return sessionVO;
    }

    /**
     * 新建会话
     * @param userId
     * @return
     */
    @Override
    public Session createNewSession(Long userId) {
        Long userA = BaseContext.getCurrentId() < userId ?  BaseContext.getCurrentId() : userId;
        Long userB = BaseContext.getCurrentId() > userId ?  BaseContext.getCurrentId() : userId;
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<Session>()
                .eq(Session::getUserA, userA)
                .eq(Session::getUserB, userB);
        Session session = sessionMapper.selectOne(wrapper);
        if(session == null){
            session = new Session();
            session.setUserA(userA);
            session.setUserB(userB);
            sessionMapper.insetNewSession(session);
        }
        return session;
    }


    /**
     * 查询当前用户的所有会话
     * @return
     */
    @Override
    public List<SessionVO> getAllSession() {
        Long currentUserId = BaseContext.getCurrentId();
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<Session>()
                .eq(Session::getUserA, currentUserId)
                .or()
                .eq(Session::getUserB, currentUserId);
        List<Session> sessionList = sessionMapper.selectList(wrapper);
        List<SessionVO> sessionVOList = new ArrayList<>();
        for(Session session : sessionList){
            // 准备聊天用户信息
            Long userId = session.getUserA().equals(currentUserId) ? session.getUserB() : session.getUserA();
            User user = userMapper.selectById(userId);
            // 准备聊天信息
            LambdaQueryWrapper<Message> messageWrapper = new LambdaQueryWrapper<Message>()
                    .eq(Message::getSession, session.getId());
            List<Message> messageList = list(messageWrapper);
            if(messageList != null && !messageList.isEmpty()){
                // 构造 SessionVO
                SessionVO sessionVO = SessionVO.builder()
                        .session(session.getId())
                        .userId(user.getId())
                        .nickname(user.getNickname())
                        .profile(user.getProfile())
                        .messageList(BeanUtil.copyToList(messageList, MessageVO.class))
                        .build();
                sessionVOList.add(sessionVO);
            }
        }
        // 按最后发送时间排序
        sessionVOList.sort(new Comparator<SessionVO>() {
            @Override
            public int compare(SessionVO sessionVO1, SessionVO sessionVO2) {

                LocalDateTime time1 =  sessionVO1.getMessageList().get(sessionVO1.getMessageList().size()-1).getCreateTime();
                LocalDateTime time2 = sessionVO2.getMessageList().get(sessionVO2.getMessageList().size()-1).getCreateTime();

                // 降序排序，时间越晚的排在越前面
                return time2.compareTo(time1);
            }
        });
        return sessionVOList;
    }
}




