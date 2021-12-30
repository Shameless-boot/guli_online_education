package com.shaun.serverbase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Shaun
 * @Date 2021/12/29 23:24
 * @Description:
 */

@Configuration
public class GlobalConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 放行哪些请求
        registry.addMapping("/**")
                .allowedMethods("*") // 放行哪些方法
                .allowedHeaders("*") // 放行哪些头部信息
                .allowCredentials(true) // 是否与跨域请求共用同一个Cookie
                .allowedOrigins("http://localhost:9528"); // 允许跨域的源
    }
}
