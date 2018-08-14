package com.yl.common.user.service;

import java.util.List;
import java.util.Map;

import com.yl.common.user.pojo.UserView;

public interface UserService {

	public UserView getUserView(String maxaccept);
	
	public List<Map<String, String>> getUserByParm(Map<String, String> param);
	
	public List<Map<String, String>> getUserInfoByRoleAndDept(Map<String, String> param);
}
