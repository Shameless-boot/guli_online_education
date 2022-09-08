package com.shaun.msm_service.service;

import java.util.Map;

/**
 * @Author Shaun
 * @Date 2022/1/9 14:50
 * @Description: 短信业务层顶级接口
 */

public interface MsmService {
    boolean sendVerifyCode(String phoneNumber, Map<String, String> params);
}
