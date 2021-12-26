package com.shaun.serverbase.exceptionhandler;

import com.shaun.commonutils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Shaun
 * @Date 2021/12/22 19:01
 * @Description: 全局异常处理类
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.Error().message("全局异常处理类处理异常...");
    }

    @ExceptionHandler(GuliException.class)
    public Result guliError(GuliException e) {
        e.printStackTrace();
        return Result.Error().code(e.getCode()).message(e.getMsg());
    }
}
