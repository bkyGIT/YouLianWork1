package com.yl.transaction.fixOrder.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yl.transaction.fixOrder.dao.FixOrderDao;
import com.yl.transaction.fixOrder.service.FixOrderService;

@Service("fixOrderService")
public class FixOrderServiceImpl implements FixOrderService {
	
	@Resource
	private FixOrderDao fixOrderDao;

	public List<Map<String, String>> getAllOrder(Map<String, Object> param) {
		return fixOrderDao.getAllOrder(param);
	}

	public String getTotalOrder(Map<String, Object> param) {
		return fixOrderDao.getTotalOrder(param);
	}

	public void sendToFix(Map<String, String> param) {
		fixOrderDao.sendToFix(param);
	}

	@Override
	public void commitOrder(Map<String, String> param) {
		fixOrderDao.commitOrder(param);
	}

	@Override
	public List<Map<String, String>> getFixOrderQueryList(Map<String, Object> param) {
		return fixOrderDao.getFixOrderQueryList(param);
	}

	@Override
	public List<Map<String, String>> getOldStatus(Map<String, String> param) {
		return fixOrderDao.getOldStatus(param);
	}

	@Override
	public List<Map<String, String>> queryIntervalOrder(Map<String, Object> param) {
		return fixOrderDao.queryIntervalOrder(param);
	}

}
