package com.shaun.service_acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Shaun
 * @Date 2022/2/19 14:53
 * @Description: ACL模块的启动类
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.shaun")
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class);
    }
}
