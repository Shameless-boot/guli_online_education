package com.shaun.ucenter_service.service;

import com.shaun.ucenter_service.entity.UCenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.ucenter_service.entity.vo.LoginVo;
import com.shaun.ucenter_service.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-01-10
 */
public interface UCenterMemberService extends IService<UCenterMember> {

    String login(LoginVo loginVo);

    void registerMember(RegisterVo registerVo);

    Integer getRegisterNum(String date);
}
