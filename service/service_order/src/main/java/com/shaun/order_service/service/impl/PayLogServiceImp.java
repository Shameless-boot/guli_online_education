package com.shaun.order_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.shaun.commonutils.ResultCode;
import com.shaun.order_service.entity.Order;
import com.shaun.order_service.entity.PayLog;
import com.shaun.order_service.mapper.PayLogMapper;
import com.shaun.order_service.service.OrderService;
import com.shaun.order_service.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.order_service.utils.HttpClient;
import com.shaun.serverbase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@Service
public class PayLogServiceImp extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    /**
     * 根据订单号获取微信支付二维码
     * @param orderNo 订单号
     * @return 包含二维码地址、支付状态等信息
     */
    @Override
    public Map<String, Object> CreateWxPayCode(String orderNo) {
        try {
            // 1、根据订单号获取订单数据
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = this.orderService.getOne(wrapper);

            // 2、使用map来设置生成二维码需要的参数
            HashMap<String, String> map = new HashMap<>();
            map.put("appid","wx74862e0dfcf69954");//支付id
            map.put("mch_id", "1558950191");//商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());//生成随机的字符串，让每次生成的二维码不一样
            map.put("body", order.getCourseTitle());//生成二维码的名字
            map.put("out_trade_no", orderNo);//二维码唯一的标识
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//支付金额
            map.put("spbill_create_ip", "127.0.0.1");//现在进行支付的ip地址，实际项目使用项目的域名
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");//支付后回调地址
            map.put("trade_type", "NATIVE");//支付类型，NATIVE:根据价格生成二维码

            // 3、发送HttpClient请求，传递参数【XML】格式，构造函数参数为获取微信支付二维码的固定URL
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // 发送参数：1、要转换为XML的MAP集合。2、商户的key，用于加密二维码中的信息
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            // 设置支持https协议
            httpClient.setHttps(true);
            // 执行post请求
            httpClient.post();

            // 4、得到发送返回的结果，返回的是xml格式的字符串
            String content = httpClient.getContent();
            // 将XML格式转换为Map集合
            Map<String, String> result = WXPayUtil.xmlToMap(content);
            // 封装最后的数据
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("out_trade_no",orderNo);
            hashMap.put("course_id",order.getCourseId());
            hashMap.put("total_fee",order.getTotalFee());
            hashMap.put("result_code",result.get("result_code")); //二维码操作状态码
            hashMap.put("code_url",result.get("code_url")); //二维码地址

            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, "生成二维码失败");
        }
    }

    /**
     * 根据订单号，查询支付的状态
     * @param orderNo 订单号
     * @return 支付的状态等
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            // 2、发送HttpClient请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setHttps(true);
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.post();

            // 3、获取返回的数据
            String content = client.getContent();

            // 4、将封装的xml数据包装成map数据
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, "获取订单状态发生错误");
        }
    }

    /**
     * 创建支付记录，并修改订单支付状态
     * @param map 订单的详细信息
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 获取订单号
        String orderNo = map.get("out_trade_no");
        // 根据订单号，获取订单数据
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = this.orderService.getOne(wrapper);

        // 修改订单状态
        if (order.getStatus() == 1) // 已经支付过了，不需要进行额外的操作了
            return;

        // 更新订单中的更新状态
        order.setStatus(1);
        boolean update = this.orderService.updateById(order);
        if (!update)
            throw new GuliException(ResultCode.ERROR, "微信支付记录插入失败");

        // 向支付表中创建支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setTradeState(map.get("trade_status"));
        payLog.setAttr(JSONObject.toJSONString(map));
        this.save(payLog);
    }
}
