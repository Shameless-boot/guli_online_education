package com.shaun.order_service.controller;


import com.shaun.commonutils.JwtUtil;
import com.shaun.commonutils.Result;
import com.shaun.commonutils.ResultCode;
import com.shaun.order_service.entity.Order;
import com.shaun.order_service.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@RestController
@RequestMapping("/order_service/order")
@Api(description = "支付业务控制层")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PutMapping("/createOrder/{courseId}")
    @ApiOperation("生成新订单")
    public Result createOrder(@PathVariable("courseId") @ApiParam("课程ID") String courseId,
                              HttpServletRequest request) {
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId))
            return Result.Error().code(ResultCode.LOGOUT).message("请先登录");

        String orderNo = this.orderService.createNewOrder(courseId, memberId);
        return Result.Ok().data("orderNo", orderNo);
    }

    @GetMapping("/getOrderByOrderNo/{orderNo}")
    @ApiOperation("根据订单号获取订单")
    public Result getOrderByOrderNo(@PathVariable("orderNo") @ApiParam("订单号") String orderNo) {
        Order order = this.orderService.getOrderByOrderNo(orderNo);
        return Result.Ok().data("item", order);
    }

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    @ApiOperation("根据课程ID和用户ID查询订单表，判断课程是否购买")
    public boolean isBuyCourse(@PathVariable("courseId") @ApiParam("课程ID") String courseId,
                               @PathVariable("memberId") @ApiParam("用户ID") String memberId) {
        return this.orderService.isBuyCourse(courseId, memberId);
    }
}

