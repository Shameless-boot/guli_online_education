package com.shaun.edu_service.client;

import com.shaun.commonutils.Result;
import com.shaun.commonutils.vo.MemberVo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Shaun
 * @Date 2022/2/9 16:07
 * @Description: 远程调用UCenter方法的接口
 */
@Component
// 指定远程调用模块的名称
@FeignClient(name = "service-ucenter")
public interface UCenterClient {
    @PostMapping("/ucenter_service/member/getMemberById/{id}")
    MemberVo getMemberById(@PathVariable("id") @ApiParam("用户id") String id);
}
