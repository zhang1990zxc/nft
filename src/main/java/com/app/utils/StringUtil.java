package com.app.utils;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {

	/**
	 * 判断字符串是否是数字
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取objid
	 * @return
	 */
	public static String getObjId() {
		String string = UUID.randomUUID().toString().toUpperCase();
		return string;
	}

	/**
	 * 判断字符串是否为空
	 * @param str 字符串
	 * @return boolean 字符串为空返回true
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if(obj==null) {
			return true;
		}
		return false;
	}

	/**
	 * 获取字符串A出现B字符的次数
	 * @param a 被匹配的长字符串
	 * @param b 匹配的短字符串
	 * @return 匹配次数
	 */
	public static int Hit(String a, String b) {
		if (a.length() < b.length()) {
			return 0;
		}
		char[] a_t = a.toCharArray();
		int count = 0;
		for (int i = 0; i < a.length() - b.length(); i++) {
			StringBuffer buffer = new StringBuffer();
			for (int j = 0; j < b.length(); j++) {
				buffer.append(a_t[i + j]);
			}
			if (buffer.toString().equals(b)) {
				count++;
			}
		}
		return count;
	}
	
	/**
     * 判断字符串中是否包含中文
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否是手机号码
     * @param val
     * @return
     */
    public static boolean isPhone(String val) {
    	String regEx = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(val);
		if(!matcher.matches()) {
			return false;
		}
		return true;
    }
    
    /**
     * 判断邮箱是否正确
     * @param val
     * @return
     */
    public static boolean isEmail(String val) {
    	String regEx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    	Pattern pattern = Pattern.compile(regEx);
    	Matcher matcher = pattern.matcher(val);
    	if(!matcher.matches()) {
			return false;
		}
		return true;
    }
    
    /**
     * 用户名判断
     * @param val
     * @return
     */
    public static boolean isUserLogin(String val) {
    	String regEx = "^[a-zA-Z][a-zA-Z0-9_]*$";
    	Pattern pattern = Pattern.compile(regEx);
    	Matcher matcher = pattern.matcher(val);
    	if(!matcher.matches()) {
			return false;
		}
		return true;
    }

	/**
	 * 求百分比
	 * @param v1 除数
	 * @param v2 被除数
	 * @param scale 保留小数
	 * @return value Double
	 */
	public static double divide(double v1, double v2, int scale) {
		double c = div(v1, v2, scale + 2);
		return div(c * 100.0, 1, scale);
	}

	/**
	 * 提供（相?）精?的除法?算。当?生除不尽的情况?，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精?到小数点以后几位。
	 * @return ?个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 获取request的值(String)
	 * @param request 请求对象
	 * @param value 获取的参数
	 * @param type 获取的数据类型（a=getAttribute,p=getParameter）
	 * @param retype 返回数据类型（null=对象，""=空字符串）
	 * @return String 获取到的数据
	 */
	public static String getRequestValue(HttpServletRequest request,String value,String type,String retype) {
		if(type.equals("a")) {
			String str = null;
			if(retype==null || retype.equals("null")) {
				str = request.getAttribute(value)==null?null:request.getAttribute(value).toString();
			}else {
				str = request.getAttribute(value)==null?"":request.getAttribute(value).toString();
			}
			return str;
		}else {
			String str = null;
			if(retype==null || retype.equals("null")) {
				str = request.getParameter(value)==null?null:request.getParameter(value).toString();
			}else {
				str = request.getParameter(value)==null?"":request.getParameter(value).toString();
			}
			return str;
		}
	}
	
	/**
	 * 获取session的值(String)
	 * @param request 请求对象
	 * @param value 获取的参数
	 * @param retype 返回数据类型（null=对象，""=空字符串）
	 * @return 获取到的数据
	 */
	public static String getSessionValue(HttpServletRequest request,String value,String retype) {
		String str = null;
		if(retype==null || retype.equals("null")) {
			str = request.getSession().getAttribute(value)==null?null:request.getSession().getAttribute(value).toString();
		}else {
			str = request.getSession().getAttribute(value)==null?"":request.getSession().getAttribute(value).toString();
		}
		return str;
	}
	
	/**
	 * 获取Application的值(String)
	 * @param request 请求对象
	 * @param value 获取的参数
	 * @param retype 返回数据类型（null=对象，""=空字符串）
	 * @return 获取到的数据
	 */
	public static String getApplicationValue(HttpServletRequest request,String value,String retype) {
		String str = null;
		if(retype==null || retype.equals("null")) {
			str = request.getSession().getServletContext().getAttribute(value)==null?null:request.getSession().getServletContext().getAttribute(value).toString();
		}else {
			str = request.getSession().getServletContext().getAttribute(value)==null?"":request.getSession().getServletContext().getAttribute(value).toString();
		}
		return str;
	}
	
	/**
	 * 获取域对象中的数据
	 * @param request 请求对象
	 * @param value key
	 * @param type 域对象类型(s=session\a=application\r=request)
	 * @return Object或者null
	 */
	public static Object getRequestObject(HttpServletRequest request,String value,String type) {
		if(type.equals("r")) {
			return request.getAttribute(value)==null?null:request.getAttribute(value);
		}else if(type.equals("a")) {
			return request.getSession().getServletContext().getAttribute(value)==null?null:request.getSession().getServletContext().getAttribute(value);
		}else if(type.equals("s")) {
			return request.getSession().getAttribute(value)==null?null:request.getSession().getAttribute(value);
		}
		return null;
	}
	
	/**
	 * 根据当前时间获取问候语
	 * @return
	 */
	public static String getWelcome() {
		String dateTime = DateUtil.getDateTime().split(" ")[1];
		int hour  = Integer.parseInt(dateTime.split(":")[0]);
		
		String hello = "";
		if (hour < 6) {
			hello = "凌晨好";
		} else if (hour < 9) {
			hello = "早上好";
		} else if (hour < 12) {
			hello = "上午好";
		} else if (hour < 14) {
			hello = "中午好";
		} else if (hour < 17) {
			hello = "下午好";
		} else if (hour < 19) {
			hello = "傍晚好";
		} else if (hour < 22) {
			hello = "晚上好";
		} else {
			hello = "晚上好";
		}
		return hello;
	}
	
}
