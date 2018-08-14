package com.yl.transaction.weixin.service;

import java.util.List;
import java.util.Map;

public interface WeixinService {
	public List<Map<String, String>> getCirType(Map<String, String> param);
	
	public void setCirType(Map<String, String> param);
	
	public void updateCirType(Map<String, String> param);
}
