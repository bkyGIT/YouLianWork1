package com.yl.transaction.trun.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.trun.dao.TrunDao;
import com.yl.transaction.trun.service.TrunService;

@Service("trunService")
public class TrunServiceImpl implements TrunService{

	@Resource
	private TrunDao trunDao;
	
	@Override
	public List<Map<String, String>> getTrunPhoneList(Map<String, Object> param) {
		return trunDao.getTrunPhoneList(param);
	}

	@Override
	public void addConnInfo(Map<String, String> param) {
		trunDao.addConnInfo(param);
	}

	@Override
	public void modifyConnInfoByMax(Map<String, String> param) {
		trunDao.modifyConnInfoByMax(param);
	}

	@Override
	public void delConnInfoInMax(Map<String, Object> param) {
		trunDao.delConnInfoInMax(param);
	}

}
