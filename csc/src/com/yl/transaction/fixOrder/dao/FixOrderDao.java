package com.yl.transaction.fixOrder.dao;

import java.util.List;
import java.util.Map;

public interface FixOrderDao {

	public List<Map<String, String>> getAllOrder(Map<String, Object> param);
	
	public String getTotalOrder(Map<String, Object> param);

	public void sendToFix(Map<String, String> param);
	
	public void commitOrder(Map<String, String> param);
	
	public List<Map<String, String>> getFixOrderQueryList(Map<String, Object> param);

	public List<Map<String, String>> getOldStatus(Map<String, String> param);

	public List<Map<String, String>> queryIntervalOrder(Map<String, Object> param);

}
