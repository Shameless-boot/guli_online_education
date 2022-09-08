package com.shaun.ucenter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Shaun
 * @Date 2022/1/9 23:59
 * @Description: uCenter服务启动类
 */

@SpringBootApplication
@ComponentScan("com.shaun")
@EnableDiscoveryClient
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class);
    }
}
