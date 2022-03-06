package com.app.run;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.comm.BaseDao;
import com.app.comm.DaoTool;

public class Demo {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		//获取数据库操作组件
		String database = "db_quantity";
		BaseDao baseDao = new BaseDao(DaoTool.getDataSource127(database));

		//sql查询
		ArrayList<HashMap<String,Object>> list = baseDao.findBySql("select * from table where 1=1");
		//占位符查询（防止sql注入）
		ArrayList<HashMap<String, Object>> list2 = baseDao.findBySql("select * from table where id=? and qq=?", new String[] {"111","222"});
		
		//更新
		baseDao.updateBySql("insert .... 或者 update");
		//更新占位符
		baseDao.findBySql("insert .... 或者 update", new String[] {});
		
		//批量更新
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("insert....");
		list3.add("insert....");
		list3.add("insert....");
		list3.add("insert....");
		baseDao.updateBySqlAll(list3, null);
	}

}
