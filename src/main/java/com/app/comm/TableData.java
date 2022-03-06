package com.app.comm;

import java.util.HashMap;
import java.util.List;

/**
 * 表格对象+分页处理(基于list map保存分页数据)
 * @author app
 */
public class TableData {
	
	/** 数据表 **/
	private List<HashMap<String, Object>> data;
	/** 总条数 **/
	private int countSize;
	/** 总页数？ **/
	private int pageSize;
	/** 每页显示条数 **/
	private int sizePage;
	/** 当前页数 **/
	private int pages;
	/** 开始取数条数？ **/
	private int startSize;
	
	/**
	 * 创建分页对象
	 * @param countSize 总数据条数
	 * @param sizePage 每页显示条数
	 * @param pages 当前分页数
	 */
	public TableData(int countSize, int sizePage, int pages) {
		super();
		this.countSize = countSize;
		this.pageSize = (countSize+sizePage-1)/sizePage;
		this.sizePage = sizePage;
		this.pages = pages;
		this.startSize = (pages-1)*sizePage;
	}

	/** 设置赋值表格Data数据 **/
	public void setData(List<HashMap<String, Object>> data) {
		this.data = data;
	}
	/** 获取表格Data数据 **/
	public List<HashMap<String, Object>> getData() {
		return data;
	}

	/** 获取总数据条数**/
	public int getCountSize() {
		return countSize;
	}

	/** 获取总页数 **/
	public int getPageSize() {
		return pageSize;
	}

	/** 获取每页显示条数 **/
	public int getSizePage() {
		return sizePage;
	}

	/** 获取当前页数 **/
	public int getPages() {
		return pages;
	}

	/** 获取开始取数数值 **/
	public int getStartSize() {
		return startSize;
	}
	
	/**
	 * 设置 总条数
	 * @param countSize
	 */
	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}
	
}
