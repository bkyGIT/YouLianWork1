package com.yl.transaction.conver.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.pojo.PageBean;
import com.yl.common.pojo.Result;
import com.yl.common.user.pojo.UserView;
import com.yl.common.user.service.UserService;
import com.yl.common.util.DateUtils;
import com.yl.transaction.conver.service.ConverService;
import com.yl.transaction.fixOrder.service.FixOrderService;

@Controller
@RequestMapping("/conver")
public class ConverController extends BaseController {

	@Resource
	private UserService userService;

	@Resource
	private ConverService converService;
	
	@Resource
	private PublicDao publicDao;

	/**
	 * 获取通话列表
	 * 
	 * @param begin_time
	 * @param end_time
	 * @param kf_maxaccept
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/converList")
	@ResponseBody
	public PageBean converList(String begin_time, String end_time, String kf_maxaccept, String order_id, String talk_flag, String in_flag, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Map<String, Object> param = new HashMap<String, Object>();
		PageBean pageBean = new PageBean();
		try{
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String oprID = userView.getMaxaccept();
			String oprDeptID = userView.getDeptCode();
			
			if(StringUtils.isNotBlank(begin_time))
				param.put("creatTime", DateUtils.dateParse(begin_time + " 00:00:00", DateUtils.DATE_TIME_PATTERN));
			if(StringUtils.isNotBlank(end_time))
				param.put("endTime", DateUtils.dateParse(end_time + " 23:59:59", DateUtils.DATE_TIME_PATTERN));
			
			param.put("page", Integer.parseInt(page));
			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("orderChannel", "70");
			param.put("oprID", oprID);
			param.put("oprDeptID", oprDeptID);
			param.put("kf_maxaccept", kf_maxaccept);
			param.put("order_id", order_id);
			param.put("talk_flag", talk_flag);
			param.put("in_flag", in_flag);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			// 获取分页数据
			List<Map<String, String>> converList = converService.getAllConverList(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(converList);
			/*
			 * //获取总数量 String total = orderService.getTotalOrder(param);
			 */
			pageBean.setTotal(pageinfo.getTotal() + "");
			pageBean.setRows(converList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
		}

		return pageBean;
	}

	/**
	 * 获取客服人员
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getUserInfoByRoleAndDept")
	@ResponseBody
	public List<Map<String, String>> getUserInfoByRoleAndDept(HttpServletRequest request, HttpServletResponse response, Model model,
			String roleLevel, String deptType) {
		Map<String, String> param = new HashMap<String, String>();
		List<Map<String, String>> userList = null;
		try {
			param.put("roleLevel", roleLevel);
			param.put("deptType", deptType);

			userList = userService.getUserInfoByRoleAndDept(param);
		} catch (Exception e) {
			logger.error(e);
		}
		return userList;
	}
}
