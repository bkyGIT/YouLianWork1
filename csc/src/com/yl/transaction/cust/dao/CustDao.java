package com.yl.transaction.cust.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CustDao {

	public Map<String, String> getCustInfoByID(@Param("custID") String custID);
}
