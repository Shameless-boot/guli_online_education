package com.shaun.oss;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Shaun
 * @Date 2021/12/27 20:08
 * @Description: OSS模块启动类
 */
@ComponentScan("com.shaun")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class);
    }
}
