package com.shaun.order_service.service;

import com.shaun.order_service.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
public interface OrderService extends IService<Order> {
    // 创建新的订单
    String createNewOrder(String courseId, String memberId);

    // 根据订单号获取订单
    Order getOrderByOrderNo(String orderNo);

    // 判断用户是否已经购买课程
    boolean isBuyCourse(String courseId, String memberId);
}
