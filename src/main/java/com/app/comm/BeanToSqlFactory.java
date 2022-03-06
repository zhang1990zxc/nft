package com.app.comm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.utils.DateUtil;

/**
 * 将实体类转成sql
 * @author app
 * @param <T>
 */
public class BeanToSqlFactory<T> {
	
	/**
	 * 生成insert Sql
	 * @param entity 实体类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toInsert(T entity, String dataBaseName) {
	    String sql = "insert into ";
	    String column = ""; // 列
	    String c_values = ""; // 列值
	    List<Map<String, Object>> list = getFiledsInfo(entity);
	    sql += dataBaseName+"."+list.get(0).get("obj_name").toString()+" ";
	    for (int i = 0; i < list.size(); i++) {
	    	//屏蔽ID
	        if (list.get(i).get("f_name").toString() == "id") {
	        	continue;
	        }
	        //其他的配置上
	        if (list.get(i).get("f_value") != null) {
	            column += list.get(i).get("f_name") + ",";
	            c_values += "'" + list.get(i).get("f_value") + "',";
	        }
	    }
	    sql += "("+column.substring(0, column.length()-1)+") values ("+c_values.substring(0,c_values.length()-1)+")";
	    return sql;
	}
	
	/**
	 * 生成update Sql
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toUpdate(T entity, String dataBaseName) {
		String sql = "update ";
	    String column = ""; // 列
	    List<Map<String, Object>> list = getFiledsInfo(entity);
	    sql += dataBaseName+"."+list.get(0).get("obj_name").toString()+" set ";
	    String objid = "";
	    for (int i = 0; i < list.size(); i++) {
	    	//屏蔽ID
	        if (list.get(i).get("f_name").toString() == "id") {
	        	continue;
	        }
	        //屏蔽objid
	        if (list.get(i).get("f_name").toString() == "objid") {
	        	objid = list.get(i).get("f_value").toString();
	        	continue;
	        }
	        //其他的配置上
	        if (list.get(i).get("f_value") != null) {
	            column += list.get(i).get("f_name") + "=";
	            column += "'" + list.get(i).get("f_value") + "',";
	        }
	    }
	    sql += column.substring(0, column.length()-1);
	    sql += " where objid='"+objid+"'";
	    return sql;
	}
	
	/**
	 * 生成delete Sql
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toDelte(T entity, String dataBaseName) {
		String sql = "delete from ";
	    List<Map<String, Object>> list = getFiledsInfo(entity);
	    sql += dataBaseName+"."+list.get(0).get("obj_name").toString()+" where ";
	    String objid = "";
	    for (int i = 0; i < list.size(); i++) {
	        if (list.get(i).get("f_name").toString() == "objid") {
	        	objid = list.get(i).get("f_value").toString();
	        	break;
	        }
	    }
	    sql += " objid='"+objid+"'";
	    return sql;
	}
	
	/**
	 * 根据属性名获取属性值
	 **/
	protected Object getFieldValueByName(String fieldName, Object o) {
	    try {
	        String firstLetter = fieldName.substring(0, 1).toUpperCase();
	        String getter = "get" + firstLetter + fieldName.substring(1);
	        Method method = o.getClass().getMethod(getter, new Class[] {});
	        Object value = method.invoke(o, new Object[] {});
	        return value;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * 类名(obj_name)获取属性类型(f_type)，属性名(f_name)，属性值(f_value)的map组成的list
	 **/
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	protected List getFiledsInfo(Object o) {
	    String obj_name = o.getClass().getSimpleName().toString();
	    Field[] fields = o.getClass().getDeclaredFields();
	    String[] fieldNames = new String[fields.length];
	    List<Map> list = new ArrayList();
	    Map<String, Object> infoMap;
	    //获取表名
	    if (o.getClass().isAnnotationPresent(DataTable.class)) {
	        DataTable info = (DataTable) o.getClass().getAnnotation(DataTable.class);
	        obj_name = info.value();
	    }
	    //赋值
	    for (int i = 0; i < fields.length; i++) {
	        infoMap = new HashMap<String, Object>();
	        infoMap.put("obj_name", obj_name);
	        infoMap.put("f_type", fields[i].getType().toString());
	        infoMap.put("f_name", fields[i].getName());
	        //屏蔽非数据库字段nulls
	        if("nulls".equals(fields[i].getName())) {
	        	continue;
	        }
	        Object object = getFieldValueByName(fields[i].getName(), o);
	        if("class java.util.Date".equals(fields[i].getType().toString()) && object!=null) {
	        	object = DateUtil.toDateTimeFormat((Date)object);
	        }
	        infoMap.put("f_value", object);
	        list.add(infoMap);
	    }
	    return list;
	}
}
