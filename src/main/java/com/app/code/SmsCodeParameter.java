package com.app.code;

import lombok.Data;

/**
 * @ClassName SmsCodeParameter
 * @Description 整条街最靓的仔，写点注释吧
 * @Author 天涯
 * @Date 2022/3/6 13:37
 * @Version 1.0
 **/
@Data
public class SmsCodeParameter {

    /**
     [1] id=项目ID  (对应的项目ID可在客户端软件里查询)
     [2] operator=0 (0=默认 1=移动 2=联通 3=电信)
     [3] Region=0   (0=默认)需要哪个地区的直接输入如：上海 系统会自动筛选上海的号码
     [4] card=0     (0=默认 1=虚拟运营商 2=实卡)
     [5] phone=     (你要指定获取的号码,不传入号码情况下,获取新号码.)
     [6] loop=1    （1=过滤 2=不过滤）1排除已做过号码取号时不会再获取到，2不过滤已做号码可以取号时循环获取号码（号码循环做业务必须选择2）
     [7] filer=     (比如排除165 那就填写165|   在比如排除165和170 那就填写165|170|)
     [8] token=     (登录成功返回的token)
     **/

    private String id;

    private String operator;

    private String region;

    private String card;

    private String phone;

    private String loop;

    private String filter;

    private String token;

    /**
     [传入参数]：
     [1] id=项目ID (获取此号码时指定的项目ID)
     [2] phone=    (通过yh_qh方法获取到的手机号)
     [3] t=开发者用户名(这里是传开发者注册时的用户名)
     [4] token=    (登录成功返回的token)
     **/

    private String t;

}
