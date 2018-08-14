package com.yl.transaction.weixin.controller;

import java.util.ArrayList;
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

import com.yl.PhoneInterface.service.PhoneInterfaceService;
import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.pojo.PageBean;
import com.yl.common.pojo.Result;
import com.yl.common.user.pojo.UserView;
import com.yl.common.util.DBUtil;
import com.yl.transaction.cust.service.CustService;
import com.yl.transaction.weixin.service.WeixinService;

@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

	@Resource
	private WeixinService weixinService;
	
	@Resource
	private PhoneInterfaceService phoneInterfaceService;
	
	@Resource
	private CustService custService;

	@Resource
	private PublicDao publicDao;

	/**
	 * 获取流转方式
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getCirType")
	@ResponseBody
	public Result getCirType(HttpServletRequest request, HttpServletResponse response, Model model) {

		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("success");
		result.setResultData(null);
		
		try {
			UserView user = this.getUserView(request);
			Map<String, String> param = new HashMap<String, String>();
			param.put("oprID", user.getMaxaccept());
			param.put("deptID", user.getDeptCode());
			List<Map<String, String>> cirList = weixinService.getCirType(param);
			if(cirList != null && cirList.size() > 0)
				result.setResultData(cirList.get(0));
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("获取流转方式失败！");
		}
		return result;
	}
	/**
	 * 设置流转方式
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/setCirType")
	@ResponseBody
	public Result setCirType(String cirType, HttpServletRequest request, HttpServletResponse response, Model model) {

		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		
		try {
			UserView user = this.getUserView(request);
			Map<String, String> param = new HashMap<String, String>();
			param.put("oprID", user.getMaxaccept());
			param.put("deptID", user.getDeptCode());
			param.put("cirType", cirType);
			List<Map<String, String>> cirList = weixinService.getCirType(param);
			if(cirList != null && cirList.size() > 0){
				param.put("maxaccept", cirList.get(0).get("MAXACCEPT"));
				weixinService.updateCirType(param);
			}else{
				param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				weixinService.setCirType(param);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("设置流转方式失败！");
		}
		return result;
	}
	/**
	 * 微信投诉详情根据地址查客户信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryUserList")
	@ResponseBody
	public PageBean queryUserList(String custID, HttpServletRequest request, HttpServletResponse response, Model model, String phone) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		PageBean pageBean = new PageBean();
		try {
			List<Map<String, String>> custList = new ArrayList<Map<String, String>>();
			Map<String, String> custInfo = custService.getCustInfoByID(custID);
			if (custInfo != null) {
				custList.add(custInfo);
				pageBean.setRows(custList);
				pageBean.setSuccess("success");
			} else {
				pageBean.setTotal("0");
				pageBean.setRows(new ArrayList<Map<String, String>>());
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return pageBean;
	}
}
