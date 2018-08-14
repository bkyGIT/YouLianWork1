package com.yl.transaction.order.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.transaction.order.dao.OrderDao;
import com.yl.transaction.order.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
	@Resource
	private OrderDao orderDao;

	public List<Map<String, String>> getAllOrder(Map<String, Object> param) {
		return orderDao.getAllOrder(param);
	}

	public void addOrder(Map<String, String> param) {
		orderDao.addOrder(param);
	}

	public void updateOrderByID(Map<String, String> param) {
		orderDao.updateOrderByID(param);
	}

	public List<Map<String, String>> getOrderByAccount(String account) {
		return orderDao.getOrderByAccount(account);
	}

	public String getTotalOrder(Map<String, Object> param) {
		return orderDao.getTotalOrder(param);
	}

	public List<Map<String, String>> getOrderTypeList() {
		return orderDao.getOrderTypeList();
	}

	public List<Map<String, String>> getFixTypeList() {
		return orderDao.getFixTypeList();
	}

	public List<Map<String, String>> getOrderStatus() {
		return orderDao.getOrderStatus();
	}

	public List<Map<String, String>> getOrderDgree() {
		return orderDao.getOrderDgree();
	}

	public void removeOrder(String[] ids) {
		orderDao.removeOrder(ids);
	}

	public void sendToFixInIds(String[] ids,String fix_dept_id) {
		orderDao.sendToFixInIds(ids,fix_dept_id);
	}

//	@Override
	public List<Map<String, Object>> getFixdeptidList() {
		return orderDao.getFixdeptidList();
	}

//	@Override
	public void updateOrderStatus(Map<String, String> param) {
		orderDao.updateOrderStatus(param);
	}

//	@Override
	public List<Map<String, String>> getCommonCode(Map<String, String> param) {
		return orderDao.getCommonCode(param);
	}

//	@Override
	public List<Map<String, String>> getAllQueryOrder(Map<String, Object> param) {
		return orderDao.getAllQueryOrder(param);
	}

//	@Override
	public void revewOrderByID(Map<String, String> param) {
		orderDao.revewOrderByID(param);
	}

	@Override
	public List<Map<String, String>> getOrderPhone() {
		return orderDao.getOrderPhone();
	}

	
	
	
	@Override
	public List<Map<String, Object>> getOrderNumByTime(Map<String, Object> param) {
		return orderDao.getOrderNumByTime(param);
	}
	
	@Override
	public List<Map<String, Object>> getAccessOrderNumByTime(Map<String, Object> param) {
		return orderDao.getAccessOrderNumByTime(param);
	}
	
	@Override
	public List<Map<String, Object>> getUnAccessOrderNumByTime(Map<String, Object> param) {
		return orderDao.getUnAccessOrderNumByTime(param);
	}

	@Override
	public List<Map<String, String>> getAllWeixinQueryOrder(Map<String, Object> param) {
		return orderDao.getAllWeixinQueryOrder(param);
	}

	@Override
	public List<Map<String, String>> getUserByDeptType(Map<String, String> param) {
		return orderDao.getUserByDeptType(param);
	}

	@Override
	public void addOrderOprInfoByID(Map<String, String> param) {
		orderDao.addOrderOprInfoByID(param);
	}

	@Override
	public void insertWeixinOrder(Map<String, String> param) {
		orderDao.insertWeixinOrder(param);
	}

	@Override
	public List<Map<String, Object>> getorderDegreeUnsatisfied(Map<String, Object> param) {
		return orderDao.getorderDegreeUnsatisfied(param);
	}

	@Override
	public void overOrderInIds(String[] ids) {
		orderDao.overOrderInIds(ids);
	}

	@Override
	public void insertCodeNum(Map<String, String> param) {
		orderDao.insertCodeNum(param);
	}

	@Override
	public List<Map<String, String>> getCodeNum(Map<String, Object> param) {
		return orderDao.getCodeNum(param);
	}

	@Override
	public List<Map<String, String>> getAdviseData(Map<String, String> param) {
		return orderDao.getAdviseData(param);
	}

	@Override
	public List<Map<String, String>> getDgreeList() {
		return orderDao.getDgreeList();
	}

	@Override
	public List<Map<String, String>> getDeptList() {
		return orderDao.getDeptList();
	}

	@Override
	public List<Map<String, String>> getSonUserByDeptCode(Map<String, String> param) {
		return orderDao.getSonUserByDeptCode(param);
	}

	@Override
	public List<Map<String, String>> getAllReviewOrder(Map<String, Object> param) {
		return orderDao.getAllReviewOrder(param);
	}

	@Override
	public List<Map<String, Object>> returnOrderList(Map<String, Object> param) {
		return orderDao.returnOrderList(param);
	}

	@Override
	public void returnOrderInIds(String[] ids) {
		orderDao.returnOrderInIds(ids);
	}

	

}
