package com.yubar.kedou.config;
/**
 * Author:Yu-Bar
 * Date:2023/12/4-22:32
 */
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@ClassName RabbitMQConfiguration
 *@Description 进行 RabbitMQ 的相关配置
 *@Author Yu-Bar
 *@Date 2023/12/4 22:32
 *@Version 1.0
 **/
@Configuration
public class RabbitMQConfiguration {

    /**
     * 消息转换器
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        // 1.定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        // 2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }

    /**
     * 消息监听器
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        return container;
    }

}
