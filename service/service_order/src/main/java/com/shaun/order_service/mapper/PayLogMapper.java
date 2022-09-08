package com.shaun.order_service.mapper;

import com.shaun.order_service.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

}
