package com.yl.transaction.knowledge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface KnowledgeDao {

	public List<Map<String, String>> getAllknowledgeType();
	
	public List<Map<String, String>> getKonwledgeByKey(@Param("codeKey") String codeKey);
	
	public void addKnowledge(Map<String, String> param);
	
	public void updateKnowledgeByMaxaccept(Map<String, String> param);
	
	public void delKnowledgeByMaxaccept(@Param("maxaccept") String maxaccept);
}
