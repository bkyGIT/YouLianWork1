package com.yl.login.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.common.user.pojo.User;
import com.yl.login.dao.LoginDao;
import com.yl.login.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	@Resource  
	private LoginDao loginDao; 
	
	public User getUserLogin(Map<String, String> param) {
		return loginDao.getUserLogin(param);
	}

	@Override
	public void updateUserOnline(Map<String, String> param) {
		loginDao.updateUserOnline(param);
	}

}
