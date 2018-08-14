package com.yl.transaction.knowledge.service;

import java.util.List;
import java.util.Map;

public interface KnowledgeService {

	public List<Map<String, String>> getAllknowledgeType();
	
	public List<Map<String, String>> getKonwledgeByKey(String codeKey);
	
	public void addKnowledge(Map<String, String> param);
	
	public void updateKnowledgeByMaxaccept(Map<String, String> param);
	
	public void delKnowledgeByMaxaccept(String maxaccept);
}
