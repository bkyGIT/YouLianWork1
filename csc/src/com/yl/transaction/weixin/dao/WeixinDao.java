package com.yl.transaction.weixin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WeixinDao {

	public List<Map<String, String>> getCirType(Map<String, String> param);
	
	public void setCirType(Map<String, String> param);
	
	public void updateCirType(Map<String, String> param);
}
