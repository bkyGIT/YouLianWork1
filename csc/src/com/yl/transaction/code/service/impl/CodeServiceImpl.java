package com.yl.transaction.code.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.code.dao.CodeDao;
import com.yl.transaction.code.service.CodeService;

@Service("codeService")
public class CodeServiceImpl implements CodeService{
	@Resource
	private CodeDao codeDao;

	@Override
	public List<Map<String, String>> getCodeList(String codeType) {
		return codeDao.getCodeList(codeType);
	}

	@Override
	public List<Map<String, String>> getCommonCode(Map<String, String> param) {
		return codeDao.getCommonCode(param);
	}
	
	@Override
	public Map<String, String> getCodename(String code_key, String code_id) {
		return codeDao.getCodename(code_key,code_id);
	}

}
