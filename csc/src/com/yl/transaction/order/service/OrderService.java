package com.yl.transaction.order.service;

import java.util.List;
import java.util.Map;


public interface OrderService {

	public List<Map<String, String>> getAllOrder(Map<String, Object> param);
	
	public List<Map<String, String>> getAllQueryOrder(Map<String, Object> param);
	
	public List<Map<String, String>> getAllWeixinQueryOrder(Map<String, Object> param);

	public void addOrder(Map<String, String> param);

	public void updateOrderByID(Map<String, String> param);

	public List<Map<String, String>> getOrderByAccount(String account);

	public String getTotalOrder(Map<String, Object> param);

	public List<Map<String, String>> getOrderTypeList();

	public List<Map<String, String>> getFixTypeList();

	public List<Map<String, String>> getOrderStatus();

	public List<Map<String, String>> getOrderDgree();

	public void removeOrder(String[] ids);
	
	public void sendToFixInIds(String[] ids,String fix_dept_id);
	
	public void overOrderInIds(String[] ids);

	public List<Map<String, Object>> getFixdeptidList();
	
	public void updateOrderStatus(Map<String, String> param);

	public List<Map<String, String>> getCommonCode(Map<String, String> param);
	
	public void revewOrderByID(Map<String, String> param);

	public List<Map<String, String>> getOrderPhone();
	

	public List<Map<String, Object>> getOrderNumByTime(Map<String, Object> param);

	public List<Map<String, String>> getUserByDeptType(Map<String, String> param);

	public List<Map<String, Object>> getAccessOrderNumByTime(Map<String, Object> param);

	public List<Map<String, Object>> getUnAccessOrderNumByTime(Map<String, Object> param);

	public void addOrderOprInfoByID(Map<String, String> param);

	public void insertWeixinOrder(Map<String, String> param);

	public List<Map<String, Object>> getorderDegreeUnsatisfied(Map<String, Object> param);
	
	public void insertCodeNum(Map<String, String> param);
	
	public List<Map<String, String>> getCodeNum(Map<String, Object> param);
	
	public List<Map<String, String>> getAdviseData(Map<String, String> param);

	public List<Map<String, String>> getDgreeList();

	public List<Map<String, String>> getDeptList();
	
	public List<Map<String, String>> getSonUserByDeptCode(Map<String, String> param);
	
	public List<Map<String, String>> getAllReviewOrder(Map<String, Object> param);

	public List<Map<String, Object>> returnOrderList(Map<String, Object> param);

	public void returnOrderInIds(String[] ids); 
}
