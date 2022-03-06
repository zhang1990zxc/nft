package com.app.comm;

import org.apache.commons.dbcp2.BasicDataSource;

public class DaoTool {
	
	/**
	 * 获取本地数据库连接
	 * @param database
	 * @return
	 */
	public static BasicDataSource getDataSource127(String database) {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql://127.0.0.1/"+database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong");
		source.setUsername("root");
		source.setPassword("root");
		return source;
	}
	
	/**
	 * 获取本地数据库连接
	 * @param database
	 * @return
	 */
	public static BasicDataSource getDataSourceTx(String database) {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql://175.178.42.59/"+database+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong");
		source.setUsername("root");
		source.setPassword("xcsql666");
		return source;
	}
	
}
