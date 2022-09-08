package com.shaun.edu_service.client;

import com.shaun.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/5 19:45
 * @Description: 用于远程调用vod服务的方法
 */
@Component
// 指定要远程调用的服务名称
@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
public interface VodClient {

    // 与远程调用服务提供的方法一致
    // 注意：@PathVariable注解需要指定名称
    @DeleteMapping("/service_vod/video/{videoId}")
    Result deleteVideoById(@PathVariable("videoId") String videoId);

    @DeleteMapping("/service_vod/video/deleteBatch")
    Result deleteBatchVideo(@RequestParam("videoList") List<String> videoList);
}
