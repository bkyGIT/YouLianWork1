package com.yl.login.dao;

import java.util.Map;
import com.yl.common.user.pojo.User;

public interface LoginDao {

	public User getUserLogin(Map<String, String> param);
	
	public void updateUserOnline(Map<String, String> param);
}
