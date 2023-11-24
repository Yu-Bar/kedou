package com.yubar.kedou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.yubar.kedou.mapper")
@SpringBootApplication
public class KeDouApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeDouApplication.class, args);
    }

}
