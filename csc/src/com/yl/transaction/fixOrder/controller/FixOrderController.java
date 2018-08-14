package com.yl.transaction.fixOrder.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.e;

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
import com.yl.transaction.fixOrder.service.FixOrderService;

@Controller
@RequestMapping("/fixOrder")
public class FixOrderController extends BaseController {

	@Resource
	private FixOrderService fixOrderService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private PublicDao publicDao;

	@RequestMapping("/orderList")
	@ResponseBody
	public PageBean orderList(String begin_time, String end_time, String fix_type, String fix_xq, String orderStatus, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Date beginTime = null;
		Date endTime = null;
		PageBean pageBean = new PageBean();
		try {
			if (begin_time != null && !begin_time.equals("")) {
				beginTime = DateUtils.dateParse(begin_time+" 00:00:00", DateUtils.DATE_TIME_PATTERN);
			}
			if (end_time != null && !end_time.equals("")) {
				endTime = DateUtils.dateParse(end_time+" 23:59:59", DateUtils.DATE_TIME_PATTERN);
			}
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String fixID = userView.getMaxaccept();
			String fixDeptID = userView.getDeptCode();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("page", Integer.parseInt(page));
			param.put("beginTime", beginTime);
			param.put("endTime", endTime);
			param.put("fix_type", fix_type);
			param.put("fix_xq", fix_xq);

			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("fixID", fixID);
			param.put("fixDeptID", fixDeptID);
			param.put("orderStatus", orderStatus);
			// 获取分页数据
			List<Map<String, String>> orderList = fixOrderService.getAllOrder(param);

			// 获取总数量
			String total = fixOrderService.getTotalOrder(param);
			pageBean.setTotal(total);
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		} catch (ParseException e) {
			logger.error(e);
		}
		return pageBean;
	}
	
	@RequestMapping("/queryOrder")
	@ResponseBody
	public Result queryOrder(String orderStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderStatus", orderStatus);
			List<Map<String, String>> orderList = fixOrderService.queryIntervalOrder(param);
			result.setResultData(orderList);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}

	/**
	 * 获取维护人员
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getFixPerson")
	@ResponseBody
	public List<Map<String, String>> getFixPerson(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> fixList = null;
		try {
			UserView userView = this.getUserView(request);
			String deptCode = userView.getDeptCode();
			Map<String, String> param = new HashMap<String, String>();
			param.put("deptCode", deptCode);
			param.put("roleLevel", "2");
			fixList = userService.getUserByParm(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return fixList;
	}
	/**
	 * 维护工单下发
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @param fix_person
	 * @return
	 */
	/*@RequestMapping("/sendOrder")
	@ResponseBody
	public Result sendOrder(HttpServletRequest request, HttpServletResponse response, Model model, String send_ids, String fix_person) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		if(send_ids.contains(",")){
			send_ids = send_ids.substring(0, send_ids.length()-1);
		}
		try {
			UserView fixUser = userService.getUserView(fix_person);
			Map<String, String> param = new HashMap<String, String>();
			param.put("ids", send_ids);
			param.put("fixUserID", fix_person);
			param.put("fixUserName", fixUser.getUserName());
			param.put("fixDeptID", fixUser.getDeptCode());
			param.put("orderStatus", "23");
			fixOrderService.sendToFix(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}*/
	
	/**
	 * author : bky
	 * @param request
	 * @param response
	 * @param model
	 * @param send_ids
	 * @param fix_person
	 * @return
	 */
	@RequestMapping("/sendOrder")
	@ResponseBody
	public Result sendOrder(HttpServletRequest request, HttpServletResponse response, Model model, String send_ids, String fix_person) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		if(send_ids.contains(",")){
			send_ids = send_ids.substring(0, send_ids.length()-1);
		}
		try {
			UserView fixUser = userService.getUserView(fix_person);
			Map<String, String> param = new HashMap<String, String>();
			Map<String, String> paramNew = new HashMap<String, String>();
			paramNew.put("ids", send_ids);
			//param.put("ids", send_ids);
			param.put("fixUserID", fix_person);
			param.put("fixUserName", fixUser.getUserName());
			param.put("fixDeptID", fixUser.getDeptCode());
			//获取工单类型并添加判断条件
			//如果工单类型为"22"(维护未派单)，派单后改为"23"(维护未执行)
			//如果工单类型为"27"(驳回工单未派单)，派单后改为"28"(驳回工单未执行)
			List<Map<String, String>> oldStatus = fixOrderService.getOldStatus(paramNew);
			for (Map<String, String> map : oldStatus) {
				if(map.get("ORDER_STATUS").equals("22")){
					param.put("ids", map.get("MAXACCEPT"));
					param.put("orderStatus", "23");
					fixOrderService.sendToFix(param);
				}else if(map.get("ORDER_STATUS").equals("27")){
					param.put("ids", map.get("MAXACCEPT"));
					param.put("orderStatus", "28");
					fixOrderService.sendToFix(param);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
	/**
	 * 工单提交
	 * @param request
	 * @param response
	 * @param model
	 * @param send_ids
	 * @param fix_person
	 * @return
	 */
	@RequestMapping("/commitOrder")
	@ResponseBody
	public Result commitOrder(HttpServletRequest request, HttpServletResponse response, Model model, String commit_ids, String commit_order_type, String commit_fix_type, String fixed_temp, String fix_mark, String commit_order_status) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("ids", commit_ids);  
			param.put("fix_mark", fix_mark);
			
			/*if("10".equals(commit_fix_type)){
				param.put("fixed_temp", fixed_temp);
				double temp = 0d;
				if(fixed_temp != null && !"".equals(fixed_temp)){
					temp = Double.parseDouble(fixed_temp);
				}
				if(temp >= 18){
					param.put("orderStatus", "24");//温度通过返回至客服
				}else{
					param.put("orderStatus", "26");//温度不通过，客服管理员拦截
				}
			}else{
				param.put("orderStatus", "24");
			}*/
			
			//order_status='23'：代表新工单；order_status='28'：代表客服管理员拦截后再次派单
			if("23".equals(commit_order_status)){
				if("10".equals(commit_fix_type)){
					param.put("fixed_temp", fixed_temp);
					double temp = 0d;
					if(fixed_temp != null && !"".equals(fixed_temp)){
						temp = Double.parseDouble(fixed_temp);
					}
					if(temp >= 18){
						param.put("orderStatus", "24");//温度通过返回至客服
					}else{
						param.put("orderStatus", "26");//温度不通过，客服管理员拦截
					}
				}else{
					param.put("orderStatus", "24");
				}
			}else{
				if("10".equals(commit_fix_type)){
					param.put("fixed_temp", fixed_temp);
					double temp = 0d;
					if(fixed_temp != null && !"".equals(fixed_temp)){
						temp = Double.parseDouble(fixed_temp);
					}
					if(temp >= 18){
						param.put("orderStatus", "29");//温度通过返回至客服
					}else{
						param.put("orderStatus", "26");//温度不通过，客服管理员再次拦截
					}
				}else{
					param.put("orderStatus", "29");
				}
			}
			fixOrderService.commitOrder(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
	/**
	 * 维护工单查询列表（因为多个状态一起查询用in所以不复用orderList方法）
	 * @param begin_time
	 * @param end_time
	 * @param fix_type
	 * @param fix_xq
	 * @param orderStatus
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/fixOrderQueryList")
	@ResponseBody
	public PageBean fixOrderQueryList(String begin_time, String end_time, String order_type, String fix_type, String fix_xq, String orderStatus, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Date beginTime = null;
		Date endTime = null;
		PageBean pageBean = new PageBean();
		try {
			if (begin_time != null && !begin_time.equals("")) {
				beginTime = DateUtils.dateParse(begin_time+" 00:00:00", DateUtils.DATE_TIME_PATTERN);
			}
			if (end_time != null && !end_time.equals("")) {
				endTime = DateUtils.dateParse(end_time+" 23:59:59", DateUtils.DATE_TIME_PATTERN);
			}
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String fixID = userView.getMaxaccept();
			String fixDeptID = userView.getDeptCode();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("page", Integer.parseInt(page));
			param.put("beginTime", beginTime);
			param.put("endTime", endTime);
			param.put("fix_type", fix_type);
			param.put("fix_xq", fix_xq);
			param.put("order_type", order_type);

			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("fixID", fixID);
			param.put("fixDeptID", fixDeptID);
			param.put("orderStatus", orderStatus);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			// 获取分页数据
			List<Map<String, String>> orderList = fixOrderService.getFixOrderQueryList(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(orderList);

			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		} catch (ParseException e) {
			logger.error(e);
		}
		return pageBean;
	}
}
