package com.app.utils;

import java.util.UUID;

public class SqlUtil {
	/**
	 * 将SQL语句中的变量转义,防止SQL注入
	 * @return 转义后的变量
	 * @param SQL语句的变量
	 */
	public static String toSqlVar(String var){
		if(var==null){
			return "";
		}
		if(var.contains("'")){
			var = var.replace("'", "\\'");
		}
		return var;
	}
	
	/**
	 * 去除数据库中存入的数据转义符(去掉变量的反斜杠)
	 * @param var 字符串变量
	 * @return 去除转义后的反斜杠变量
	 */
	public static String enSqlVar(String var){
		if(var==null){
			return "";
		}
		if(var.contains("\\'")){
			var = var.replace("\\'", "'");
		}
		return var;
	}
	
	/**
	 * 生成36位数的大写UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().toUpperCase();
	}
	
}
