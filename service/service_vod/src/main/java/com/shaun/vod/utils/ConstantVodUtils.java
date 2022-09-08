package com.shaun.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Shaun
 * @Date 2022/1/4 21:51
 * @Description: 存储视频点播连接阿里云相关变量的常量类
 */

@Component
public class ConstantVodUtils implements InitializingBean {
    @Value("${aliyun.vod.file.AccessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.vod.file.AccessKeySecret}")
    private String accessKeySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
    }
}
