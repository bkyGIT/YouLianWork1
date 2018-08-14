package com.yl.transaction.xq.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.xq.dao.XqDao;
import com.yl.transaction.xq.service.XqService;

@Service("xqService")
public class XqServiceImpl implements XqService{

	@Resource
	private XqDao xqDao;
	public List<Map<String, String>> getAllXqInfo() {
		return xqDao.getAllXqInfo();
	}

}
