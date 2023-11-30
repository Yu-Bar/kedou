package com.yubar.kedou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan(basePackages = "com.yubar.kedou.mapper")
@SpringBootApplication
public class KeDouApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeDouApplication.class, args);
    }

}
