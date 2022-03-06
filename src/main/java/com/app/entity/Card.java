package com.app.entity;

import com.app.comm.DataTable;

/**
 * 身份证表
 * @author app
 */
@DataTable("t_card")
public class Card {

	/** 主键ID  */
	private String objid;
	/** 姓名  */
	private String f_name;
	/** 身份证号码  */
	private String f_id;
	/** 剩余使用次数  */
	private String f_size;
	/** 已使用次数  */
	private String f_use_size;

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
	/** 获取 姓名  */
	public String getF_name() {
		if("Y".equals(this.nulls)) {if(this.f_name==null) {return "";}}
		return f_name;
	}
	/** 设置 姓名  */
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	/** 获取 身份证号码  */
	public String getF_id() {
		if("Y".equals(this.nulls)) {if(this.f_id==null) {return "";}}
		return f_id;
	}
	/** 设置 身份证号码  */
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}
	/** 获取 剩余使用次数  */
	public String getF_size() {
		if("Y".equals(this.nulls)) {if(this.f_size==null) {return "";}}
		return f_size;
	}
	/** 设置 剩余使用次数  */
	public void setF_size(String f_size) {
		this.f_size = f_size;
	}
	/** 获取 已使用次数  */
	public String getF_use_size() {
		if("Y".equals(this.nulls)) {if(this.f_use_size==null) {return "";}}
		return f_use_size;
	}
	/** 设置 已使用次数  */
	public void setF_use_size(String f_use_size) {
		this.f_use_size = f_use_size;
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
