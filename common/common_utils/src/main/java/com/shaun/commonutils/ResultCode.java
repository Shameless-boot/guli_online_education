package com.shaun.commonutils;

/**
 * @Author Shaun
 * @Date 2021/12/22 15:56
 * @Description: 定义状态码的常量类
 */
public interface ResultCode {
    // 状态码：成功
    Integer SUCCESS = 20000;
    // 状态码：失败
    Integer ERROR = 20001;
    // 状态码：支付中
    Integer PAYING = 25000;
    // 状态码：未登录
    Integer LOGOUT = 28004;
}
