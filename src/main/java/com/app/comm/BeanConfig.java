package com.app.comm;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author app
 * 自动配置Bean
 */
@Configuration
public class BeanConfig {
	
	/**
	 * 配置文件
	 */
	@Autowired Environment ev;
	
	/**
	 * 创建数据源1
	 * @return
	 */
	@Bean
	public BasicDataSource dataSource1() {
		//获取配置文件
		String name = ev.getProperty("datasource1.driver-class-name");
		String url = ev.getProperty("datasource1.url");
		String username = ev.getProperty("datasource1.username");
		String password = ev.getProperty("datasource1.password");
		
		//创建数据源
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(name);
		source.setUrl(url);
		source.setUsername(username);
		source.setPassword(password);

		//【配置数据库连接池】
		//初始化连接数
		source.setInitialSize(10);
		//最大空闲连接数
		source.setMaxIdle(30);
		//最小空闲连接数
		source.setMinIdle(8);
		//超时等待时间（以毫秒为单位）20秒
		source.setMaxWaitMillis(20000);
		
		//取得连接时是否进行有效性验证（即是否还和数据库连通的）
		source.setTestOnBorrow(true);
		//返回连接时是否进行有效性验证（即是否还和数据库连通的）
		source.setTestOnReturn(true);
		//连接空闲时是否进行有效性验证（即是否还和数据库连通的）
		source.setTestWhileIdle(true);
				
		//是否自动回收超时连接
		source.setRemoveAbandonedOnBorrow(true);
		//超过时间限制，回收没有用(废弃)的连接（默认为 300秒，调整为180）
		source.setRemoveAbandonedTimeout(180);
		//连接检测sql
		source.setValidationQuery("select 1 from dual");
		
	    //配置获取连接等待超时的时间
		return source;
	}
	
	/**
	 * 注入数据源1
	 */
	@Autowired
    private BasicDataSource dataSource1;
	
	/**
	 * 创建持久化操作对象（数据源1）
	 * @return
	 */
    @Bean
    public BaseDao baseDao(){
    	BaseDao baseDao = new BaseDao(dataSource1);
		return baseDao;
    }
    
    /**
     * baseDao对象（基础数据持久操作类）
     */
    @Autowired
    private BaseDao baseDao;
    
    /**
     * 对象持久化操作类（数据源1）
     * @return
     */
    @Bean
    public EntityDao entityDao() {
    	String databaseName = ev.getProperty("datasource1.databaseName");
    	EntityDao entityDao = new EntityDao(baseDao, databaseName);
		return entityDao;
    }
    
    /**
     * 解决生产环境向客户的写cookie报错的问题
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (serverFactory) -> serverFactory.addContextCustomizers((context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }
    
}
