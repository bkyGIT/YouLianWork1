package com.yl.transaction.dept.service;

import java.util.List;
import java.util.Map;

public interface DeptService {

	public List<Map<String, String>> getPageDept(Map<String, Object>param);
	
	public void addDept(Map<String, String> param);
	
	public void delDeptInIDS(String ids);
	
	public void updateDeptByID(Map<String, String> param);
	
	public List<Map<String, String>> getPersonnelByAccount(String account);
	
	public String getTotalDept();
	
	public List<Map<String, String>> getAllDept();
	
	public List<Map<String, String>> getDeptByType(Map<String, String> param);
	
	public List<Map<String, String>> getOnlineUserByDeptCode(Map<String, String> param);
	
	public List<Map<String, String>> isAdminAccept(Map<String, String> param);
}
