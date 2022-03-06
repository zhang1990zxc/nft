package com.app.comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 直接读取数据库，生成实体类
 * @author Ctrte
 */
public class DataBaseToEntity {

	public static void main(String[] args) throws Exception {
		//配置相关项
		String path = "D:/temp/work/";
		String packagePath = "package com.app.entity;";
		String author = "app";
		BaseDao baseDao = new BaseDao(DaoTool.getDataSourceTx("db_theone"));
		
		//业务操作项
		System.out.println("===>>数据操作开始,请耐心等待一会!");
		ArrayList<HashMap<String, Object>> list = baseDao.findBySql("show tables");
		for (int i = 0; i < list.size(); i++) {
			//获取表名
			HashMap<String,Object> map = list.get(i);
			String tableName = map.get("TABLE_NAME").toString();
			
			//转成类名
			String tableName_hub = lineToHump(tableName);
			tableName_hub = tableName_hub.substring(1, tableName_hub.length());

			//获取建表语句
			ArrayList<HashMap<String, Object>> ls1 = baseDao.findBySql("show create table "+tableName);
			String table_sql = ls1.get(0).get("Create Table").toString();
			
			//获取类描述
			String class_msg = "";
			if(true) {
				try {
					String[] split_msg = table_sql.split("\r\n");
					class_msg = split_msg[split_msg.length-1].split("COMMENT='")[1].split("'")[0];
				} catch (Exception e) {}
			}
			
			//获取基础代码并格式化
			String classContext = getClassContext(packagePath, author);
			classContext = classContext.replace("$类描述", class_msg);
			classContext = classContext.replace("$类名", tableName_hub);
			classContext = classContext.replace("$表名", tableName);
			
			//获取成员变量,成员方法,导包
			StringBuffer sb_var = new StringBuffer();
			StringBuffer sb_fun = new StringBuffer();
			StringBuffer sb_bag = new StringBuffer();
			boolean tag = false;//是否需要导入包
			if(true) {
				String[] split_msg = table_sql.split("\n");
				for (int j = 0; j < split_msg.length; j++) {
					String find = split_msg[j].trim();
					if(find.startsWith("`")) {
						String findName = find.split("`")[1];
						String findType = "String";
						if(find.contains("int(")) {
							findType = "int";
						}else if(find.contains("datetime ") || find.contains("date ")) {
							findType = "Date";
							tag = true;
						}else if(find.contains("double ") || find.contains("float ")) {
							findType = "double";
						}
						String commend = find.split("COMMENT '")[1].split("'")[0];
						
						//成员变量
						sb_var.append("	/** ").append(commend).append("  */").append("\r\n");
						//sb_var.append("	@DataTable(\""+findName+"\")").append("\r\n");
						sb_var.append("	private ").append(findType).append(" ").append(findName).append(";\r\n");
						
						//get
						if(true) {
							sb_fun.append("	/** ").append("获取 ").append(commend).append("  */").append("\r\n");
							String fun_NameUp = findName.substring(0, 1).toUpperCase()+findName.substring(1, findName.length());//字段名首字母大写转换
							sb_fun.append("	public "+findType+" get"+fun_NameUp+"() {").append("\r\n");
							if("String".equals(findType)) {//字符串类型才生效
								sb_fun.append("		if(\"Y\".equals(this.nulls)) {if(this."+findName+"==null) {return \"\";}}").append("\r\n");//为空的数据返回空字符串
							}
							sb_fun.append("		return "+findName+";").append("\r\n");
							sb_fun.append("	}").append("\r\n");
						}
						//get获取是否
						if(commend.contains("(Y,N)")) {
							sb_fun.append("	/** ").append("获取是否转义YN ").append(commend).append("  */").append("\r\n");
							String fun_NameUp = findName.substring(0, 1).toUpperCase()+findName.substring(1, findName.length())+"_yn";//字段名首字母大写转换
							sb_fun.append("	public "+findType+" get"+fun_NameUp+"() {").append("\r\n");
							sb_fun.append("		if(\"Y\".equals("+findName+")) {").append("\r\n");
							sb_fun.append("			return \"是\";").append("\r\n");
							sb_fun.append("		}else {").append("\r\n");
							sb_fun.append("			return \"否\";").append("\r\n");
							sb_fun.append("		}").append("\r\n");
							sb_fun.append("	}").append("\r\n");
						}
						//set
						if(true) {
							sb_fun.append("	/** ").append("设置 ").append(commend).append("  */").append("\r\n");
							String fun_NameUp = findName.substring(0, 1).toUpperCase()+findName.substring(1, findName.length());//字段名首字母大写转换
							sb_fun.append("	public void set"+fun_NameUp+"("+findType+" "+findName+") {").append("\r\n");
							sb_fun.append("		this."+findName+" = "+findName+";").append("\r\n");
							sb_fun.append("	}").append("\r\n");
						}
					}
				}
			}
			
			//导包
			if(tag) {
				sb_bag.append("\r\nimport java.util.Date;");
			}
			sb_bag.append("\r\nimport com.app.comm.DataTable;\r\n");
			
			//控制null变量返回空字符串
			if(true) {
				sb_var.append("\r\n");
				sb_var.append("	/** ========== 非数据库映射字段 ========== **").append("\r\n");
				sb_var.append("	/** 控制null数据返回空字符串(只针对String类型数据) **/").append("\r\n");
				sb_var.append("	private String nulls;").append("\r\n").append("\r\n");
				
				sb_fun.append("\r\n");
				sb_fun.append("	/** ========== 非数据库映射字段 ========== **").append("\r\n");
				sb_fun.append("	/** 获取 控制null数据返回空字符串(只针对String类型数据) **/").append("\r\n");
				sb_fun.append("	public String getNulls() {").append("\r\n");
				sb_fun.append("		return nulls;").append("\r\n");
				sb_fun.append("	}").append("\r\n");
				sb_fun.append("	/** 设置 控制null数据返回空字符串(只针对String类型数据) **/").append("\r\n");
				sb_fun.append("	public void setNulls(String nulls) {").append("\r\n");
				sb_fun.append("		this.nulls = nulls;").append("\r\n");
				sb_fun.append("	}").append("\r\n");
			}
			
			classContext = classContext.replace("$成员变量", sb_var.toString());
			classContext = classContext.replace("$成员方法", sb_fun.toString());
			classContext = classContext.replace("$导包", sb_bag.toString());
			
			//生成文件
			createDir(path);
			createFiles(path+tableName_hub+".java", classContext.toString());
			System.out.println("==>>"+tableName);
		}
		System.out.println("===>>数据操作结束,感谢您的使用!");
		System.out.println(path);
	}
	
	/**
	 * 类文档结构
	 * $导包,$类描述,$类名,$成员变量,$成员方法
	 * @return
	 */
	public static String getClassContext(String packagePath,String author) {
		StringBuffer sb = new StringBuffer();
		//包名
		sb.append(packagePath).append("\r\n");
		
		//导入包
		sb.append("$导包").append("\r\n");
		
		//注释
		sb.append("/**").append("\r\n");
		sb.append(" * ").append("$类描述").append("\r\n");
		sb.append(" * @author ").append(author).append("\r\n");
		//sb.append(" * @date ").append(DateUtil.getDateTime()).append("\r\n");
		sb.append(" */");sb.append("\r\n");
		//类开始
		sb.append("@DataTable(\"$表名\")").append("\r\n");//注解
		sb.append("public class $类名 {").append("\r\n").append("\r\n");
		
		//成员变量，成员方法占位符
		sb.append("$成员变量");sb.append("\r\n");
		sb.append("$成员方法");sb.append("\r\n");

		//类结束
		sb.append("}");sb.append("\r\n");
		return sb.toString();
	}
	
	/** 
	 * 下划线转驼峰
	 */
	public static String lineToHump(String str) {
		Pattern linePattern = Pattern.compile("_(\\w)");
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
 
	/** 
	 * 驼峰转下划线
	 */
	public static String humpToLine(String str) {
		return str.replaceAll("[A-Z]", "_$0").toLowerCase();
	}

	/**
     * 读取文本内容
     * @param path 路径
     */
    public static String readFileText(String path,String encoding){
    	StringBuilder sb = new StringBuilder();
    	try {
            File file=new File(path);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	sb.append(lineTxt+"\r\n");
                }
                read.close();
	    }else{
	        return null;
	    }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
		return sb.toString();
    }
    
    /**
	 * 创建带内容的文件
	 * @param fileName 文件路径
	 * @param content 文字内容
	 */
	public static void createFiles(String fileName, String content) {
		try {
			File writename = new File(fileName); // 相对路径，如果没有则要建立一个新的output.txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(content); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建文件夹
	 * @param dir 文件夹路径
	 * @return 创建状态,ok返回ok,失败返回错误信息
	 */
	public static void createDir(String dir){
        try {
            String dirTemp = dir;
            File dirPath = new File(dirTemp);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
