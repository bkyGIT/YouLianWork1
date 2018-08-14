package com.yl.transaction.order.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.yl.PhoneInterface.service.PhoneInterfaceService;
import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.pojo.PageBean;
import com.yl.common.pojo.Result;
import com.yl.common.user.pojo.User;
import com.yl.common.user.pojo.UserView;
import com.yl.common.util.ConfigUtil;
import com.yl.common.util.DBUtil;
import com.yl.common.util.DateUtils;
import com.yl.common.util.HttpUtil;
import com.yl.common.util.RandomUtil;
import com.yl.transaction.dept.service.DeptService;
import com.yl.transaction.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
	
	@Resource
	private OrderService orderService;
	@Resource
	private DeptService deptService;
	@Resource
	private PhoneInterfaceService phoneInterfaceService;
	
	@Resource
	private PublicDao publicDao;
	
	@RequestMapping("/orderList")
	@ResponseBody
	public PageBean orderList(String creat_time,String end_time,String order_type,String fix_type,
			HttpServletRequest request, HttpServletResponse response, Model model){
		
		PageBean pageBean = new PageBean();
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(creat_time))
				param.put("beginTime", DateUtils.dateParse(creat_time + " 00:00:00", DateUtils.DATE_TIME_PATTERN));
			if(StringUtils.isNotBlank(end_time))
				param.put("endTime", DateUtils.dateParse(end_time + " 23:59:59", DateUtils.DATE_TIME_PATTERN));
			
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String oprID = userView.getMaxaccept();
			String oprDeptID = userView.getDeptCode();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			param.put("page", Integer.parseInt(page));
			param.put("order_type", order_type);
			param.put("fix_type", fix_type);
			
			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("oprID", oprID);
			param.put("oprDeptID", oprDeptID);
			param.put("orderStatus", "20");
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			//获取分页数据
			List<Map<String, String>> orderList = orderService.getAllOrder(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(orderList);
			/*//获取总数量
			String total = orderService.getTotalOrder(param);*/
			
			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
		}
		
		return pageBean;
	}
	
	
	/**
	 * 增加工单
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addOrder")
	public void addOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = (User)request.getSession().getAttribute("userInfo");
		System.out.println(user);
		String ordertype = request.getParameter("order_types"); 
		String fixtype = request.getParameter("order_fix_type"); 
		String oprid = request.getParameter("order_opr_id"); 
		String custid = request.getParameter("order_cust_id");
		String orderstatus = request.getParameter("order_status"); 
		String fixusername = request.getParameter("order_fixuser_name");
		String deptcode =request.getParameter("order_dept_code"); 
		String ordermark = request.getParameter("order_mark");
		String orderdgree = request.getParameter("order_dgree"); 
		String oprdeptid = user.getDeptCode();//request.getParameter("order_opr_dept_id"); //客服部门
		Map<String, String> param = new HashMap<String, String>(); 
		
		param.put("ordertype", ordertype);
		param.put("fixtype", fixtype);
		param.put("oprid", oprid);
		param.put("custid", custid);
		param.put("orderstatus", orderstatus);
		param.put("fixusername", fixusername);
		param.put("deptcode", deptcode);
		param.put("ordermark", ordermark);
		param.put("orderdgree", orderdgree);
		param.put("oprdeptid", oprdeptid);
		//param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		try {
			orderService.addOrder(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}
	
	
	
	
	/**
	 * 编辑工单信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/modifyOrder")
	public void modifyOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maxaccept = request.getParameter("order_maxaccept");
		
		String ordertype = request.getParameter("order_types"); 
		String fixtype = request.getParameter("order_fix_type"); 
		String ordermark = request.getParameter("order_mark"); 
		String phone = request.getParameter("order_phone");
		Map<String, String> param = new HashMap<String, String>();
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			param.put("maxaccept", maxaccept);
			param.put("ordertype", ordertype);
			if("2".equals(ordertype))
				param.put("fixtype", fixtype);
			else
				param.put("fixtype", "");
			param.put("ordermark", ordermark);
			param.put("phone", phone);
			
			orderService.updateOrderByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}
	
	
	/**
	 * 加载工单类型
	 */
	@RequestMapping("/getOrderTypeList")
	public void orderTypeList(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> orderTypeList = orderService.getOrderTypeList();
		write(response, orderTypeList);
	}
	
	/**
	 * 加载维护类型
	 */
	@RequestMapping("/getFixTypeList")
	public void getFixTypeList(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> fixTypeList = orderService.getFixTypeList();
		write(response, fixTypeList);
	}
	
	/**
	 * 加载工单装状态
	 */
	@RequestMapping("/getOrderStatus")
	public void getOrderStatus(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> orderStatus = orderService.getOrderStatus();
		write(response, orderStatus);
	}
	
	/**
	 * 加载客客户满意度
	 */
	@RequestMapping("/getOrderDgree")
	public void getOrderDgree(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> orderDgree = orderService.getOrderDgree();
		write(response, orderDgree);
	}
	
	/**
	 * 加载客户联系方式
	 */
	@RequestMapping("/getOrderPhone")
	public void getOrderPhone(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> orderPhone = orderService.getOrderPhone();
		write(response, orderPhone);
	}
	/**
	 * 根据部门类型获取部门下人员
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUserByDeptType")
	@ResponseBody
	public List<Map<String, String>> getUserByDeptType(String deptType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("deptType", deptType);
		List<Map<String, String>> userList = orderService.getUserByDeptType(param);
		return userList;
	}
	/**
	 * 根据部门ID获取下面子成员
	 * @param deptType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getSonUserByDeptCode")
	@ResponseBody
	public List<Map<String, String>> getSonUserByDeptCode(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> param = new HashMap<String, String>();
		UserView user = this.getUserView(request);
		param.put("deptCode", user.getDeptCode());
		param.put("roleLevel", "2");
		List<Map<String, String>> userList = orderService.getSonUserByDeptCode(param);
		return userList;
	}
	/**
	 * 公共获取转换码方法
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getCommonCode")
	public void getCommonCode(HttpServletRequest request, HttpServletResponse response, Model model, String codeKey, String codeIDS) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("codeKey", codeKey);
		param.put("codeIDS", codeIDS);
		List<Map<String, String>> orderStatus = orderService.getCommonCode(param);
		write(response, orderStatus);
	}
	
	/**
	 * 取消工单
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/removeOrder")
	public @ResponseBody Result removeOrder(String ids,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		if(ids.contains(","))
			ids = ids.substring(0, ids.length()-1);
		try {
//			orderService.removeOrder(ids);
			Map<String, String> param = new HashMap<String, String>();
			param.put("ids", ids);
			param.put("status", "21");
			orderService.updateOrderStatus(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
		
	}
	/**
	 * 客服工单派发至维护部门fv
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/sendToFix")
	@ResponseBody
	public Result sendToFix(String[] ids,String fix_dept_id,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			orderService.sendToFixInIds(ids,fix_dept_id);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result; 
	}
	/**
	 * 工单终结
	 * @param ids
	 * @param fix_dept_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/overOrder")
	@ResponseBody
	public Result overOrder(String[] ids,String fix_dept_id,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			orderService.overOrderInIds(ids);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result; 
	}
	/**
	 * 微信工单派发
	 * @param ids
	 * @param fix_dept_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sendToFixWeixin")
	@ResponseBody
	public Result sendToFixWeixin(String ids,String fix_dept_id,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			String[] idArr = ids.split(",");
				
			orderService.sendToFixInIds(idArr,fix_dept_id);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result; 
	}
	@RequestMapping("/getFixdeptidList")
	@ResponseBody
	public List<Map<String, Object>>  getFixdeptidList() {
		List<Map<String, Object>> list=orderService.getFixdeptidList();
		return list;
	}
	
	/**
	 * 客服工单查询（因为查询多个状态工单用IN防止效率问题不和orderQuery复用）
	 * @param creat_time
	 * @param order_type
	 * @param fix_type
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/orderQueryList")
	@ResponseBody
	public PageBean orderQueryList(String begin_time, String end_time,String order_type,String fix_type, String fix_xq, String orderStatus, String orderChannel, String order_id, String orderDgree, String deptId,
			HttpServletRequest request, HttpServletResponse response, Model model){
		Date beginTime = null;
		Date endTime = null;
		PageBean pageBean = new PageBean();
		try{
			
			if (begin_time != null && !begin_time.equals("")) {
				beginTime = DateUtils.dateParse(begin_time + " 00:00:00", DateUtils.DATE_TIME_PATTERN);
			}
			if (end_time != null && !end_time.equals("")) {
				endTime = DateUtils.dateParse(end_time + " 23:59:59", DateUtils.DATE_TIME_PATTERN);
			}
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String oprID = userView.getMaxaccept();
			String oprDeptID = userView.getDeptCode();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("page", Integer.parseInt(page));
			param.put("beginTime", beginTime);
			param.put("endTime", endTime);
			param.put("order_type", order_type);
			param.put("fix_type", fix_type);
			param.put("fix_xq", fix_xq);
			
			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("oprID", oprID);
			param.put("oprDeptID", oprDeptID);
			param.put("orderStatus", orderStatus);
			param.put("orderChannel", orderChannel);
			param.put("order_id", order_id);
			param.put("orderDgree", orderDgree);
			param.put("deptId", deptId);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			//获取分页数据
			List<Map<String, String>> orderList = orderService.getAllQueryOrder(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(orderList);
			
			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
		}
		
		/*pageWrite(response, pageBean);*/
		return pageBean;
	}
	/**
	 * 工单回访列表查询
	 * @param begin_time
	 * @param end_time
	 * @param order_type
	 * @param fix_type
	 * @param fix_xq
	 * @param orderStatus
	 * @param orderChannel
	 * @param order_id
	 * @param orderDgree
	 * @param deptId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/orderReviewList")
	@ResponseBody
	public PageBean orderReviewList(String begin_time, String end_time,String order_type,String fix_type, String fix_xq, String orderStatus, String orderChannel, String order_id, String orderDgree, String deptId,
			HttpServletRequest request, HttpServletResponse response, Model model){
		Date beginTime = null;
		Date endTime = null;
		PageBean pageBean = new PageBean();
		try{
			
			if (begin_time != null && !begin_time.equals("")) {
				beginTime = DateUtils.dateParse(begin_time + " 00:00:00", DateUtils.DATE_TIME_PATTERN);
			}
			if (end_time != null && !end_time.equals("")) {
				endTime = DateUtils.dateParse(end_time + " 23:59:59", DateUtils.DATE_TIME_PATTERN);
			}
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String oprID = userView.getMaxaccept();
			String oprDeptID = userView.getDeptCode();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("page", Integer.parseInt(page));
			param.put("beginTime", beginTime);
			param.put("endTime", endTime);
			param.put("order_type", order_type);
			param.put("fix_type", fix_type);
			param.put("fix_xq", fix_xq);
			
			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("oprID", oprID);
			param.put("oprDeptID", oprDeptID);
			param.put("orderStatus", orderStatus);
			param.put("orderChannel", orderChannel);
			param.put("order_id", order_id);
			param.put("orderDgree", orderDgree);
			param.put("deptId", deptId);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			//获取分页数据
			List<Map<String, String>> orderList = orderService.getAllReviewOrder(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(orderList);
			
			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
		}
		
		/*pageWrite(response, pageBean);*/
		return pageBean;
	}
	/**
	 * 工单回访
	 * @param request
	 * @param response
	 * @param model
	 */
	/*@RequestMapping("/revewOrder")
	@ResponseBody
	public Result revewOrder(HttpServletRequest request, HttpServletResponse response, Model model, String review_id, String review_degree) {
		Map<String, String> param = new HashMap<String, String>();
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			param.put("maxaccept", review_id);
			param.put("reviewDegree", review_degree);
			param.put("orderStatus", "25");
			orderService.revewOrderByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}*/
	/**
	 *  bky
	 *  添加判断，如果满意度为不满意，则设置order_status = '26'
	 */
	@RequestMapping("/revewOrder")
	@ResponseBody
	public Result revewOrder(HttpServletRequest request, HttpServletResponse response, Model model, String review_id, String review_degree, String order) {
		Map<String, String> param = new HashMap<String, String>();
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		String status = order;
		
		try {
			if("24".equals(order)){
				param.put("maxaccept", review_id);
				param.put("reviewDegree", review_degree);
				if(review_degree.equals("32")){
					param.put("orderStatus", "26");
				}else{
					param.put("orderStatus", "25");
				}
			}else{
				param.put("maxaccept", review_id);
				param.put("reviewDegree", review_degree);
				if(review_degree.equals("32")){
					param.put("orderStatus", "26");
				}else{
					param.put("orderStatus", "80");
				}
			}
			
			orderService.revewOrderByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
	
	
	//查询全部工单数量
	@RequestMapping("/getOrderNumByTime")
	@ResponseBody
	public List<Map<String, Object>> getOrderNumByTime(String beginTime, String endTime, String userId, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		String userID = null;
		if(userId != null && userId != ""){
			userID = userId;
		}
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
			param.put("userId", userID);
			dataList = orderService.getOrderNumByTime(param);
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
	public List<Map<String, Object>> getAccessOrderNumByTime(String beginTime, String endTime, String userId, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		String userID = null;
		if(userId != null && userId != ""){
			userID = userId;
		}
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
			param.put("userId", userID);
			dataList = orderService.getAccessOrderNumByTime(param);
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
	public List<Map<String, Object>> getUnAccessOrderNumByTime(String beginTime, String endTime, String userId, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		String userID = null;
		if(userId != null && userId != ""){
			userID = userId;
		}
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
			param.put("userId", userID);
			dataList = orderService.getUnAccessOrderNumByTime(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
	}
	/**
	 * 公众号工单列表
	 * @param creat_time
	 * @param order_type
	 * @param fix_type
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/weixinList")
	@ResponseBody
	public PageBean weixinList(String begin_time, String end_time,String order_type, String orderStatus, 
			HttpServletRequest request, HttpServletResponse response, Model model){
		Date beginTime = null;
		Date endTime = null;
		PageBean pageBean = new PageBean();
		try{
			
			if (begin_time != null && !begin_time.equals("")) {
				beginTime = DateUtils.dateParse(begin_time + " 00:00:00", DateUtils.DATE_TIME_PATTERN);
			}
			if (end_time != null && !end_time.equals("")) {
				endTime = DateUtils.dateParse(end_time + " 23:59:59", DateUtils.DATE_TIME_PATTERN);
			}
			UserView userView = this.getUserView(request);
			String roleLevel = userView.getRoleLevel();
			String oprID = userView.getMaxaccept();
			String oprDeptID = userView.getDeptCode();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("page", Integer.parseInt(page));
			param.put("beginTime", beginTime);
			param.put("endTime", endTime);
			param.put("order_type", order_type);
			
			param.put("rows", Integer.parseInt(rows));
			param.put("roleLevel", roleLevel);
			param.put("oprID", oprID);
			param.put("oprDeptID", oprDeptID);
			param.put("orderStatus", orderStatus);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			//获取分页数据
			List<Map<String, String>> orderList = orderService.getAllWeixinQueryOrder(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(orderList);
			
			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
		}
		
		/*pageWrite(response, pageBean);*/
		return pageBean;
	}
	
	@RequestMapping("/changeWeixinOrder")
	@ResponseBody
	public Result changeWeixinOrder(String order_id, String opr_id, HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		if(order_id.contains(","))
			order_id = order_id.substring(0, order_id.length()-1);
		try {
//			orderService.removeOrder(ids);
			Map<String, String> param = new HashMap<String, String>();
			param.put("ids", order_id);
			param.put("opr_id", opr_id);
			orderService.addOrderOprInfoByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
		
	}
	/**
	 * 微信公众号投诉提交
	 * @param order_id
	 * @param opr_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/adviseSubmit")
	@ResponseBody
	public Result adviseSubmit(String advice_type, String fix_type, String cust_name, String cust_phone, String cust_address, String order_mark, HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("advice_type", advice_type);
			if("2".equals(advice_type))
				param.put("fix_type", fix_type);
			param.put("cust_name", cust_name);
			param.put("cust_phone", cust_phone);
			param.put("cust_address", cust_address);
			param.put("order_mark", order_mark);
			param.put("deptType", "61");
			
			/*****************客户资料存储******************/
			//判断是否已有用户信息
			List<Map<String, String>> custList = phoneInterfaceService.getCustInfoByAdress(param);
			String cust_id = "";
			if (custList == null || custList.size()==0) {
				cust_id = DBUtil.getMaxaccept(publicDao);
				param.put("maxaccept", cust_id);
				param.put("cust_name", cust_name);
				param.put("normal_phone", cust_phone);
				param.put("cust_adress", cust_address);
				phoneInterfaceService.inserCustInfo(param);
			}else{
				cust_id = custList.get(0).get("MAXACCEPT");
			}
			param.put("cust_id", cust_id);
			
			//判断联系方式是否存在
			Map<String, String> phoneMap = new HashMap<String, String>();
			phoneMap.put("maxaccept", cust_id);
			phoneMap.put("r_phone", cust_phone);
			List<Map<String, String>> conList = phoneInterfaceService.getConvertPhoneList(phoneMap);
			if (conList == null || conList.size() < 1) {
				phoneMap.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				phoneMap.put("conn_phone", cust_phone);
				phoneMap.put("custID", cust_id);
				phoneInterfaceService.insertConvertPhone(phoneMap);
			}

			
			/*****************工单创建******************/
			//获取客服部门
			List<Map<String, String>> deptList = deptService.getDeptByType(param);
			Map<String, String> dept = RandomUtil.getRandomObj(deptList);
			if(dept != null){
				param.put("deptCode", dept.get("MAXACCEPT"));
				param.put("roleLevel", "1");
				param.put("cirType", "0");
				//判断客服管理员是否可接单
				List<Map<String, String>> adminList = deptService.getOnlineUserByDeptCode(param);
				if(adminList != null && adminList.size() > 0){//管理员可接单，工单全部发给管理员
					param.put("deptCode", adminList.get(0).get("DEPT_CODE"));
				}else{
					param.put("roleLevel", "2");
					param.put("isOnlineStatus", "0");
					List<Map<String, String>> userList = deptService.isAdminAccept(param);
					Map<String, String> user = RandomUtil.getRandomObj(userList);
					if(user != null){
						param.put("oprID", user.get("MAXACCEPT"));
						param.put("oprName", user.get("USER_NAME"));
					}else{//部门下没有上线人员，随机获取全部人员信息分发
						param.put("roleLevel", "2");
						param.put("isOnlineStatus", "1");
						List<Map<String, String>> allUserList = deptService.isAdminAccept(param);
						Map<String, String> allUser = RandomUtil.getRandomObj(allUserList);
						if(allUser != null){
							param.put("oprID", allUser.get("MAXACCEPT"));
							param.put("oprName", allUser.get("USER_NAME"));
						}
					}
				}
				
				orderService.insertWeixinOrder(param);
			}
			
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
		
	}
	/**
	 * 发送验证码
	 * @param phone
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/adviseSendCode")
	@ResponseBody
	public Result adviseSendCode(String phone, HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("验证码发送成功！请注意检查短信");
		
		try {
			//发送验证码
			String num = (int)((Math.random()*9+1)*1000) + "";
			StringBuffer sendData = new StringBuffer();
			sendData.append("cdkey=" + ConfigUtil.getConfigKey("CD_KEY"));
			sendData.append("&password=" + ConfigUtil.getConfigKey("SMS_PWD"));
			sendData.append("&phone=" + phone);
			sendData.append("&message=欢迎使用同鑫热力投诉系统，您的短信验证码是" +num+ "，一小时内有效，请确保由您本人完成操作。如非本人操作，请忽略本短信。【同鑫热力】");
			sendData.append("&seqid=123");
			sendData.append("&addserial=");
			HttpUtil.doPost(sendData.toString());
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("phone", phone);
			param.put("codeNum", num);
			orderService.insertCodeNum(param);
			
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("验证码发送失败" + e);
		}
		return result;
	}
	/**
	 * 投诉查询验证码校验
	 * @param phone
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/adviseQuery")
	@ResponseBody
	public Result adviseQuery(String phone, String codeNum, HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("phone", phone);
			param.put("codeNum", codeNum);
			param.put("beginDate", DateUtils.dateAddHours(new Date(), -1));
			List<Map<String, String>> codeList = orderService.getCodeNum(param);
			if(codeList == null || codeList.size() == 0){
				result.setResultCode("0001");
				result.setResultMsg("验证码不正确！");
			}else{
				result.setResultData(phone);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("验证码发送失败" + e);
		}
		return result;
	}
	/**
	 * 查询投诉数据
	 * @param phone
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/adviseQueryData")
	public String adviseQueryData(String phone, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("phone", phone);
			param.put("orderChannel", "71");
			List<Map<String, String>> adviseList = orderService.getAdviseData(param);
			request.setAttribute("adviseList", adviseList);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("验证码发送失败" + e);
		}
		return "/transaction/weixin/advise_result";
	}
	/**
	 * 查看详情
	 * @param phone
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/adviseQueryDetail")
	public String adviseQueryDetail(String maxaccept, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", maxaccept);
			List<Map<String, String>> adviseList = orderService.getAdviseData(param);
			request.setAttribute("adviseList", adviseList);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("验证码发送失败" + e);
		}
		return "transaction/weixin/advise_detail";
	}
	
	/**
	 * 查询不满意工单数量
	 * @param beginTime
	 * @param endTime
	 * @param userId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getorderDegreeUnsatisfied")
	@ResponseBody
	public List<Map<String, Object>> getorderDegreeUnsatisfied(String beginTime, String endTime, String userId, HttpServletRequest request, HttpServletResponse response, Model model){
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		Date stratTime = null;
		Date overTime = null;
		String userID = null;
		if(userId != null && userId != ""){
			userID = userId;
		}
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
			param.put("userId", userID);
			dataList = orderService.getorderDegreeUnsatisfied(param);
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return dataList;
		
	}
	
	@RequestMapping("/getDgreeList")
	@ResponseBody
	public void getDgreeList(HttpServletRequest request, HttpServletResponse response, Model model){
		List<Map<String, String>> dgreeList = new ArrayList<Map<String, String>>();
		dgreeList = orderService.getDgreeList();
		write(response, dgreeList);
		
	}
	
	@RequestMapping("/getDeptList")
	@ResponseBody
	public void getDeptList(HttpServletRequest request, HttpServletResponse response, Model model){
		List<Map<String, String>> dgreeList = new ArrayList<Map<String, String>>();
		dgreeList = orderService.getDeptList();
		write(response, dgreeList);
		
	}
	/**
	 * 客服管理员回访工单流转
	 * @param send_review_id
	 * @param opr_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sendRevewOrder")
	@ResponseBody
	public Result sendRevewOrder(String send_review_id, String opr_id, HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("ids", send_review_id);
			param.put("opr_id", opr_id);
			orderService.addOrderOprInfoByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("验证码发送失败" + e);
		}
		return result;
	}
	
	/**
	 * 获取驳回工单
	 * @param begin_Time
	 * @param end_Time
	 * @param fix_type
	 * @param kfManagerId
	 * @param orderChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/returnOrderList")
	@ResponseBody
	public PageBean returnOrderList(String begin_time, String end_time, String fix_type, String kfManagerId, String orderChannel, HttpServletRequest request, HttpServletResponse response, Model model){
		Date stratTime = null;
		Date overTime = null;
		
		PageBean pageBean = new PageBean();
		
		//List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try{
			if (begin_time != null && !begin_time.equals("")) {
				stratTime = DateUtils.dateParse(begin_time + " 00:00:00", DateUtils.DATE_TIME_PATTERN);
			}
			if (end_time != null && !end_time.equals("")) {
				overTime = DateUtils.dateParse(end_time + " 23:59:59", DateUtils.DATE_TIME_PATTERN);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			param.put("page", Integer.parseInt(page));
			param.put("rows", Integer.parseInt(rows));
			param.put("creatTime", stratTime);
			param.put("overTime", overTime);
			param.put("fixType", fix_type);
			param.put("kfManagerId", kfManagerId);
			param.put("orderChannel", orderChannel);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			List<Map<String, Object>> dataList = orderService.returnOrderList(param);
			PageInfo<Map<String, Object>> pageinfo = new PageInfo<Map<String, Object>>(dataList);
			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(dataList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return pageBean;
	}
	
	/**
	 * 驳回工单
	 * @param ids
	 * @param fix_dept_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/returnOrder")
	@ResponseBody
	public Result returnOrder(String[] ids,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			orderService.returnOrderInIds(ids);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result; 
	}
}
