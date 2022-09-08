package com.shaun.sta_service.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Shaun
 * @Date 2022/2/15 0:27
 * @Description: 远程调用UCenter模块的接口
 */
@Component
@FeignClient("service-ucenter")
public interface UCenterClient {
    @GetMapping("/ucenter_service/member/getRegisterNum/{date}")
    @ApiOperation("根据日期，获取某一天的注册人数")
    Integer getRegisterNum(@PathVariable("date") @ApiParam("日期") String date);
}
