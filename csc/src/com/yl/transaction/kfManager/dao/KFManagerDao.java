package com.yl.transaction.kfManager.dao;

import java.util.List;
import java.util.Map;

public interface KFManagerDao {

	List<Map<String, Object>> getOrderCount(Map<String, Object> param);

	List<Map<String, Object>> getPhoneTime(Map<String, Object> param);

//	String getPhoneGood(Map<String, Object> param);
//
//	String getPhoneOk(Map<String, Object> param);
//
//	String getPhoneNo(Map<String, Object> param);

	String getConverAccess(Map<String, Object> param);

	String getConverUnAccess(Map<String, Object> param);

	List<Map<String, String>> getNameList(Map<String, Object> param);
	

	List<Map<String, Object>> getWaitSend(Map<String, Object> param);

	List<Map<String, Object>> getCancelOrder(Map<String, Object> param);

	List<Map<String, Object>> getWaitFix(Map<String, Object> param);

	List<Map<String, Object>> getWaitReview(Map<String, Object> param);

	List<Map<String, Object>> getAccessOrderNumByTime(Map<String, Object> param);

	String getOrderZX(Map<String, Object> param);

	String getOrderWH(Map<String, Object> param);

	String getOrderJY(Map<String, Object> param);

	String getOrderTS(Map<String, Object> param);

	List<Map<String, String>> getChannelList();
	
}
