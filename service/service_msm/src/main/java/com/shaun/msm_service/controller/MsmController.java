package com.shaun.msm_service.controller;

import com.shaun.commonutils.Result;
import com.shaun.msm_service.service.MsmService;
import com.shaun.msm_service.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Shaun
 * @Date 2022/1/9 14:46
 * @Description: 短信控制层
 */

@RestController()
@RequestMapping("/msm_service/message")
@Api(description = "短信服务接口")
public class MsmController {
    @Autowired
    private MsmService msmService;
    // SpringBoot整合Redis提供的
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/sendCode/{phoneNumber}")
    @ApiOperation("发送注册验证码给用户")
    public Result sendCode(@ApiParam("用户手机号码") @PathVariable("phoneNumber") String phoneNumber) {
        // 验证码已发送
        if (redisTemplate.opsForValue().get(phoneNumber) != null)
            return Result.Ok();

        // 1、生成验证码
        String verifyCode = RandomUtil.getSixBitRandom();

        // 2、将验证码存储在Map集合中，因为阿里云只接受json参数
        Map<String, String> params = new HashMap<>();
        params.put("code", verifyCode);

        // 3、调用业务层发送验证码，将用户手机号码和验证码作为参数
        boolean isSend = this.msmService.sendVerifyCode(phoneNumber, params);

        // 4、如果发送成功，则将验证码存储到redis中，并设置5有分钟有效时间
        if (isSend) {
            redisTemplate.opsForValue().set(phoneNumber, verifyCode, 5, TimeUnit.MINUTES);
            return Result.Ok();
        }

        return Result.Error();
    }
}
