package com.shaun.ucenter_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.commonutils.JwtUtil;
import com.shaun.commonutils.MD5Util;
import com.shaun.commonutils.Result;
import com.shaun.commonutils.ResultCode;
import com.shaun.serverbase.exceptionhandler.GuliException;
import com.shaun.ucenter_service.entity.UCenterMember;
import com.shaun.ucenter_service.entity.vo.LoginVo;
import com.shaun.ucenter_service.entity.vo.RegisterVo;
import com.shaun.ucenter_service.mapper.UCenterMemberMapper;
import com.shaun.ucenter_service.service.UCenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-01-10
 */
@Service
public class UCenterMemberServiceImp extends ServiceImpl<UCenterMemberMapper, UCenterMember> implements UCenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 进行登录业务处理，并返回token值给控制层。
     * @param loginVo 用于登录参数的实体类
     * @return token值
     */
    @Override
    public String login(LoginVo loginVo) {
        // 1、获取登录参数实体类中的手机号码和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 2、对手机号码和密码进行非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
            throw new GuliException(ResultCode.ERROR, "手机号码或者密码不能为空");

        // 3、向数据库查询是否有该手机的用户数据
        QueryWrapper<UCenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UCenterMember member = this.getOne(wrapper);

        // 4、判断获取的用户数据是否为空
        if (member == null)
            throw new GuliException(ResultCode.ERROR, "手机号码尚未注册");

        // 5、将密码进行加密，然后对用户数据的密码进行比较
        String enCode = MD5Util.encrypt(password);
        System.out.println("密码加密："+enCode);
        if (!enCode.equals(member.getPassword()))
            throw new GuliException(ResultCode.ERROR, "密码错误");

        // 6、手机号码和密码符合，登录成功返回token值
        String token = JwtUtil.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    /**
     * 进行用户注册的业务处理
     * @param registerVo 前端用户注册实体类
     */
    @Override
    public void registerMember(RegisterVo registerVo) {
        // 1、获取所有注册参数
        String mobile = registerVo.getMobile();
        String nickName = registerVo.getNickname();
        String password = registerVo.getPassword();
        String verifyCode = registerVo.getVerifyCode();

        // 2、 对所有参数进行非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(verifyCode) )
            throw new GuliException(ResultCode.ERROR, "注册信息不能为空");

        // 3、判断手机号码是否已经注册过了
        QueryWrapper<UCenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        long count = this.count(wrapper);
        if (count > 0)
            throw new GuliException(ResultCode.ERROR, "手机号码已经注册过了");

        // 4、判断输入的验证码是否正确
        if (!verifyCode.equals(redisTemplate.opsForValue().get(mobile)))
            throw new GuliException(ResultCode.ERROR, "验证码错误");

        // 5、进行注册业务处理
        UCenterMember member = new UCenterMember();
        BeanUtils.copyProperties(registerVo, member);
        member.setPassword(MD5Util.encrypt(password));
        this.save(member);
    }

    /**
     * 根据给定日期，查询某一天的注册人数
     * @param date 日期
     * @return 注册人数
     */
    @Override
    public Integer getRegisterNum(String date) {
        return this.baseMapper.getRegisterNum(date);
    }
}
