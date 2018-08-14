package com.yl.transaction.trun.dao;

import java.util.List;
import java.util.Map;

public interface TrunDao {

	public List<Map<String, String>> getTrunPhoneList(Map<String, Object> param);
	
	public void addConnInfo(Map<String, String> param);
	
	public void modifyConnInfoByMax(Map<String, String> param);
	
	public void delConnInfoInMax(Map<String, Object> param);
}
