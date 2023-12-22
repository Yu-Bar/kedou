package com.yubar.kedou.mq;
/**
 * Author:Yu-Bar
 * Date:2023/12/22-15:05
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *@ClassName RabbitMQConfig
 *@Description TODO
 *@Author Yu-Bar
 *@Date 2023/12/22 15:05
 *@Version 1.0
 **/
@Slf4j
@Component
public class QueueManager {

    private final RabbitAdmin rabbitAdmin;

    @Autowired
    public QueueManager(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }


    /**
     * 添加一个方法来动态创建队列并将其绑定到 Direct 交换机
     * @param queueName 队列名
     * @param routingKey 队列路由
     */
    public void createQueueAndBindToExchange(String queueName, String routingKey) {
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);

        DirectExchange directExchange = new DirectExchange("kedou.message");
        rabbitAdmin.declareExchange(directExchange);

        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
        log.info("创建消息队列：{}",queueName);
    }
}
