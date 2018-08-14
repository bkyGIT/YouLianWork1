package com.yl.transaction.trun.controller;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.pojo.PageBean;
import com.yl.common.pojo.Result;
import com.yl.common.util.DBUtil;
import com.yl.transaction.trun.service.TrunService;

@Controller
@RequestMapping("/trun")
public class TrunController extends BaseController{

	@Resource
	private TrunService trunService;
	
	@Resource
	private PublicDao publicDao;
	
	/**
	 * 获取呼转列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadTrunPhoneList")
	@ResponseBody
	public PageBean loadTrunPhoneList(HttpServletRequest request, HttpServletResponse response, Model model, String trun_phone, String dept_id){
		PageBean pageBean = new PageBean();
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			param.put("page", Integer.parseInt(page));
			param.put("rows", Integer.parseInt(rows));
			param.put("trunPhone", trun_phone);
			param.put("deptID", dept_id);
			PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
			//获取分页数据
			List<Map<String, String>> orderList = trunService.getTrunPhoneList(param);
			PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(orderList);
			
			pageBean.setTotal(pageinfo.getTotal()+"");
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		}catch(Exception e){
			logger.error(e);
		}
		
		return pageBean;
	}
	/**
	 * 添加联系人
	 * @param order_id
	 * @param opr_id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/addConnInfo")
	@ResponseBody
	public Result addConnInfo(String add_dept_id, String add_conn_phone, String add_conn_name, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("deptID", add_dept_id);
			param.put("connPhone", add_conn_phone);
			param.put("connName", add_conn_name);
			trunService.addConnInfo(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
		
	}
	/**
	 * 联系方式修改
	 * @param add_dept_id
	 * @param add_conn_phone
	 * @param add_conn_name
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/modifyConnInfo")
	@ResponseBody
	public Result modifyConnInfo(String modify_id, String modify_dept_id, String modify_conn_phone, String modify_conn_name, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", modify_id);
			param.put("deptID", modify_dept_id);
			param.put("connPhone", modify_conn_phone);
			param.put("connName", modify_conn_name);
			trunService.modifyConnInfoByMax(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
	/**
	 * 删除联系方式
	 * @param modify_id
	 * @param modify_dept_id
	 * @param modify_conn_phone
	 * @param modify_conn_name
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/delConnInfo")
	@ResponseBody
	public Result delConnInfo(String ids, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		
		try {
			if(ids.contains(",")){
				ids = ids.substring(0, ids.length()-1);
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ids", ids);
			trunService.delConnInfoInMax(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
}
