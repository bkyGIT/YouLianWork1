package com.yl.PhoneInterface.dao;

import java.util.List;
import java.util.Map;

public interface PhoneInterfaceDao {

	public void setPhoneList(Map<String, String> param);
	
	public List<Map<String, String>> getPhoneList(Map<String, String> param);
	
	public void updatePhoneStatus(Map<String, String> param);
	
	public void insertCSInfo(Map<String, String> param);
	
	public void insertOnlineFlag(Map<String, String> param);
	
	public String getOnlineFlag(Map<String, String> param);
	
	public void setOrderCSID(Map<String, String> param);
	
	public void updateCSInfo(Map<String, String> param);
	
	public List<Map<String, String>> getCSBySeatAndPhone(Map<String, String> param);
	
	public void insertCSAllInfo(Map<String, String> param);
	
	public void insertIntoPhone(Map<String, String> param);
	
	public List<Map<String, String>> getIntoPhoneBySeatID(Map<String, String> param);
	
	public void updateIntoPhoneStatus(Map<String, String> param);
	
	public List<Map<String, String>> getCustInfo(Map<String, String> param);
	
	public List<Map<String, String>> getCustDyInfo(Map<String, String> param);
	
	public List<Map<String, String>> getOrderInfoByCustID(Map<String, String> param);
	
	public void updateNormalPhone(Map<String, String> param);
	
	public void updateConvertPhone(Map<String, String> param);
	
	public void insertConvertPhone(Map<String, String> param);
	
	public void insertOrder(Map<String, String> param);
	
	public void inserCustInfo(Map<String, String> param);
	
	public void removePhoneRelase(Map<String, String> param);
	
	public List<Map<String, String>> getConvertPhoneList(Map<String, String> param);
	
	public List<Map<String, String>> getCustInfoByLikeAdress(Map<String, String> param);
	
	public List<Map<String, String>> getCustInfoByAdress(Map<String, String> param);
	
	public List<Map<String, String>> getOrderInfoByInfo(Map<String, String> param);
	
	public void setOrderOutCSID(Map<String, String> param);
	
	public List<Map<String, String>> getPhoneStatus(Map<String, String> param);
	
	public void setPhoneStatus(Map<String, String> param);
	
	public List<Map<String, String>> getTrunPhoneList(Map<String, String> param);
	
	public void updateTrunPhone(Map<String, String> param);
	
	public void insertRebackOrder(Map<String, String> param);
	
	public void rebackOrder(Map<String, String> param);
}
