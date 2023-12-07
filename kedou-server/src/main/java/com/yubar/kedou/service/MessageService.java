package com.yubar.kedou.service;

import com.yubar.kedou.domain.dto.MessageDTO;
import com.yubar.kedou.domain.po.Message;
import com.yubar.kedou.domain.po.Session;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yubar.kedou.domain.vo.MessageVO;
import com.yubar.kedou.domain.vo.SessionVO;
import com.yubar.kedou.event.MessageReceiveEvent;

import java.util.List;

/**
* @author 兰豹基
* @description 针对表【message】的数据库操作Service
* @createDate 2023-12-04 20:39:33
*/
public interface MessageService extends IService<Message> {

    /**
     * 发送消息
     * @param messageDTO
     */
    MessageVO sendMessage(MessageDTO messageDTO);

    /**
     * 接收消息
     * @param messageReceiveEvent
     * @return
     */
    void receiveMessage(MessageReceiveEvent messageReceiveEvent);


    /**
     * 新建或者获取会话
     * @param userId
     * @return
     */
    SessionVO createOrGetSession(Long userId);

    /**
     * 新建会话
     * @param userId
     * @return
     */
    Session createNewSession(Long userId);

    /**
     * 查询当前用户的所有会话
     * @return
     */
    List<SessionVO> getAllSession();
}
