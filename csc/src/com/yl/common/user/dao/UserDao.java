package com.yl.common.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yl.common.user.pojo.UserView;

public interface UserDao {

	public UserView getUserView(@Param("maxaccept") String maxaccept);
	
	public List<Map<String, String>> getUserByParm(Map<String, String> param);
	
	public List<Map<String, String>> getUserInfoByRoleAndDept(Map<String, String> param);
}
