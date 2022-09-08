package com.shaun.ucenter_service.mapper;

import com.shaun.ucenter_service.entity.UCenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2022-01-10
 */
@Mapper
public interface UCenterMemberMapper extends BaseMapper<UCenterMember> {

    Integer getRegisterNum(String date);
}
