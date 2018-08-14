package com.yl.transaction.fixPerson.controller;

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
import com.yl.common.dao.PublicDao;
import com.yl.common.util.DateUtils;
import com.yl.transaction.fixPerson.service.FixPersonService;

@Controller
@RequestMapping("/fixPerson")
public class FixPersonController extends BaseController{

	@Resource
	private FixPersonService fixPersonService;
	
	@RequestMapping("/getFixType")
	@ResponseBody
	public List<Double> getFixType(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
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
			String br = fixPersonService.getFixTypeBR(param);
			String ls = fixPersonService.getFixTypeLS(param);

			Double brNum = Double.parseDouble(br);
			Double lsNum = Double.parseDouble(ls);
			
			dataList.add(brNum);
			dataList.add(lsNum);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getFixDgree")
	@ResponseBody
	public List<Double> getFixDgree(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
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
			String good = fixPersonService.getFixDgreeGood(param);
			String ok = fixPersonService.getFixDgreeOk(param);
			String no = fixPersonService.getFixDgreeNo(param);
			
			Double goodNum = Double.parseDouble(good);
			Double okNum = Double.parseDouble(ok);
			Double noNum = Double.parseDouble(no);
			
			dataList.add(goodNum);
			dataList.add(okNum);
			dataList.add(noNum);
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
		nameList = fixPersonService.getNameList(param);
		write(response, nameList);
		
	}
	
	@RequestMapping("/getOrderNumByTime")
	@ResponseBody
	public List<Map<String, Object>> getOrderNumByTime(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
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
			dataList = fixPersonService.getOrderNumByTime(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	
	//查询已完成工单数量
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
			dataList = fixPersonService.getAccessOrderNumByTime(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	//查询待完成工单数量
	@RequestMapping("/getUnAccessOrderNumByTime")
	@ResponseBody
	public List<Map<String, Object>> getUnAccessOrderNumByTime(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
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
			dataList = fixPersonService.getUnAccessOrderNumByTime(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getorderDegreeUnsatisfied")
	@ResponseBody
	public List<Map<String, Object>> getorderDegreeUnsatisfied(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
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
			dataList = fixPersonService.getorderDegreeUnsatisfied(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getKFreview")
	@ResponseBody
	public List<Map<String, Object>> getKFreview(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
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
			dataList = fixPersonService.getKFreview(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	
	@RequestMapping("/getWaitSendOrder")
	@ResponseBody
	public List<Map<String, Object>> getWaitSendOrder(String beginTime, String endTime, String userId, String managerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
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
			dataList = fixPersonService.getWaitSendOrder(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
}
