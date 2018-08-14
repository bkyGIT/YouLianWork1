package com.yl.transaction.conver.dao;

import java.util.List;
import java.util.Map;

public interface ConverDao {

	public List<Map<String, String>> getAllConverList(Map<String, Object> param);
}
