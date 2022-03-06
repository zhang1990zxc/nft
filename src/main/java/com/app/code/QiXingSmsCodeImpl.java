package com.app.code;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName QiXingSmsCodeImpl
 * @Description 整条街最靓的仔，写点注释吧
 * @Author 天涯
 * @Date 2022/3/6 13:47
 * @Version 1.0
 **/
@Component
public class QiXingSmsCodeImpl implements SmsCodeApi {

    @Autowired
    SmsCodeProperties smsCodeProperties;

    @Override
    public String getToken() {
        // http://125.77.159.17:8000/api/sign/username=用户名&password=密码
        String url = smsCodeProperties.getUrl() + "sign/username=" + smsCodeProperties.getUsername() + "&password=" + smsCodeProperties.getPassword();
        HttpRequest httpRequest = HttpUtil.createGet(url);
        String body = httpRequest.execute().body();
        if (body.startsWith("1")) {
            return body.split("\\|")[1];
        }
        return null;
    }

    @Override
    public String getMobilePhone(SmsCodeParameter parameter) {
        // http://125.77.159.17:8000/api/yh_qh/id=项目ID&operator=0&Region=0&card=0&phone=&loop=1&filer=&token=登录返回token
        String url = smsCodeProperties.getUrl() + "yh_qh/id=" + parameter.getId() + "&operator=" + parameter.getOperator()
                + "&Region=" + parameter.getRegion() + "&card=" + parameter.getCard() + "&phone=" + parameter.getPhone()
                + "&loop=" + parameter.getLoop() + "&filer=" + parameter.getFilter() + "&token=" + parameter.getToken();
        HttpRequest httpRequest = HttpUtil.createGet(url);
        String body = httpRequest.execute().body();
        if (body.startsWith("1")) {
            return body.split("\\|")[1];
        }
        return null;
    }

    @Override
    public String getVerificationCode(SmsCodeParameter parameter) {
        // http://125.77.159.17:8000/api/yh_qm/id=项目ID&phone=手机号码&t=开发者用户名&token=登录返回token
        String url = smsCodeProperties.getUrl() + "yh_qm/id=" + parameter.getId() + "&phone=" + parameter.getPhone()
                + "&t=" + parameter.getT() + "&token=" + parameter.getToken();
        HttpRequest httpRequest = HttpUtil.createGet(url);
        String body = httpRequest.execute().body();
        if (body.startsWith("1")) {
            return body.split("\\|")[1];
        }
        return null;
    }

    @Override
    public String releaseMobilePhone(SmsCodeParameter parameter) {
        // http://125.77.159.17:8000/api/yh_sf/id=项目ID&phone=手机号码&token=登录返回token
        String url = smsCodeProperties.getUrl() + "yh_sf/id=" + parameter.getId() + "&phone=" + parameter.getPhone() + "&token=" + parameter.getToken();
        HttpRequest httpRequest = HttpUtil.createGet(url);
        String body = httpRequest.execute().body();
        if (body.startsWith("1")) {
            return body.split("\\|")[1];
        }
        return null;
    }

}
