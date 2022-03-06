package com.app.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作数据库工具类（默认数据源）
 * @author app
 * @date 2020-8-26 15:59:27
 */
public class BaseDao {
	/**
	 * 注入数据源1
	 */
	@Autowired
    private BasicDataSource dataSource1;
	
	public BaseDao(BasicDataSource dataSource1) {
		super();
		this.dataSource1 = dataSource1;
	}

	/**
	 * 通过JDBC执行update语句
	 * @param String sql 可以填写？占位符防止sql注入
	 * @param Object params 占位符填充变量，可多个
	 * @return int 受到影响的数据条数
	 * @throws Exception 
	 */
	public int updateBySql(String sql,Object obj[]) throws Exception{
		Connection connection = null;
		PreparedStatement prest = null;
        try {
            // 从数据库连接池中获取数据库连接
        	connection = dataSource1.getConnection();
        	// 设置事务不自动提交，用于异常回滚
        	connection.setAutoCommit(false);
        	// 先对sql预编译 拿到PrepareStatement对象
        	prest = connection.prepareStatement(sql);
        	// 循环给语句中的？赋值
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    prest.setObject(i+1, obj[i]);
                }
            }
            // 执行查询操作
            int count = prest.executeUpdate();
            // 提交事务
            connection.commit();
            return count;
        } catch (Exception e) {
        	if (connection!=null) {
                connection.rollback();
            }
            e.printStackTrace();
            System.out.println(sql);
            System.out.println(obj);
            throw e;
        } finally {
            if(connection!=null) {
                connection.close();
            }
            if(prest!=null) {
            	prest.close();
            }
        }
	}
	
	/**
	 * 更新数据库
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int updateBySql(String sql) throws Exception{
		int updateBySql = updateBySql(sql,null);
		return updateBySql;
	}
	
	/**
	 * 批量执行update的sql语句
	 * @param list sql集合
	 * @param args 参数集合
	 * @return
	 * @throws Exception
	 */
	public int updateBySqlAll(ArrayList<String> list, ArrayList<Object[]> args) throws Exception{
		Connection connection = null;
		PreparedStatement prest = null;
		int all_count = 0;
        try {
            // 从数据库连接池中获取数据库连接
        	connection = dataSource1.getConnection();
        	// 设置事务不自动提交，用于异常回滚
        	connection.setAutoCommit(false);
        	//======================
        	//批量执行
        	for (int i = 0; i < list.size(); i++) {
				// 先对sql预编译 拿到PrepareStatement对象
	        	prest = connection.prepareStatement(list.get(i));
	        	// 循环给语句中的？赋值
	        	if(args!=null) {
	        		Object[] obj = args.get(i);
		            if (obj != null) {
		                for (int j = 0; j < obj.length; j++) {
		                    prest.setObject(j+1, obj[j]);
		                }
		            }
	        	}
	            // 执行查询操作
	            int count = prest.executeUpdate();
	            all_count+=count;
			}
        	//======================
            // 提交事务
            connection.commit();
            return all_count;
        } catch (Exception e) {
        	if (connection!=null) {
                connection.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if(connection!=null) {
                connection.close();
            }
            if(prest!=null) {
            	prest.close();
            }
        }
	}
	
	/**
	 * 查询
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ArrayList<HashMap<String, Object>> findBySql(String sql) throws Exception {
		ArrayList<HashMap<String,Object>> list = findBySql(sql, null);
		return list;
	}
	
	/**
     * 封装查询的方法
     * @param sql 查询的sql语句
     * @param obj 语句中问号的设置的值的数组
     * @return rs 返回查询结果
	 * @throws Exception 
     */
    public ArrayList<HashMap<String, Object>> findBySql(String sql, Object obj[]) throws Exception {
        Connection connection = null;
        PreparedStatement prest = null;
        try {
            // 从数据库连接池中获取数据库连接
        	connection = dataSource1.getConnection();
        	// 先对sql预编译 拿到PrepareStatement对象
        	prest = connection.prepareStatement(sql);
        	// 循环给语句中的？赋值
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    prest.setObject(i + 1, obj[i]);
                }
            }
            // 执行查询操作
            ResultSet rs = prest.executeQuery();
            ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();//获得列数 
    		while (rs.next()) {
    			HashMap<String,Object> rowData = new HashMap<String,Object>();
    			for (int i = 1; i <= columnCount; i++) {
    				rowData.put(md.getColumnName(i), rs.getObject(i));
    			}
    			list.add(rowData);
    		}
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        	if(connection!=null) {
                connection.close();
            }
            if(prest!=null) {
            	prest.close();
            }
        }
    }
	
}
