package com.yl.common.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yl.common.user.dao.UserDao;
import com.yl.common.user.pojo.User;
import com.yl.common.user.pojo.UserView;
import com.yl.common.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	public UserView getUserView(String maxaccept) {
		return userDao.getUserView(maxaccept);
	}

	public List<Map<String, String>> getUserByParm(Map<String, String> param) {
		return userDao.getUserByParm(param);
	}

	@Override
	public List<Map<String, String>> getUserInfoByRoleAndDept(Map<String, String> param) {
		return userDao.getUserInfoByRoleAndDept(param);
	}

}
