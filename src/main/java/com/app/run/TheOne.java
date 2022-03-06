package com.app.run;

import java.util.List;

import okhttp3.*;

/**
 * 唯一艺术
 * 登录和报名链接
 * https://sourl.cn/cVtBbE
 * https://sourl.cn/CTDrJN
 * @author Crete
 */
public class TheOne {

	public static void main(String[] args) {
		//手机号码
		String phone = "13076289332";
		
		//发送验证码
		//String sendSMS = sendSMS(phone);
		//System.out.println(sendSMS);
		//{"code":200,"message":"成功","data":true}
		
		//登录
		//String code = "509847";//验证码
		//String loginMsg = login(phone, code);
		//System.out.println(loginMsg);
		//{"code":200,"message":"成功","data":{"userName":"13076289332","phone":"13076289332","nicknames":"唯艺10230","photo":"https://theoneart-wechat.oss-cn-beijing.aliyuncs.com/uniapp-small-program/1.png","uuid":"6708dbb34be2173ad5300e7891108722","token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzA3NjI4OTMzMiIsImlhdCI6MTY0NjI4OTc2OCwiZXhwIjoxNjQ2ODk0NTY4fQ.wHHmnmzKfRKea2QZBlbTqixLBHoWr-P46vcOaVrUHem9O73lWMWVvrFXYeQFErXy_zZFX-rFsUR072cTYIYZig","isNewUser":0}}

		//解析拿到token
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzA3NjI4OTMzMiIsImlhdCI6MTY0NjQ4NzY3MywiZXhwIjoxNjQ3MDkyNDczfQ.bSbAeezWvU4gLCAN1ObxjEWjUBxbl_SpYxpSG6wN_4LXCmfaNJjwnHu3yTdgbuGbeE0NQ4TVVOj8ajdZ7IKnYA";
		
		//获取用户信息,是否完成实名认证
		String queryUserMsg = queryUserMsg(token);
		System.out.println(queryUserMsg);
		//{"code":200,"message":"成功","data":{"userUuid":"6708dbb34be2173ad5300e7891108722","initiativeNum":0,"passiveNum":0,"friendNum":0,"likeNum":0,"historyViewNum":0,"realname":"*朝雄","idNumber":"4402**********0335","statusVerified":1,"verifiedMsg":null,"verifiedTime":"2022-03-03 14:44:51","statusBankVerified":0,"avatar":"https://theoneart-wechat.oss-cn-beijing.aliyuncs.com/uniapp-small-program/1.png","nickname":"唯艺10230","nicknameUpdatetime":null,"birthday":null,"introduce":null,"idnumberFront":null,"idnumberBack":null,"level":0,"levelUuid":null,"levelName":null,"title":0,"sex":1,"isAuthor":0,"artDetail":null,"alipayAccount":"","viewHomepage":0,"viewAssets":0,"isPwd":0,"isPayPwd":0,"isFulfill":1,"isBindingWechat":0,"inviteCode":"b51lG0jn","email":""}}
		
		//实名认证
		String name = "张三";
		String idCard = "123456";
		String cardMsg = toCard(phone,name,idCard,token);
		System.out.println(cardMsg);
		
		//活动报名
		String activity = "pJbrrV5aqQJNwiwTZ2FILxKlYhHeId9VAUX892ISqSqKoQaMl20gaTtioK0EVu2k";//活动key
		String apply = toApply(token,activity);
		System.out.println(apply);
		//*/
	}
	
	/**
	 * 活动报名
	 * @return 
	 */
	private static String toApply(String token, String activity) {
		String url = "https://api.theone.art/activity/api/activity/signUp";
		OkHttpClient okHttpClient = new OkHttpClient();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, "{\"actKey\":\""+activity+"\"}");
		Request request = new Request.Builder().url(url).post(requestBody).addHeader("authorization", token).build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response execute = call.execute();
		    ResponseBody body = execute.body();
		    return body.string();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}

	/**
	 * 自动实名认证
	 * @param token 
	 * @return 
	 */
	private static String toCard(String phone, String name, String idCard, String token) {
		String url = "https://api.theone.art/user/api/userAttribute/verifiedIdcard";
		OkHttpClient okHttpClient = new OkHttpClient();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, "{\"idNumber\":\""+idCard+"\",\"phoneCode\":\"\",\"realname\":\""+name+"\"}");
		Request request = new Request.Builder().url(url).post(requestBody).addHeader("authorization", token).build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response execute = call.execute();
		    ResponseBody body = execute.body();
		    return body.string();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询用户信息判断是否已经实名制
	 */
	private static String queryUserMsg(String token) {
		String url = "https://api.theone.art/market/api/userAttribute/queryByUserUuid";
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody bodys = new FormBody.Builder().build();
		Request request = new Request.Builder().url(url).post(bodys).addHeader("authorization", token).build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response execute = call.execute();
		    ResponseBody body = execute.body();
		    return body.string();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证码登录(返回token)
	 */
	public static String login(String phone,String code) {
		String codeRtn = null;
		if(code!=null && code.length()>3) {
			String url = "https://api.theone.art/auth/api/auth/authCodeLogin";
			OkHttpClient okHttpClient = new OkHttpClient();
			MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	        RequestBody requestBody = RequestBody.create(JSON, "{\"phone\":\""+phone+"\",\"authCode\":\""+code+"\"}");
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Call call = okHttpClient.newCall(request);
			try {
			    Response execute = call.execute();
			    ResponseBody body = execute.body();
			    List<String> headers = execute.headers("Set-Cookie");
			    if(headers.size()>0) {
			    	//输出cookie
			    	System.out.println(headers.get(0));
			    }
			    return body.string();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		return codeRtn;
	}
	
	/**
	 * 发送验证码
	 */
	public static String sendSMS(String phone) {
		String code = null;
		String url = "https://api.theone.art/auth/api/auth/getAuthCode";
		OkHttpClient okHttpClient = new OkHttpClient();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, "{\"phone\":\""+phone+"\",\"type\":1}");
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Call call = okHttpClient.newCall(request);
		try {
		    ResponseBody body = call.execute().body();
		    return body.string();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return code;
	}

}
