package com.yl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yl.common.user.pojo.User;

public class LoginInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		if(url.contains("login")){
			return true;
		}
		if(url.contains("PhoneInterface")){
			return true;
		}
		if(url.contains("weixin")){
			return true;
		}
		if(url.contains("advise")){
			return true;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {
			return true;
		} else {
			response.sendRedirect("../login.jsp");
			return false;
		}
	}

}
