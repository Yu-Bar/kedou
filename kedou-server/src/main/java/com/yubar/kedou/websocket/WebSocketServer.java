package com.yubar.kedou.websocket;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.yubar.kedou.domain.dto.MessageMQDTO;
import com.yubar.kedou.domain.vo.MessageVO;
import com.yubar.kedou.event.MessageReceiveEvent;
import com.yubar.kedou.mq.MessageListenerManager;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * WebSocket服务
 */
@Component
@ServerEndpoint(value = "/ws/{sid}")
@Slf4j
public class WebSocketServer {

//    private static MessageService messageService;
    private static MessageListenerManager messageListenerManager;

    //存放会话对象
    private static Map<Long, Session> sessionMap = new HashMap();

//    @Autowired
//    public void setMessageService(MessageService messageService) {
//        WebSocketServer.messageService = messageService;
//    }

    @Autowired
    public void setMessageListenerManager(MessageListenerManager messageListenerManager) {
        WebSocketServer.messageListenerManager = messageListenerManager;
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") Long sid) {
        log.info("客户端：" + sid + "建立WebSocket连接");
        sessionMap.put(sid, session);
//        messageService.receiveMessage("user." + sid);
        // 新建消息监听器
        messageListenerManager.addListener("user."+sid);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") Long sid) {
        log.info("收到来自客户端：" + sid + "的WebSocket信息:" + message);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") Long sid) {
        log.info("WebSocket连接断开:" + sid);
        sessionMap.remove(sid);
        messageListenerManager.removeListener("user."+sid);
    }

    /**
     * 单发
     */
    @EventListener
    public void sendToClient(MessageReceiveEvent messageReceiveEvent){
        if(messageReceiveEvent.getSource() instanceof MessageMQDTO messageMQDTO){
            MessageVO messageVO = BeanUtil.copyProperties(messageMQDTO, MessageVO.class);
            Session session = sessionMap.get(messageMQDTO.getReceiver());
            try {
                session.getBasicRemote().sendText(JSON.toJSONString(messageVO));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        log.info("WebSocket群发消息{}",message);
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
