package com.shaun.ucenter_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.commonutils.ResultCode;
import com.shaun.serverbase.exceptionhandler.GuliException;
import com.shaun.ucenter_service.entity.UCenterMember;
import com.shaun.ucenter_service.service.UCenterMemberService;
import com.shaun.ucenter_service.service.WxApiService;
import com.shaun.ucenter_service.utils.ConstantWxUtils;
import com.shaun.ucenter_service.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Shaun
 * @Date 2022/1/12 21:43
 * @Description:
 */
@Service
public class WxApiServiceImpl implements WxApiService {
    @Autowired
    private UCenterMemberService uCenterMemberService;

    /**
     * 根据临时票据、appid、appsecret获取access_token的JSON数据
     * @param code 临时票据
     * @param wxAppId 应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @param wxAppSecret 应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     * @return access_token
     */
    @Override
    public String getAccessTokenInfo(String code, String wxAppId, String wxAppSecret) {
        // 1、拼接获取access_token的路径字符串
        String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        // 2、将参数填入baseUrl路径之中
        String accessTokenUrl = String.format(baseUrl, wxAppId, wxAppSecret, code);

        // 3、通过httpclient访问路径，获取access_token
        String accessTokenInfo;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, "微信扫码登录失败");
        }
        return accessTokenInfo;
    }

    /**
     * 根据access_token和openid获取用户信息
     * @param access_token 接口调用凭证
     * @param openid 授权用户唯一标识
     * @return 用户信息
     */
    @Override
    public String getUserInfo(String access_token, String openid) {
        //访问微信的资源服务器，获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";

        String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

        String userInfo = "";
        try {
            userInfo = HttpClientUtils.get(userInfoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, "微信扫码登录失败");
        }
        return userInfo;
    }

    /**
     * 根据openid查询数据库
     * @param openid 应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @return 用户对象
     */
    @Override
    public UCenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UCenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return uCenterMemberService.getBaseMapper().selectOne(wrapper);
    }
}
