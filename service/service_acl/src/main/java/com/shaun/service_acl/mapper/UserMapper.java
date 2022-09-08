package com.shaun.service_acl.mapper;

import com.shaun.service_acl.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2022-02-19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
