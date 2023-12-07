package com.yubar.kedou.mq;
/**
 * Author:Yu-Bar
 * Date:2023/12/5-0:59
 */


import com.alibaba.fastjson.JSONObject;
import com.yubar.kedou.domain.dto.MessageMQDTO;
import com.yubar.kedou.event.EventPublisher;
import com.yubar.kedou.event.MessageReceiveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *@ClassName ChatMessageListener
 *@Description 消息监听器
 *@Author Yu-Bar
 *@Date 2023/12/5 0:59
 *@Version 1.0
 **/
@Slf4j
@Component
public class ChatMessageListener implements MessageListener {
    @Autowired
    EventPublisher eventPublisher;

    @Override
    public void onMessageBatch(List<Message> messages) {
        MessageListener.super.onMessageBatch(messages);
    }

    @Override
    public void onMessage(Message message) {
        try {
            // 解析消息
            String mes = new String(message.getBody(), "utf-8");
            log.info("监听到消息:{}",mes);
            MessageMQDTO messageMQDTO = JSONObject.parseObject(mes, MessageMQDTO.class);
            // 发布收到消息事件
            MessageReceiveEvent messageReceiveEvent = new MessageReceiveEvent(messageMQDTO);
            eventPublisher.sendEvent(messageReceiveEvent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
