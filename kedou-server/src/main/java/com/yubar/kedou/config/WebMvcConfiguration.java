package com.yubar.kedou.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.yubar.kedou.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@EnableKnife4j
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    //    @Autowired
//    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;


    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtTokenAdminInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/employee/login");
        registry.addInterceptor(jwtTokenUserInterceptor)
        .addPathPatterns("/user/**")
        .excludePathPatterns("/user/user/login")
        .excludePathPatterns("/user/video/list");
        WebMvcConfigurer.super.addInterceptors(registry);
    }


//    /**
//     * 扩展Spring MVC框架的消息转化器
//     * @param converters
//     */
//    @Override
//    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        log.info("扩展消息转换器...");
//        //创建一个消息转换器对象
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        //需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
//        converter.setObjectMapper(new JacksonObjectMapper());
//        //将自己的消息转换器加入到容器中（需要设置优先级，否则就是排在容器中所有已有的转换器后面）
//        converters.add(0,converter);
//    }
}
