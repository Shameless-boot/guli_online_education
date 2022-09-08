package com.shaun.cms_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @Author Shaun
 * @Date 2022/1/6 15:36
 * @Description: cms服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.shaun")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class);
    }
}
