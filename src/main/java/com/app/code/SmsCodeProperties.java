package com.app.code;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SmsCodeProperties
 * @Description 整条街最靓的仔，写点注释吧
 * @Author 天涯
 * @Date 2022/3/6 13:51
 * @Version 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "app.sms.code")
@Data
public class SmsCodeProperties {

    private String url;

    private String username;

    private String password;

}
