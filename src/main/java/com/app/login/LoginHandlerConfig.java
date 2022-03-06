package com.app.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.comm.BaseDao;

/**
 * 拦截器注册
 * @author app
 */
@Configuration
public class LoginHandlerConfig implements WebMvcConfigurer {
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 默认首页
	 */
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //设置对"/"的请求映射到index
        //如果没有数据返回到页面，没有必要用控制器方法对请求进行映射
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //SpringMVC下，拦截器的注册需要排除对静态资源的拦截(*.css,*.js)
        //SpringBoot已经做好了静态资源的映射，因此我们无需任何操作
        registry.addInterceptor(new LoginHandlerInterceptor(baseDao))
        		.addPathPatterns("/**")
                .excludePathPatterns(
                		"/common/**",
                		"/img/**",
                		"/face/**");
    }
    
}
