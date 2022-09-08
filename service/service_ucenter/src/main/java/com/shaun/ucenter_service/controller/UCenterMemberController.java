package com.shaun.ucenter_service.controller;


import com.shaun.commonutils.JwtUtil;
import com.shaun.commonutils.Result;
import com.shaun.commonutils.vo.MemberVo;
import com.shaun.ucenter_service.entity.UCenterMember;
import com.shaun.ucenter_service.entity.vo.LoginVo;
import com.shaun.ucenter_service.entity.vo.RegisterVo;
import com.shaun.ucenter_service.service.UCenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-01-10
 */
@RestController
@RequestMapping("/ucenter_service/member")
@Api(description = "用户注册和登录接口")
public class UCenterMemberController {
    @Autowired
    private UCenterMemberService uCenterMemberService;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public Result login(@RequestBody @ApiParam("登录Vo对象") LoginVo loginVo) {
        // 调用业务层进行登录业务处理，并返回token值
        String token = this.uCenterMemberService.login(loginVo);
        return Result.Ok().data("token", token);
    }

    @PostMapping("/register")
    @ApiOperation("注册用户的方法")
    public Result register(@RequestBody @ApiParam("注册Vo对象") RegisterVo registerVo) {
        // 调用业务层进行注册业务处理
        this.uCenterMemberService.registerMember(registerVo);
        return Result.Ok();
    }

    @GetMapping("/getMemberInfo")
    @ApiOperation("根据request头里的header属性获取token值，返回用户数据")
    public Result getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        UCenterMember member = this.uCenterMemberService.getById(memberId);
        return Result.Ok().data("item", member);
    }

    @PostMapping("/getMemberById/{id}")
    @ApiOperation("根据id获取会员信息")
    public MemberVo getMemberById(@PathVariable("id") @ApiParam("用户id") String id) {
        UCenterMember member = this.uCenterMemberService.getById(id);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member, memberVo);
        return memberVo;
    }

    @GetMapping("/getRegisterNum/{date}")
    @ApiOperation("根据日期，获取某一天的注册人数")
    public Integer getRegisterNum(@PathVariable("date") @ApiParam("日期") String date) {
        return this.uCenterMemberService.getRegisterNum(date);
    }
}






