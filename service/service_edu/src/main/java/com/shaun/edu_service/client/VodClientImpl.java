package com.shaun.edu_service.client;

import com.shaun.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/5 21:08
 * @Description: 远程调用服务端宕机或者超时调用里面的方法
 */
@Component
public class VodClientImpl implements VodClient{

    @Override
    public Result deleteVideoById(String videoId) {
        return Result.Error().message("熔断功能...");
    }

    @Override
    public Result deleteBatchVideo(List<String> videoList) {
        return Result.Error().message("熔断功能...");
    }
}
