package com.yl.transaction.conver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.conver.dao.ConverDao;
import com.yl.transaction.conver.service.ConverService;

@Service("converService")
public class ConverServiceImpl implements ConverService{

	@Resource
	private ConverDao converDao;
	@Override
	public List<Map<String, String>> getAllConverList(Map<String, Object> param) {
		return converDao.getAllConverList(param);
	}

}
