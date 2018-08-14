package com.yl.transaction.kfManager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yl.common.controller.BaseController;
import com.yl.common.util.DateUtils;
import com.yl.transaction.kfManager.service.KFManagerService;

@Controller
@RequestMapping("/kfManager")
public class KFManagerController extends BaseController{
	
	@Resource
	private KFManagerService kfManagerService;
	
	@RequestMapping("/getOrderCount")
	@ResponseBody
	public List<Map<String, Object>> getOrderCount(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			dataList = kfManagerService.getOrderCount(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getPhoneTime")
	@ResponseBody
	public List<Map<String, Object>> getPhoneTime(String beginTime, String endTime, String userId, String managerId, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			paramList = kfManagerService.getPhoneTime(param);
			for (Map<String, Object> map : paramList) {
				System.out.println("月份："+map.get("MONTH"));
				System.out.println("平均通话时长："+map.get("TIME"));
			}
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return paramList;
	}
	
	/*@RequestMapping("/getPhoneDegree")
	@ResponseBody
	public List<Double> getPhoneDegree(String beginTime, String endTime, String userId, String managerId, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		//String userID = checkUserId(userId, response);
		List<Double> dataList = new ArrayList<Double>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			String goodNum = kfManagerService.getPhoneGood(param);
			String okNum = kfManagerService.getPhoneOk(param);
			String noNum = kfManagerService.getPhoneNo(param);
			
			Double good = Double.parseDouble(goodNum);
			Double ok = Double.parseDouble(okNum);
			Double no = Double.parseDouble(noNum);
			
			dataList.add(good);
			dataList.add(ok);
			dataList.add(no);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}*/
	
	@RequestMapping("/getOrderType")
	@ResponseBody
	public List<Double> getOrderType(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		//String userID = checkUserId(userId, response);
		List<Double> dataList = new ArrayList<Double>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			String zxNum = kfManagerService.getOrderZX(param);
			String whNum = kfManagerService.getOrderWH(param);
			String jyNum = kfManagerService.getOrderJY(param);
			String tsNum = kfManagerService.getOrderTS(param);
			
			Double zx = Double.parseDouble(zxNum);
			Double wh = Double.parseDouble(whNum);
			Double jy = Double.parseDouble(jyNum);
			Double ts = Double.parseDouble(tsNum);
			
			dataList.add(zx);
			dataList.add(wh);
			dataList.add(jy);
			dataList.add(ts);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getConverStatus")
	@ResponseBody
	public List<Double> getConverStatus(String beginTime, String endTime, String userId, String managerId, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		//String userID = checkUserId(userId, response);
		List<Double> dataList = new ArrayList<Double>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			String converAccess = kfManagerService.getConverAccess(param);
			String converUnAccess = kfManagerService.getConverUnAccess(param);
			
			Double access = Double.parseDouble(converAccess);
			Double unAccess = Double.parseDouble(converUnAccess);
			
			dataList.add(access);
			dataList.add(unAccess);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getNameList")
	@ResponseBody
	public void getNameList(String managerId, HttpServletRequest request, HttpServletResponse response, Model model){
		List<Map<String, String>> nameList = new ArrayList<Map<String, String>>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("managerId", managerId);
		nameList = kfManagerService.getNameList(param);
		write(response, nameList);
		
	}
	
	@RequestMapping("/getWaitSend")
	@ResponseBody
	public List<Map<String, Object>> getWaitSend(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			dataList = kfManagerService.getWaitSend(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getCancelOrder")
	@ResponseBody
	public List<Map<String, Object>> getCancelOrder(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			dataList = kfManagerService.getCancelOrder(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getWaitFix")
	@ResponseBody
	public List<Map<String, Object>> getWaitFix(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			dataList = kfManagerService.getWaitFix(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getWaitReview")
	@ResponseBody
	public List<Map<String, Object>> getWaitReview(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			dataList = kfManagerService.getWaitReview(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getAccessOrderNumByTime")
	@ResponseBody
	public List<Map<String, Object>> getAccessOrderNumByTime(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (beginTime != null && !beginTime.equals("")) {
				stratTime = DateUtils.dateParse(beginTime, DateUtils.DATE_PATTERN);
			}
			if (endTime != null && !endTime.equals("")) {
				overTime = DateUtils.dateParse((endTime.concat(" 23:59:59")), DateUtils.HOUR_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("userId", userId);
			param.put("managerId", managerId);
			param.put("orderChannel", orderChannel);
			dataList = kfManagerService.getAccessOrderNumByTime(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getChannelList")
	@ResponseBody
	public void getChannelList(HttpServletRequest request, HttpServletResponse response, Model model){
		List<Map<String, String>> channelList = new ArrayList<Map<String, String>>();
		channelList = kfManagerService.getChannelList();
		write(response, channelList);
		
	}
	
}
