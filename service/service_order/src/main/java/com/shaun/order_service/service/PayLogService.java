package com.shaun.order_service.service;

import com.shaun.order_service.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
public interface PayLogService extends IService<PayLog> {
    // 生成二维码
    Map<String, Object> CreateWxPayCode(String orderNo);

    // 获取支付状态
    Map<String, String> queryPayStatus(String orderNo);

    // 创建支付记录，并修改订单支付状态
    void updateOrderStatus(Map<String, String> map);
}
