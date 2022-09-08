package com.shaun.ucenter_service.entity.vo;

import lombok.Data;

/**
 * @Author Shaun
 * @Date 2022/1/10 0:22
 * @Description: 接受前端登录的实体类
 */

@Data
public class LoginVo {
    private String mobile;
    private String password;
}
