package com.yl.transaction.test.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yl.common.dao.PublicDao;
import com.yl.transaction.test.pojo.Test;
import com.yl.transaction.test.service.TestService;
 
@Controller  
@RequestMapping("/user")
public class TestController {
	@Resource
	private TestService testService;
	
	@Resource
	private PublicDao publicDao;

	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request, Model model) {
		String userId = request.getParameter("id");
		Test test = testService.getUserById(userId);
		model.addAttribute("user", test);
		return "test/test";
	}

}
