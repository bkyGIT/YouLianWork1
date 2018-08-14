package com.yl.transaction.fixOrder.service;

import java.util.List;
import java.util.Map;


public interface FixOrderService {

	public List<Map<String, String>> getAllOrder(Map<String, Object> param);

	public String getTotalOrder(Map<String, Object> param);

	public void sendToFix(Map<String, String> param);
	
	public void commitOrder(Map<String, String> param);
	
	public List<Map<String, String>> getFixOrderQueryList(Map<String, Object> param);

	//获取当前工单工单状态
	public List<Map<String, String>> getOldStatus(Map<String, String> paramNew);
	
	public List<Map<String, String>> queryIntervalOrder(Map<String, Object> param);

}
