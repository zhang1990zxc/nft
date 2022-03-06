package com.app.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.hutool.json.JSONUtil;

/**
 * 实体类映射数据操作类
 * @author app
 * @date 2020-8-26 16:02:27
 */
public class EntityDao {
	
	private BaseDao baseDao;
	private String dataBaseName;
	
	/**
	 * 数据操作类型，数据库名
	 * @param baseDao
	 * @param dataBaseName
	 */
	public EntityDao(BaseDao baseDao, String dataBaseName) {
		super();
		this.baseDao = baseDao;
		this.dataBaseName = dataBaseName;
	}
	
	/**
	 * 查询sql
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ArrayList<HashMap<String, Object>> findBySql(String sql, Object obj[]) throws Exception {
		ArrayList<HashMap<String,Object>> list = baseDao.findBySql(sql, obj);
		return list;
	}
	
	/**
	 * 通过请求对象自动获取创建
	 * @param request
	 */
	/*
	public EntityDao(HttpServletRequest request) {
		super();
		this.baseDao = CommService.getBaseDao(request);
		this.dataBaseName = CommService.getDataBaseName(request);
	}
	*/
	
	/**
	 * 根据ID查询出实体类(单个)
	 * @param <T>
	 * @param id 数据ID
	 * @param clazz 实体类型
	 * @return
	 */
	@SuppressWarnings({"hiding"})
	public <T> T findEntityById(String id, Class<T> clazz) {
		try {
            if (clazz.isAnnotationPresent(DataTable.class)) {
            	DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
            	String tableName = info.value();
            	String sql = "select * from %s.%s where id=?";
            	sql = String.format(sql,dataBaseName,tableName);
            	ArrayList<HashMap<String,Object>> list = baseDao.findBySql(sql, new String[]{id});
            	if(list.size()!=1) {
            		return null;
            	}
            	HashMap<String, Object> map = list.get(0);
            	T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
            	return t;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据ID查询出实体类(单个)
	 * @param <T>
	 * @param objId 数据objid
	 * @param clazz 实体类型
	 * @return
	 */
	@SuppressWarnings({"hiding"})
	public <T> T findEntityByObjId(String objId, Class<T> clazz) {
		try {
            if (clazz.isAnnotationPresent(DataTable.class)) {
            	DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
            	String tableName = info.value();
            	String sql = "select * from %s.%s where objid=?";
            	sql = String.format(sql,dataBaseName,tableName);
            	ArrayList<HashMap<String,Object>> list = baseDao.findBySql(sql, new String[]{objId});
            	if(list.size()!=1) {
            		return null;
            	}
            	HashMap<String, Object> map = list.get(0);
            	T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
            	return t;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询实体类
	 * @param <T>
	 * @param sql 三种形式(select * ...)(where ...)(from ...)会自动给你补全前面的参数 $tableVar 这个变量会帮你转换成数据库和表名
	 * @param obj 动态参数
	 * @param clazz 实体类型
	 * @return
	 */
	@SuppressWarnings({"hiding"})
	public <T> List<T> findEntity(String sql, Object[] obj, Class<T> clazz) {
		try {
			if (clazz.isAnnotationPresent(DataTable.class)) {
            	DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
            	String tableName = info.value();
            	
            	//sql补充
    			if(sql.trim().startsWith("where ")) {
    				sql = "select * from "+dataBaseName+"."+tableName+" "+sql;
    			}else  if(sql.trim().startsWith("from ")) {
    				sql = "select * "+sql;
    			}
    			
    			//数据库表名转换
    			if(sql.contains("$tableVar")) {
    				sql = sql.replace("$tableVar", dataBaseName+"."+tableName);
    			}
    			
            	ArrayList<HashMap<String,Object>> list = baseDao.findBySql(sql, obj);
            	//System.out.println(sql);
            	ArrayList<T> arrayList = new ArrayList<T>();
            	for (int i = 0; i < list.size(); i++) {
            		HashMap<String, Object> map = list.get(i);
                	T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
                	arrayList.add(t);
    			}
            	return arrayList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询实体类(只查询objid列)
	 * @param <T>
	 * @param sql 三种形式(select * ...)(where ...)(from ...)会自动给你补全前面的参数
	 * @param obj 动态参数
	 * @param clazz 实体类型
	 * @return
	 */
	@SuppressWarnings({"hiding"})
	public <T> List<T> findEntityForObj(String sql, Object[] obj, Class<T> clazz) {
		try {
			if (clazz.isAnnotationPresent(DataTable.class)) {
            	DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
            	String tableName = info.value();
            	
            	//sql补充
    			if(sql.trim().startsWith("where ")) {
    				sql = "select objid from "+dataBaseName+"."+tableName+" "+sql;
    			}
    			if(sql.trim().startsWith("from ")) {
    				sql = "select objid "+sql;
    			}
    			
            	ArrayList<HashMap<String,Object>> list = baseDao.findBySql(sql, obj);
            	ArrayList<T> arrayList = new ArrayList<T>();
            	for (int i = 0; i < list.size(); i++) {
            		HashMap<String, Object> map = list.get(i);
                	T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
                	arrayList.add(t);
    			}
            	return arrayList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 分页查询
	 * @param <T>
	 * @param sql 三种形式(select * ...)(where ...)(from ...)会自动给你补全前面的参数
	 * @param obj 动态参数
	 * @param page 当前页
	 * @param Pageize 每页显示条数
	 * @param clazz 实体类型
	 * @return
	 */
	@SuppressWarnings({"hiding"})
	public <T> List<T> findEntity(String sql, Object[] obj, int page, int Pageize, Class<T> clazz) {
		try {
			if (clazz.isAnnotationPresent(DataTable.class)) {
            	DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
            	String tableName = info.value();
            	
            	//sql补充
    			if(sql.trim().startsWith("where ")) {
    				sql = "select * from "+dataBaseName+"."+tableName+" "+sql;
    			}
    			if(sql.trim().startsWith("from ")) {
    				sql = "select * "+sql;
    			}
    			
    			//开始条数
    			int startSize = (page-1)*Pageize;
            	ArrayList<HashMap<String,Object>> list = baseDao.findBySql(sql+" limit "+startSize+","+Pageize+" ", obj);
            	ArrayList<T> arrayList = new ArrayList<T>();
            	for (int i = 0; i < list.size(); i++) {
            		HashMap<String, Object> map = list.get(i);
                	T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
                	arrayList.add(t);
    			}
            	return arrayList;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新实体对象
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T updateEntity(T entity) {
		BeanToSqlFactory<T> ci = new BeanToSqlFactory<T>();
		String update = ci.toUpdate(entity, dataBaseName);
		try {
			baseDao.updateBySql(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 批量更新对象
	 * @param entity
	 */
	@SuppressWarnings("hiding")
	public <T> void updateListEntity(List<T> list) {
		BeanToSqlFactory<T> ci = new BeanToSqlFactory<T>();
		ArrayList<String> sqls = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			String update = ci.toUpdate(t, dataBaseName);
			sqls.add(update);
		}
		try {
			baseDao.updateBySqlAll(sqls, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存实体对象
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T saveEntity(T entity) {
		BeanToSqlFactory<T> ci = new BeanToSqlFactory<T>();
		String update = ci.toInsert(entity, dataBaseName);
		try {
			baseDao.updateBySql(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 批量保存对象
	 * @param list
	 */
	@SuppressWarnings("hiding")
	public <T> void saveListEntity(List<T> list) {
		BeanToSqlFactory<T> ci = new BeanToSqlFactory<T>();
		ArrayList<String> sqls = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			String update = ci.toInsert(t, dataBaseName);
			sqls.add(update);
		}
		try {
			baseDao.updateBySqlAll(sqls, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T deleteEntity(T entity) {
		BeanToSqlFactory<T> ci = new BeanToSqlFactory<T>();
		String update = ci.toDelte(entity, dataBaseName);
		try {
			baseDao.updateBySql(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 更新数据
	 * @param sql
	 * @param obj
	 */
	public void updateSql(String sql,Object obj[]){
		try {
			baseDao.updateBySql(sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新数据
	 * @param sql
	 */
	public void updateSql(String sql){
		try {
			baseDao.updateBySql(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新操作对象SQL(具有sql自动补全的功能)
	 * @param sql 形式(原生sql)(更新 set ...)(删除 where ...)
	 * @param obj 形参
	 * @param type（delete,update）自动补全sql用到
	 * @param clazz 类型
	 */
	@SuppressWarnings("hiding")
	public <T> void updateEntitySql(String sql, Object obj[], String type, Class<T> clazz){
		try {
			if (clazz.isAnnotationPresent(DataTable.class)) {
            	DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
            	String tableName = info.value();
            	
            	//sql补充
            	if("update".equals(type)) {
            		if(sql.trim().startsWith("set ")) {
        				sql = "update "+dataBaseName+"."+tableName+" "+sql;
        			}
            	}else if("delete".equals(type)) {
            		if(sql.trim().startsWith("where ")) {
        				sql = "delete from "+dataBaseName+"."+tableName+" "+sql;
        			}
            	}else {
            		return;
            	}
    			baseDao.updateBySql(sql, obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询分页数据[有参数的情况]
	 * @param tableName 表名
	 * @param lastWhere 结尾的查询条件[没有就传null对象][find='value'][注意sql注入]
	 * @param lastSQL 结尾排序条件
	 * @param findName 过滤字段名
	 * @param value	过滤字段值
	 * @param sizePage 分页大小值
	 * @param Page 分页数
	 * @param like 是否模糊搜索
	 * @return List<HashMap<String,Object>>数据table
	 */
	 public TableData getTableDatas(String tableName, String lastWhere, String lastSQL, String findName, String value, int sizePage, int Page,boolean like) {
		//查询数据条数
		String sql_zs = "select count(*) as zs from %s.%s where %s like ? %s %s";
		String database = dataBaseName;
		//lastWhere处理
		if(lastWhere==null){
			lastWhere="";
		}else{
			lastWhere = " and "+lastWhere+" ";
		}
		if(lastSQL==null){
			lastSQL="";
		}
		//模糊搜索处理
		if(like) {
			value = "%"+value+"%";
		}
		sql_zs = String.format(sql_zs, database,tableName,findName,lastWhere,lastSQL);
		List<HashMap<String, Object>> list_zs = new ArrayList<HashMap<String, Object>>();
		try {
			list_zs = baseDao.findBySql(sql_zs, new String[] {value});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int size = Integer.parseInt(list_zs.get(0).get("zs").toString());
		//创建表格对象
		TableData table = new TableData(size, sizePage, Page);
		//查询表格值
		String sql = "select * from %s.%s where %s like ? %s %s limit %s,%s";
		sql = String.format(sql, database,tableName,findName,lastWhere,lastSQL,table.getStartSize(),table.getSizePage());
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			list = baseDao.findBySql(sql, new String[] {value});
		} catch (Exception e) {
			e.printStackTrace();
		}
		table.setData(list);
		return table;
	 }

	/**
	 * 查询分页数据[无参数过滤,查询全部分页数据]
	 * @param tableName 表名
	 * @param lastWhere 结尾的查询条件[没有就传null对象][find='value'][注意sql注入]
	 * @param lastSQL 结尾排序条件
	 * @param sizePage 分页大小值
	 * @param Page 分页数
	 * @return Table对象
	 */
	public TableData getTableData(String tableName, String lastWhere, String lastSQL, int sizePage, int Page) {
		//查询数据条数
		String sql_zs = "select count(*) as zs from %s.%s %s %s";
		//lastWhere处理
		if(lastWhere==null){
			lastWhere="";
		}else{
			lastWhere = " where "+lastWhere+" ";
		}
		if(lastSQL==null){
			lastSQL="";
		}
		String database = dataBaseName;
		sql_zs = String.format(sql_zs, database,tableName,lastWhere,lastSQL);
		List<HashMap<String, Object>> list_zs = new ArrayList<HashMap<String, Object>>();
		try {
			list_zs = baseDao.findBySql(sql_zs);
		} catch (Exception e) {
			System.out.println("==>>执行SQL出现问题...");
			System.out.println(sql_zs);
			e.printStackTrace();
		}
		int size = Integer.parseInt(list_zs.get(0).get("zs").toString());
		//创建表格对象
		TableData table = new TableData(size, sizePage, Page);
		//查询表格值
		String sql = "select * from %s.%s %s %s limit %s,%s";
		sql = String.format(sql, database,tableName,lastWhere,lastSQL,table.getStartSize(),table.getSizePage());
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			list = baseDao.findBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		table.setData(list);
		return table;
	}
	
	/**
	 * 查询分页数据[有参数的情况]
	 * @param lastWhere 结尾的查询条件[没有就传null对象][find='value'][注意sql注入]
	 * @param lastSQL 结尾排序条件
	 * @param findName 过滤字段名
	 * @param value	过滤字段值
	 * @param sizePage 分页大小值
	 * @param Page 分页数
	 * @param like 是否模糊搜索
	 * @return Page对象
	 */
	 @SuppressWarnings("hiding")
	public <T> PageData getTablePage(String lastWhere, String lastSQL, String findName, String value, int sizePage, int Page,boolean like, Class<T> clazz) {
		//通过反射获取表名
		String tableName = "";
		if (clazz.isAnnotationPresent(DataTable.class)) {
	        DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
	        tableName = info.value();
	    }
		
		//查询数据条数
		String sql_zs = "select count(*) as zs from %s.%s where %s like ? %s %s";
		String database = dataBaseName;
		//lastWhere处理
		if(lastWhere==null){
			lastWhere="";
		}else{
			lastWhere = " and "+lastWhere+" ";
		}
		if(lastSQL==null){
			lastSQL="";
		}
		//模糊搜索处理
		if(like) {
			value = "%"+value+"%";
		}
		sql_zs = String.format(sql_zs, database,tableName,findName,lastWhere,lastSQL);
		List<HashMap<String, Object>> list_zs = new ArrayList<HashMap<String, Object>>();
		try {
			list_zs = baseDao.findBySql(sql_zs, new String[] {value});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int size = Integer.parseInt(list_zs.get(0).get("zs").toString());
		//创建表格对象
		PageData table = new PageData(size, sizePage, Page);
		//查询表格值
		String sql = "select * from %s.%s where %s like ? %s %s limit %s,%s";
		sql = String.format(sql, database,tableName,findName,lastWhere,lastSQL,table.getStartSize(),table.getSizePage());
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			list = baseDao.findBySql(sql, new String[] {value});
			List<Object> arrayList = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String,Object> map = list.get(i);
				T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
            	arrayList.add(t);
			}
			table.setData(arrayList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	 }

	/**
	 * 查询分页数据[无参数过滤,查询全部分页数据]
	 * @param lastWhere 结尾的查询条件[没有就传null对象][find='value'][注意sql注入]
	 * @param lastSQL 结尾排序条件
	 * @param sizePage 分页大小值
	 * @param Page 分页数
	 * @return Page对象
	 */
	@SuppressWarnings("hiding")
	public <T> PageData getTablePage(String lastWhere, String lastSQL, int sizePage, int Page, Class<T> clazz) {
		//通过反射获取表名
		String tableName = "";
		if (clazz.isAnnotationPresent(DataTable.class)) {
	        DataTable info = (DataTable) clazz.getAnnotation(DataTable.class);
	        tableName = info.value();
	    }
		
		//查询数据条数
		String sql_zs = "select count(*) as zs from %s.%s %s %s";
		//lastWhere处理
		if(lastWhere==null){
			lastWhere="";
		}else{
			lastWhere = " where "+lastWhere+" ";
		}
		if(lastSQL==null){
			lastSQL="";
		}
		String database = dataBaseName;
		sql_zs = String.format(sql_zs, database,tableName,lastWhere,lastSQL);
		List<HashMap<String, Object>> list_zs = new ArrayList<HashMap<String, Object>>();
		try {
			list_zs = baseDao.findBySql(sql_zs);
		} catch (Exception e) {
			System.out.println("==>>执行SQL出现问题...");
			System.out.println(sql_zs);
			e.printStackTrace();
		}
		int size = Integer.parseInt(list_zs.get(0).get("zs").toString());
		//创建表格对象
		PageData table = new PageData(size, sizePage, Page);
		//查询表格值
		String sql = "select * from %s.%s %s %s limit %s,%s";
		sql = String.format(sql, database,tableName,lastWhere,lastSQL,table.getStartSize(),table.getSizePage());
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			list = baseDao.findBySql(sql);
			List<Object> arrayList = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String,Object> map = list.get(i);
				T t = JSONUtil.toBean(JSONUtil.toJsonStr(map), clazz);
            	arrayList.add(t);
			}
			table.setData(arrayList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 获取数据库操作对象
	 * @return
	 */
	public BaseDao getBaseDao() {
		return baseDao;
	}
	/**
	 * 获取默认数据库名
	 * @return
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}

}
