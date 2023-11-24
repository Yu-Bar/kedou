package com.yubar.kedou.config;
/**
 * Author:LanBao
 * Date:2023/10/24-13:25
 */



import com.yubar.kedou.properties.AliOssProperties;
import com.yubar.kedou.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@ClassName OssConfiguration
 *@Description 对象存储服务配置
 *@Author 兰豹基
 *@Date 2023/10/24 13:25
 *@Version 1.0
 **/
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传工具类对象：{}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
