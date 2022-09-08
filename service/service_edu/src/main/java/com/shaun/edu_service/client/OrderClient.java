package com.shaun.edu_service.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Shaun
 * @Date 2022/2/14 20:35
 * @Description: 远程调用Order模块的接口
 */
@Component
@FeignClient("service-order")
public interface OrderClient {
    @GetMapping("/order_service/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") @ApiParam("课程ID") String courseId,
                        @PathVariable("memberId") @ApiParam("用户ID") String memberId);
}
