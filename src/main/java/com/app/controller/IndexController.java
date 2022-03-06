package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页请求控制类
 * @author app
 */
@Controller
@RequestMapping(value="/")
public class IndexController {
	
	//@Autowired
	//private BaseDao baseDao;
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/")
	public String index(Model model){
		System.out.println(111);
		return "index";
	}
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index.html")
	public String indexShow(Model model){
		System.out.println(222);
		return "index";
	}
	
}
