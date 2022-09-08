package com.shaun.ucenter_service.controller;

import com.google.gson.Gson;
import com.shaun.commonutils.JwtUtil;
import com.shaun.commonutils.ResultCode;
import com.shaun.serverbase.exceptionhandler.GuliException;
import com.shaun.ucenter_service.entity.UCenterMember;
import com.shaun.ucenter_service.service.UCenterMemberService;
import com.shaun.ucenter_service.service.WxApiService;
import com.shaun.ucenter_service.utils.ConstantWxUtils;
import com.shaun.ucenter_service.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author Shaun
 * @Date 2022/1/11 16:16
 * @Description: 微信模块控制层
 */

@Controller
@RequestMapping("/api/ucenter/wx")
@Api(description = "微信模块接口")
public class WxApiController {
    @Autowired
    private UCenterMemberService uCenterMemberService;
    @Autowired
    private WxApiService wxApiService;

    /**
     * 通过临时票据（code）、appid、Appsecret获取access_token，再使用access_token获取用户信息
     * @param code 临时票据，唯一标识ID
     * @param state 原样数据
     * @return 重定向到首页面，带着用户数据的token。
     */
    @GetMapping("/callback")
    @ApiOperation("获取扫描登录的用户信息")
    public String getScanUserInfo(String code, String state) {
        // 获取access_token的JSON数据
        String accessTokenInfo = wxApiService.getAccessTokenInfo(code, ConstantWxUtils.WX_APP_ID,
                ConstantWxUtils.WX_APP_SECRET);

        // 将返回的accessTokenInfo数据转换为Map集合，获取里面的access_token属性和openid属性值。
        // 因为accessTokenInfo是字符串形式的Json数据，所以借用Gson转换为Map集合
        Gson gson = new Gson();
        HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
        String openid = (String) map.get("openid");
        String access_token = (String) map.get("access_token");

        // 根据微信的openid判断该用户是否已经注册过了，如果注册过了直接登录，否则将用户信息添加到数据库中
        UCenterMember member = this.wxApiService.getMemberByOpenId(openid);
        if (member == null) {
            // 使用access_token获取用户信息
            String userInfo = wxApiService.getUserInfo(access_token, openid);
            HashMap userMap = gson.fromJson(userInfo, HashMap.class);
            String  nickname = (String)userMap.get("nickname");
            String headImgUrl = (String)userMap.get("headimgurl");

            member = new UCenterMember();
            member.setNickname(nickname);
            member.setAvatar(headImgUrl);
            member.setOpenid(openid);
            uCenterMemberService.save(member);
        }

        // 生成token值存储用户信息，返回给前端
        String token = JwtUtil.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }

    @GetMapping("/login")
    @ApiOperation("获得微信登录二维码")
    public String showWxScanCode() {
        // 1、创建微信开放平台授权baseUrl模板
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 2、对redirect_url进行urlEncode编码操作
        String redirectUrl = ConstantWxUtils.WX_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 3、将相关微信配置参数填写到baseUrl模板中
        String url = String.format(baseUrl, ConstantWxUtils.WX_APP_ID, redirectUrl, "Shaun");
        return "redirect:" + url;
    }
}
