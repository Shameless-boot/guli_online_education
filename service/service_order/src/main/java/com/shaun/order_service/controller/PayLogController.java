package com.shaun.order_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.commonutils.ResultCode;
import com.shaun.order_service.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@RestController
@RequestMapping("/order_service/payLog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @GetMapping("/CreateWxPayCode/{orderNo}")
    @ApiOperation("生成微信支付二维码")
    public Result CreateWxPayCode(@PathVariable @ApiParam("订单号") String orderNo) {
        // 返回信息：包含二维码地址、其他信息
        Map<String, Object> map = this.payLogService.CreateWxPayCode(orderNo);
        return Result.Ok().data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    @ApiOperation("根据订单号获取订单支付状态")
    public Result queryPayStatus(@PathVariable("orderNo") @ApiParam("订单号") String orderNo) {
        Map<String, String> map = this.payLogService.queryPayStatus(orderNo);
        if (map == null)
            return Result.Error().message("支付出错了");

        if (map.get("trade_state").equals("SUCCESS")) { //支付成功
            // 添加记录到支付表中，并修改订单表的状态
            this.payLogService.updateOrderStatus(map);
            return Result.Ok().message("支付成功");
        }

        return Result.Ok().code(ResultCode.PAYING).message("支付中");
    }


}

