package com.app.run;

import com.app.code.SmsCodeApi;
import com.app.code.SmsCodeParameter;
import com.app.code.SmsCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName BulkRegistrationRunner
 * @Description 整条街最靓的仔，写点注释吧
 * @Author 天涯
 * @Date 2022/3/6 20:59
 * @Version 1.0
 **/
@Component
public class BulkRegistrationRunner implements ApplicationRunner {

    @Autowired
    SmsCodeApi smsCodeApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1.获取token
        String token = smsCodeApi.getToken();
        // 2.获取手机号
        SmsCodeParameter smsCodeProperties = new SmsCodeParameter();
        smsCodeProperties.setId("");
        smsCodeProperties.setId("");
        smsCodeProperties.setId("");
        smsCodeProperties.setId("");
        smsCodeProperties.setToken(token);
        String mobilePhone = smsCodeApi.getMobilePhone(smsCodeProperties);

        // https://api.theone.art/auth/api/auth/getAuthCode
        // {"phone": "13856786544","type": 1}
        // {"code":200,"message":"成功","data":true}
        // 发送验证码接口


        // 3.获取七星验证码

        // 4.开始注册
    }

}
