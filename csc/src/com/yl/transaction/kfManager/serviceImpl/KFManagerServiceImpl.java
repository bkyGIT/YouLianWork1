package com.yl.transaction.kfManager.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.kfManager.dao.KFManagerDao;
import com.yl.transaction.kfManager.service.KFManagerService;

@Service("kfManagerService")
public class KFManagerServiceImpl implements KFManagerService {
	
	@Resource
	public KFManagerDao kfManagerDao;

	@Override
	public List<Map<String, Object>> getOrderCount(Map<String, Object> param) {
		return kfManagerDao.getOrderCount(param);
	}

	@Override
	public List<Map<String, Object>> getPhoneTime(Map<String, Object> param) {
		return kfManagerDao.getPhoneTime(param);
	}

//	@Override
//	public String getPhoneGood(Map<String, Object> param) {
//		return kfManagerDao.getPhoneGood(param);
//	}
//
//	@Override
//	public String getPhoneOk(Map<String, Object> param) {
//		return kfManagerDao.getPhoneOk(param);
//	}
//
//	@Override
//	public String getPhoneNo(Map<String, Object> param) {
//		return kfManagerDao.getPhoneNo(param);
//	}

	@Override
	public String getConverAccess(Map<String, Object> param) {
		return kfManagerDao.getConverAccess(param);
	}

	@Override
	public String getConverUnAccess(Map<String, Object> param) {
		return kfManagerDao.getConverUnAccess(param);
	}

	@Override
	public List<Map<String, String>> getNameList(Map<String, Object> param) {
		return kfManagerDao.getNameList(param);
	}

	@Override
	public List<Map<String, Object>> getWaitSend(Map<String, Object> param) {
		return kfManagerDao.getWaitSend(param);
	}

	@Override
	public List<Map<String, Object>> getCancelOrder(Map<String, Object> param) {
		return kfManagerDao.getCancelOrder(param);
	}

	@Override
	public List<Map<String, Object>> getWaitFix(Map<String, Object> param) {
		return kfManagerDao.getWaitFix(param);
	}

	@Override
	public List<Map<String, Object>> getWaitReview(Map<String, Object> param) {
		return kfManagerDao.getWaitReview(param);
	}

	@Override
	public List<Map<String, Object>> getAccessOrderNumByTime(
			Map<String, Object> param) {
		return kfManagerDao.getAccessOrderNumByTime(param);
	}

	@Override
	public String getOrderZX(Map<String, Object> param) {
		return kfManagerDao.getOrderZX(param);
	}

	@Override
	public String getOrderWH(Map<String, Object> param) {
		return kfManagerDao.getOrderWH(param);
	}

	@Override
	public String getOrderJY(Map<String, Object> param) {
		return kfManagerDao.getOrderJY(param);
	}

	@Override
	public String getOrderTS(Map<String, Object> param) {
		return kfManagerDao.getOrderTS(param);
	}

	@Override
	public List<Map<String, String>> getChannelList() {
		return kfManagerDao.getChannelList();
	}
	

}
