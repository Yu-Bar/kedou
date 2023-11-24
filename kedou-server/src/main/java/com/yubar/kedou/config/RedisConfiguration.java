package com.yubar.kedou.config;
/**
 * Author:LanBao
 * Date:2023/10/26-21:16
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *@ClassName RedisConfiguration
 *@Description
 *@Author 兰豹基
 *@Date 2023/10/26 21:16
 *@Version 1.0
 **/
@Configuration
@Slf4j
public class RedisConfiguration {

    /**
     * 允许Object类型的key-value，都可以被转为json进行存储。
     * @param redisConnectionFactory 自动配置好了连接工厂
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始配置redistemplate...");
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //把对象转为json字符串的序列化工具
//        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
