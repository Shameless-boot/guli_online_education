package com.shaun.sta_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Shaun
 * @Date 2022/2/15 0:26
 * @Description: 统计模块启动类
 */
@SpringBootApplication
@ComponentScan("com.shaun")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class);
    }
}
