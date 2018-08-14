package com.yl.transaction.dept.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yl.transaction.dept.dao.DeptDao;
import com.yl.transaction.dept.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService{

	@Resource
	private DeptDao deptDao;

	public List<Map<String, String>> getPageDept(Map<String, Object> param) {
		return deptDao.getPageDept(param);
	}

	public void addDept(Map<String, String> param) {
		deptDao.addDept(param);
	}

	public void delDeptInIDS(String ids) {
		deptDao.delDeptInIDS(ids);
	}

	public void updateDeptByID(Map<String, String> param) {
		deptDao.updateDeptByID(param);
	}

	public List<Map<String, String>> getPersonnelByAccount(String account) {
		return deptDao.getPersonnelByAccount(account);
	}

	public String getTotalDept() {
		return deptDao.getTotalDept();
	}

	public List<Map<String, String>> getAllDept() {
		return deptDao.getAllDept();
	}

	@Override
	public List<Map<String, String>> getDeptByType(Map<String, String> param) {
		return deptDao.getDeptByType(param);
	}

	@Override
	public List<Map<String, String>> getOnlineUserByDeptCode(Map<String, String> param) {
		return deptDao.getOnlineUserByDeptCode(param);
	}

	@Override
	public List<Map<String, String>> isAdminAccept(Map<String, String> param) {
		return deptDao.isAdminAccept(param);
	}
	
}
