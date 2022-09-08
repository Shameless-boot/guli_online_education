package com.shaun.order_service.client;

import com.shaun.commonutils.vo.MemberVo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author Shaun
 * @Date 2022/2/9 21:29
 * @Description: 调用UCenter服务方法的接口
 */
@Component
// 指定远程调用模块的名称
@FeignClient(name = "service-ucenter")
public interface MemberClient {
    @PostMapping("/ucenter_service/member/getMemberById/{id}")
    MemberVo getMemberById(@PathVariable("id") @ApiParam("用户id") String id);
}
