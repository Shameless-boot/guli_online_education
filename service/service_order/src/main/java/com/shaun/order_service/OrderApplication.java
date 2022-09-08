package com.shaun.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Shaun
 * @Date 2022/2/9 20:46
 * @Description: Order模块启动类
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.shaun")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
