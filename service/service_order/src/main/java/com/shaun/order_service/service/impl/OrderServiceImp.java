package com.shaun.order_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.commonutils.vo.CourseVo;
import com.shaun.commonutils.vo.MemberVo;
import com.shaun.order_service.client.EduClient;
import com.shaun.order_service.client.MemberClient;
import com.shaun.order_service.entity.Order;
import com.shaun.order_service.mapper.OrderMapper;
import com.shaun.order_service.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.order_service.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@Service
public class OrderServiceImp extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;

    @Autowired
    private MemberClient memberClient;

    /**
     * 创建新的订单
     * @param courseId 课程ID
     * @param memberId 会员ID
     * @return 订单号
     */
    @Override
    public String createNewOrder(String courseId, String memberId) {
        // 1、创建订单对象
        Order order = new Order();
        // 2、设置订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());

        //3、远程调用获取课程信息
        CourseVo courseVo = this.eduClient.getCourseVoById(courseId);
        //4、远程调用获取用户信息
        System.out.println(this.memberClient);
        MemberVo memberVo = this.memberClient.getMemberById(memberId);

        // 5、将用户信息和课程信息赋值给订单对象
        order.setCourseCover(courseVo.getCover());
        order.setCourseId(courseId);
        order.setCourseTitle(courseVo.getTitle());
        order.setTeacherName(courseVo.getTeacherName());
        order.setMemberId(memberId);
        order.setNickname(memberVo.getNickname());
        order.setMobile(memberVo.getMobile());
        order.setTotalFee(courseVo.getPrice());
        order.setPayType(1);
        order.setStatus(0);

        // 6、将订单添加到数据库中
        this.save(order);

        return order.getOrderNo();
    }

    /**
     * 根据订单号获取订单
     * @param orderNo 订单号
     * @return 订单
     */
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);

        return this.getOne(wrapper);
    }

    /**
     * 根据课程ID和用户ID查询订单表，判断用户是否购买了课程
     * @param courseId 课程ID
     * @param memberId 用户ID
     * @return 用户是否购买
     */
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1); // 订单状态：0：未支付，1：已支付
        long count = this.count(wrapper);

        return count > 0;
    }
}
