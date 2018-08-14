package com.yl.login.service;

import java.util.Map;
import com.yl.common.user.pojo.User;

public interface LoginService {

	public User getUserLogin(Map<String, String> param);
	
	public void updateUserOnline(Map<String, String> param);
}
