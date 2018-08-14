package com.yl.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yl.common.controller.BaseController;
import com.yl.common.user.pojo.User;
import com.yl.common.user.pojo.UserView;
import com.yl.login.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	@Resource
	private LoginService loginService;

	@RequestMapping("/userLogin")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String userName = request.getParameter("username");
		String pwd = request.getParameter("password");
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", userName);
		param.put("pwd", pwd);
		User user = loginService.getUserLogin( param);
		
		if(user != null){
			request.getSession().setAttribute("userInfo", user);
			model.addAttribute("user", user);
			response.sendRedirect("../transaction/main/main.jsp");
			
			//标注用户登录
			Map<String, String> para = new HashMap<String, String>();
			para.put("maxaccept", user.getMaxaccept());
			para.put("isOnline", "0");
			loginService.updateUserOnline(para);
			return null;
		}else{
			model.addAttribute("msg", "账号或密码错误");
			return "login";
		}
	}
	
	@RequestMapping("/cancel")
	public void cancel(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		//标注用户登录
		Map<String, String> para = new HashMap<String, String>();
		UserView user = this.getUserView(request);
		para.put("maxaccept", user.getMaxaccept());
		para.put("isOnline", "1");
		loginService.updateUserOnline(para);
		
		//移除session
		request.getSession().removeAttribute("userInfo");
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		write(response, result);
	}
}
