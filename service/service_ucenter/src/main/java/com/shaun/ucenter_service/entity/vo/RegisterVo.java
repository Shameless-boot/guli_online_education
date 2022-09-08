package com.shaun.ucenter_service.entity.vo;

import lombok.Data;

/**
 * @Author Shaun
 * @Date 2022/1/10 14:44
 * @Description: 注册VO实体类
 */

@Data
public class RegisterVo {
    private String nickname;
    private String password;
    private String mobile;
    private String verifyCode;
}
