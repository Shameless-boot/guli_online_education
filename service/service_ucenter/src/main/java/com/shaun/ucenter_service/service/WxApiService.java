package com.shaun.ucenter_service.service;

import com.shaun.ucenter_service.entity.UCenterMember;

/**
 * @Author Shaun
 * @Date 2022/1/12 21:43
 * @Description: 微信顶级服务接口
 */
public interface WxApiService {
    String getAccessTokenInfo(String code, String wxAppId, String wxAppSecret);

    String getUserInfo(String access_token, String openid);

    UCenterMember getMemberByOpenId(String openid);
}
