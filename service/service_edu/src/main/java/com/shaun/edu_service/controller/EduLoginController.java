package com.shaun.edu_service.controller;

import com.shaun.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Shaun
 * @Date 2021/12/26 14:49
 * @Description: 讲师登录接口
 */

@RestController
@RequestMapping("/edu_service/user")
@Api("讲师登录接口")
@CrossOrigin
public class EduLoginController {

    @PostMapping("/login")
    @ApiOperation("讲师登录方法")
    public Result login() {
        return Result.Ok().data("token", "admin");
    }

    @GetMapping("/info")
    @ApiOperation("获取讲师信息")
    public Result getInfo() {
        return Result.Ok().data("name", "admin").data("avatar", "https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
    }
}
