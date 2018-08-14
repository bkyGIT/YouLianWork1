package com.yl.transaction.cust.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.cust.dao.CustDao;
import com.yl.transaction.cust.service.CustService;

@Service("custService")
public class CustServiceImpl implements CustService{

	@Resource
	private CustDao custDao;
	
	public Map<String, String> getCustInfoByID(String custID) {
		return custDao.getCustInfoByID(custID);
	}

}
