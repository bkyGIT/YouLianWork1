package com.yl.transaction.knowledge.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yl.transaction.knowledge.dao.KnowledgeDao;
import com.yl.transaction.knowledge.service.KnowledgeService;

@Service("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService{

	@Resource
	private KnowledgeDao knowledgeDao;
	
	public List<Map<String, String>> getAllknowledgeType() {
		return knowledgeDao.getAllknowledgeType();
	}

	public List<Map<String, String>> getKonwledgeByKey(String codeKey) {
		return knowledgeDao.getKonwledgeByKey(codeKey);
	}

	public void addKnowledge(Map<String, String> param) {
		knowledgeDao.addKnowledge(param);
	}

	public void updateKnowledgeByMaxaccept(Map<String, String> param) {
		knowledgeDao.updateKnowledgeByMaxaccept(param);
	}

	public void delKnowledgeByMaxaccept(String maxaccept) {
		knowledgeDao.delKnowledgeByMaxaccept(maxaccept);
	}

}
