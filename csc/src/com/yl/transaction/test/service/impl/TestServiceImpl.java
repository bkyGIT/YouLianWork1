package com.yl.transaction.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.test.dao.TestDao;
import com.yl.transaction.test.pojo.Test;
import com.yl.transaction.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Resource  
	private TestDao testDao; 

	public Test getUserById(String userId) {
		return this.testDao.selectByPrimaryKey(userId);
	}

}
