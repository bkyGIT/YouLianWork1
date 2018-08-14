package com.yl.transaction.fixPerson.dao;

import java.util.List;
import java.util.Map;

public interface FixPersonDao {

	String getFixTypeBR(Map<String, Object> param);

	String getFixTypeLS(Map<String, Object> param);

	String getFixDgreeGood(Map<String, Object> param);
	
	String getFixDgreeOk(Map<String, Object> param);
	
	String getFixDgreeNo(Map<String, Object> param);

	List<Map<String, String>> getNameList(Map<String, Object> param);

	List<Map<String, Object>> getOrderNumByTime(Map<String, Object> param);

	List<Map<String, Object>> getAccessOrderNumByTime(Map<String, Object> param);

	List<Map<String, Object>> getUnAccessOrderNumByTime(
			Map<String, Object> param);

	List<Map<String, Object>> getorderDegreeUnsatisfied(
			Map<String, Object> param);

	List<Map<String, Object>> getKFreview(Map<String, Object> param);

	List<Map<String, Object>> getWaitSendOrder(Map<String, Object> param);

}
