package com.app.code;

/**
 * @ClassName SmsCodeApi
 * @Description 整条街最靓的仔，写点注释吧
 * @Author 天涯
 * @Date 2022/3/6 13:31
 * @Version 1.0
 **/
public interface SmsCodeApi {

    /**
     * @Description: 获取token
     * @Date: 2022/3/6 13:32
     * @Param [username, password]
     * @return java.lang.String
     **/
    String getToken();

    /**
     * @Description: 获取手机号
     * @Date: 2022/3/6 13:44
     * @Param [parameter]
     * @return java.lang.String
     **/
    String getMobilePhone(SmsCodeParameter parameter);

    /**
     * @Description: 获取验证码
     * @Date: 2022/3/6 13:44
     * @Param [parameter]
     * @return java.lang.String
     **/
    String getVerificationCode(SmsCodeParameter parameter);


    /**
     * @Description: 释放手机号
     * @Date: 2022/3/6 13:44
     * @Param [parameter]
     * @return java.lang.String
     **/
    String releaseMobilePhone(SmsCodeParameter parameter);
}
