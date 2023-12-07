package com.yubar.kedou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *@ClassName WebSocketConfiguration
 *@Description WebSocket配置类，用于注册WebSocket的Bean
 *@Author Yu-Bar
 *@Date 2023/12/4 0:18
 *@Version 1.0
 **/
@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}