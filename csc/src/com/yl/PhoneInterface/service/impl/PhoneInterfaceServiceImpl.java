package com.yl.PhoneInterface.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.PhoneInterface.dao.PhoneInterfaceDao;
import com.yl.PhoneInterface.service.PhoneInterfaceService;

@Service("PhoneInterfaceService")
public class PhoneInterfaceServiceImpl implements PhoneInterfaceService{

	@Resource
	private PhoneInterfaceDao phoneInterfaceDao;
	
	@Override
	public void setPhoneList(Map<String, String> param) {
		phoneInterfaceDao.setPhoneList(param);
	}

	@Override
	public List<Map<String, String>> getPhoneList(Map<String, String> param) {
		return phoneInterfaceDao.getPhoneList(param);
	}

	@Override
	public void updatePhoneStatus(Map<String, String> param) {
		phoneInterfaceDao.updatePhoneStatus(param);
	}

	@Override
	public void insertCSInfo(Map<String, String> param) {
		phoneInterfaceDao.insertCSInfo(param);
	}

	@Override
	public void insertOnlineFlag(Map<String, String> param) {
		phoneInterfaceDao.insertOnlineFlag(param);
	}

	@Override
	public String getOnlineFlag(Map<String, String> param) {
		return phoneInterfaceDao.getOnlineFlag(param);
	}

	@Override
	public void setOrderCSID(Map<String, String> param) {
		phoneInterfaceDao.setOrderCSID(param);
	}

	@Override
	public void updateCSInfo(Map<String, String> param) {
		phoneInterfaceDao.updateCSInfo(param);
	}

	@Override
	public List<Map<String, String>> getCSBySeatAndPhone(Map<String, String> param) {
		return phoneInterfaceDao.getCSBySeatAndPhone(param);
	}

	@Override
	public void insertCSAllInfo(Map<String, String> param) {
		phoneInterfaceDao.insertCSAllInfo(param);
	}

	@Override
	public List<Map<String, String>> getIntoPhoneBySeatID(Map<String, String> param) {
		return phoneInterfaceDao.getIntoPhoneBySeatID(param);
	}

	@Override
	public void insertIntoPhone(Map<String, String> param) {
		phoneInterfaceDao.insertIntoPhone(param);
	}

	@Override
	public void updateIntoPhoneStatus(Map<String, String> param) {
		phoneInterfaceDao.updateIntoPhoneStatus(param);
	}

	@Override
	public List<Map<String, String>> getCustInfo(Map<String, String> param) {
		return phoneInterfaceDao.getCustInfo(param);
	}

	@Override
	public List<Map<String, String>> getCustDyInfo(Map<String, String> param) {
		return phoneInterfaceDao.getCustDyInfo(param);
	}

	@Override
	public List<Map<String, String>> getOrderInfoByCustID(Map<String, String> param) {
		return phoneInterfaceDao.getOrderInfoByCustID(param);
	}

	@Override
	public void updateNormalPhone(Map<String, String> param) {
		phoneInterfaceDao.updateNormalPhone(param);
	}

	@Override
	public void updateConvertPhone(Map<String, String> param) {
		phoneInterfaceDao.updateConvertPhone(param);
	}

	@Override
	public void insertConvertPhone(Map<String, String> param) {
		phoneInterfaceDao.insertConvertPhone(param);
	}

	@Override
	public void insertOrder(Map<String, String> param) {
		phoneInterfaceDao.insertOrder(param);
	}

	@Override
	public void inserCustInfo(Map<String, String> param) {
		phoneInterfaceDao.inserCustInfo(param);
	}

	@Override
	public void removePhoneRelase(Map<String, String> param) {
		phoneInterfaceDao.removePhoneRelase(param);
	}

	@Override
	public List<Map<String, String>> getConvertPhoneList(Map<String, String> param) {
		return phoneInterfaceDao.getConvertPhoneList(param);
	}

	@Override
	public List<Map<String, String>> getCustInfoByLikeAdress(Map<String, String> param) {
		return phoneInterfaceDao.getCustInfoByLikeAdress(param);
	}
	
	@Override
	public List<Map<String, String>> getCustInfoByAdress(Map<String, String> param) {
		return phoneInterfaceDao.getCustInfoByAdress(param);
	}

	@Override
	public List<Map<String, String>> getOrderInfoByInfo(Map<String, String> param) {
		return phoneInterfaceDao.getOrderInfoByInfo(param);
	}

	@Override
	public void setOrderOutCSID(Map<String, String> param) {
		phoneInterfaceDao.setOrderOutCSID(param);
	}

	@Override
	public List<Map<String, String>> getPhoneStatus(Map<String, String> param) {
		return phoneInterfaceDao.getPhoneStatus(param);
	}

	@Override
	public void setPhoneStatus(Map<String, String> param) {
		phoneInterfaceDao.setPhoneStatus(param);
	}

	@Override
	public List<Map<String, String>> getTrunPhoneList(Map<String, String> param) {
		return phoneInterfaceDao.getTrunPhoneList(param);
	}

	@Override
	public void updateTrunPhone(Map<String, String> param) {
		phoneInterfaceDao.updateTrunPhone(param);
	}

	@Override
	public void insertRebackOrder(Map<String, String> param) {
		phoneInterfaceDao.insertRebackOrder(param);
	}

	@Override
	public void rebackOrder(Map<String, String> param) {
		phoneInterfaceDao.rebackOrder(param);
	}
}
