package com.shaun.msm_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.shaun.msm_service.service.MsmService;
import com.shaun.msm_service.utils.SmsClient;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author Shaun
 * @Date 2022/1/9 14:50
 * @Description: 短信业务层实现类
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     *
     * @param phoneNumber 用户手机号码
     * @param params 传递给阿里云短信服务的参数
     * @return 是否发送成功
     */
    @Override
    public boolean sendVerifyCode(String phoneNumber, Map<String, String> params) {
        Client client;
        try {
            client = SmsClient.createClient("LTAI5tP99e9Cqidp43N62uax", "EmRpRChbaf2IJs61lbbFHS7V8Y9DtL");
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber) // 用户手机号码
                    .setSignName("阿里云短信测试") // 短信签名名称
                    .setTemplateParam(JSONObject.toJSONString(params)) // 需要发送的验证码
                    .setTemplateCode("SMS_154950909"); // 短信模板ID

            SendSmsResponse response = client.sendSms(request);
            String code = response.getBody().getCode();
            return code.equals("OK");
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
