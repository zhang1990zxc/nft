package com.app.entity;

import java.util.Date;
import com.app.comm.DataTable;

/**
 * 用户表
 * @author app
 */
@DataTable("t_user")
public class User {

	/** 主键ID  */
	private String objid;
	/** 手机号码  */
	private String f_phone;
	/** 注册时间  */
	private Date f_cdt;
	/** Token最后维系时间  */
	private Date f_opt_dt;
	/** 身份证号码  */
	private String f_card;
	/** 是否已经绑定身份证(Y/N)  */
	private String f_is_card;
	/** 是否已经预约(Y/N)  */
	private String f_is_reserve;
	/** 预约时间  */
	private Date f_reserve_dt;
	/** Token  */
	private String f_token;
	/** Token是否在线(Y/N)  */
	private String f_is_online;

	/** ========== 非数据库映射字段 ========== **
	/** 控制null数据返回空字符串(只针对String类型数据) **/
	private String nulls;


	/** 获取 主键ID  */
	public String getObjid() {
		if("Y".equals(this.nulls)) {if(this.objid==null) {return "";}}
		return objid;
	}
	/** 设置 主键ID  */
	public void setObjid(String objid) {
		this.objid = objid;
	}
	/** 获取 手机号码  */
	public String getF_phone() {
		if("Y".equals(this.nulls)) {if(this.f_phone==null) {return "";}}
		return f_phone;
	}
	/** 设置 手机号码  */
	public void setF_phone(String f_phone) {
		this.f_phone = f_phone;
	}
	/** 获取 注册时间  */
	public Date getF_cdt() {
		return f_cdt;
	}
	/** 设置 注册时间  */
	public void setF_cdt(Date f_cdt) {
		this.f_cdt = f_cdt;
	}
	/** 获取 Token最后维系时间  */
	public Date getF_opt_dt() {
		return f_opt_dt;
	}
	/** 设置 Token最后维系时间  */
	public void setF_opt_dt(Date f_opt_dt) {
		this.f_opt_dt = f_opt_dt;
	}
	/** 获取 身份证号码  */
	public String getF_card() {
		if("Y".equals(this.nulls)) {if(this.f_card==null) {return "";}}
		return f_card;
	}
	/** 设置 身份证号码  */
	public void setF_card(String f_card) {
		this.f_card = f_card;
	}
	/** 获取 是否已经绑定身份证(Y/N)  */
	public String getF_is_card() {
		if("Y".equals(this.nulls)) {if(this.f_is_card==null) {return "";}}
		return f_is_card;
	}
	/** 设置 是否已经绑定身份证(Y/N)  */
	public void setF_is_card(String f_is_card) {
		this.f_is_card = f_is_card;
	}
	/** 获取 是否已经预约(Y/N)  */
	public String getF_is_reserve() {
		if("Y".equals(this.nulls)) {if(this.f_is_reserve==null) {return "";}}
		return f_is_reserve;
	}
	/** 设置 是否已经预约(Y/N)  */
	public void setF_is_reserve(String f_is_reserve) {
		this.f_is_reserve = f_is_reserve;
	}
	/** 获取 预约时间  */
	public Date getF_reserve_dt() {
		return f_reserve_dt;
	}
	/** 设置 预约时间  */
	public void setF_reserve_dt(Date f_reserve_dt) {
		this.f_reserve_dt = f_reserve_dt;
	}
	/** 获取 Token  */
	public String getF_token() {
		if("Y".equals(this.nulls)) {if(this.f_token==null) {return "";}}
		return f_token;
	}
	/** 设置 Token  */
	public void setF_token(String f_token) {
		this.f_token = f_token;
	}
	/** 获取 Token是否在线(Y/N)  */
	public String getF_is_online() {
		if("Y".equals(this.nulls)) {if(this.f_is_online==null) {return "";}}
		return f_is_online;
	}
	/** 设置 Token是否在线(Y/N)  */
	public void setF_is_online(String f_is_online) {
		this.f_is_online = f_is_online;
	}

	/** ========== 非数据库映射字段 ========== **
	/** 获取 控制null数据返回空字符串(只针对String类型数据) **/
	public String getNulls() {
		return nulls;
	}
	/** 设置 控制null数据返回空字符串(只针对String类型数据) **/
	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

}
