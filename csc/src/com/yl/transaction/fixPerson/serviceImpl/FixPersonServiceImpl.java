package com.yl.transaction.fixPerson.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.fixPerson.dao.FixPersonDao;
import com.yl.transaction.fixPerson.service.FixPersonService;

@Service("fixPersonService")
public class FixPersonServiceImpl implements FixPersonService {
	
	@Resource
	private FixPersonDao fixPersonDao;

	@Override
	public String getFixTypeBR(Map<String, Object> param) {
		return fixPersonDao.getFixTypeBR(param);
	}

	@Override
	public String getFixTypeLS(Map<String, Object> param) {
		return fixPersonDao.getFixTypeLS(param);
	}

	@Override
	public String getFixDgreeGood(Map<String, Object> param) {
		return fixPersonDao.getFixDgreeGood(param);
	}

	@Override
	public String getFixDgreeOk(Map<String, Object> param) {
		return fixPersonDao.getFixDgreeOk(param);
	}

	@Override
	public String getFixDgreeNo(Map<String, Object> param) {
		return fixPersonDao.getFixDgreeNo(param);
	}

	@Override
	public List<Map<String, String>> getNameList(Map<String, Object> param) {
		return fixPersonDao.getNameList(param);
	}

	@Override
	public List<Map<String, Object>> getOrderNumByTime(Map<String, Object> param) {
		return fixPersonDao.getOrderNumByTime(param);
	}

	@Override
	public List<Map<String, Object>> getAccessOrderNumByTime(
			Map<String, Object> param) {
		return fixPersonDao.getAccessOrderNumByTime(param);
	}

	@Override
	public List<Map<String, Object>> getUnAccessOrderNumByTime(
			Map<String, Object> param) {
		return fixPersonDao.getUnAccessOrderNumByTime(param);
	}

	@Override
	public List<Map<String, Object>> getorderDegreeUnsatisfied(
			Map<String, Object> param) {
		return fixPersonDao.getorderDegreeUnsatisfied(param);
	}

	@Override
	public List<Map<String, Object>> getKFreview(Map<String, Object> param) {
		return fixPersonDao.getKFreview(param);
	}

	@Override
	public List<Map<String, Object>> getWaitSendOrder(Map<String, Object> param) {
		return fixPersonDao.getWaitSendOrder(param);
	}

}
