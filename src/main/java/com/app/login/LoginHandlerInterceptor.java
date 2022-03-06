package com.app.login;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.app.comm.BaseDao;

/**
 * 登录拦截器
 * @author app
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
	
	private BaseDao baseDao;

	public LoginHandlerInterceptor(BaseDao baseDao) {
		super();
		this.baseDao = baseDao;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//System.out.println("=========登录拦截器执行============");
		//===================================================================
		//获取系统对象，将BaseDao、SystemConfig放入域对象中
		ServletContext application = request.getSession().getServletContext();
		if(application.getAttribute("baseDao")==null){
			application.setAttribute("baseDao", baseDao);
		}
		//首页路径（兼容多域名）
		if(application.getAttribute("basePath")==null){
			application.setAttribute("basePath", "/");
		}
		//访问规则处理器处理
		return true;
		//===================================================================
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//System.out.println("=========访问资源时没有异常，将执行============");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//System.out.println("===============访问资源时，不论有没有异常，都将执行================");
	}

}
