package com.pokeya.zhulong.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author mac
 */
@EnableDubbo
@EnableConfigurationProperties
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.pokeya.zhulong.infrastructure")
@ComponentScan({"com.pokeya.zhulong", "com.pokeya.yao"})
public class ZhuLongApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhuLongApplication.class, args);
    }
}
