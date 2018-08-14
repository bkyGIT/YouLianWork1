package com.yl.transaction.trun.service;

import java.util.List;
import java.util.Map;

public interface TrunService {

	public List<Map<String, String>> getTrunPhoneList(Map<String, Object> param);
	
	public void addConnInfo(Map<String, String> param);
	
	public void modifyConnInfoByMax(Map<String, String> param);
	
	public void delConnInfoInMax(Map<String, Object> param);
}
