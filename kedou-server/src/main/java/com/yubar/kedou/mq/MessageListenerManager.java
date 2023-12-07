package com.yubar.kedou.mq;
/**
 * Author:Yu-Bar
 * Date:2023/12/5-1:02
 */

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 *@ClassName MessageManager
 *@Description 消息监听器管理器
 *@Author Yu-Bar
 *@Date 2023/12/5 1:02
 *@Version 1.0
 **/
@Component
public class MessageListenerManager {
    @Autowired
    private SimpleMessageListenerContainer container;
    @Autowired
    private ChatMessageListener messageListener;

    public void addListener(String queueName) {
        //获取当前监听的队列名称
        String[] strings = container.getQueueNames();
        List<String> list = Arrays.asList(strings);
        if (!list.contains(queueName)) {
            container.addQueueNames(queueName);
            //设置消息监听处理类
            container.setMessageListener(messageListener);

        }
    }

    public void removeListener(String queueName) {
        //获取当前监听的队列名称
        String[] strings = container.getQueueNames();
        List<String> list = Arrays.asList(strings);
        if (list.contains(queueName)) {
            container.removeQueueNames(queueName);
        }
    }

}
