package com.yl.transaction.dept.controller;

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
import com.yl.common.util.DBUtil;
import com.yl.transaction.dept.service.DeptService;

@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController{

	@Resource
	private DeptService deptService;
	
	@Resource
	private PublicDao publicDao;
	
	@RequestMapping("/deptList")
	@ResponseBody
	public PageBean deptList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Map<String, Object> param = new HashMap<String, Object>();
		//获取分页数据
		PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		List<Map<String, String>> deptlList = deptService.getPageDept(param);
		PageInfo<Map<String, String>> pageinfo = new PageInfo<Map<String, String>>(deptlList);
		//获取总数量
		String total = pageinfo.getTotal()+"";
		PageBean pageBean = new PageBean();
		pageBean.setTotal(total);
		pageBean.setRows(deptlList);
		pageBean.setSuccess("success");
		return pageBean;
	}
	/**
	 * 增加人员
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addDept")
	public void addPersonnel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String deptName = request.getParameter("dept_name");
		String deptDes = request.getParameter("dept_des");
		Map<String, String> param = new HashMap<String, String>();
		param.put("deptName", deptName);
		param.put("deptDes", deptDes);
		param.put("dept_type", request.getParameter("dept_type"));
		param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			deptService.addDept(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/delDept")
	public void delDept(HttpServletRequest request, HttpServletResponse response, Model model) {
		String ids = request.getParameter("ids");
		if(ids.contains(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			deptService.delDeptInIDS(ids);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}
	/**
	 * 修改人员信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/modifyDept")
	public void modifyDept(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maxaccept = request.getParameter("dept_maxaccept");
		String deptName = request.getParameter("dept_name");
		String deptDes = request.getParameter("dept_des");
		Map<String, String> param = new HashMap<String, String>();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			param.put("maxaccept", maxaccept);
			param.put("deptName", deptName);
			param.put("deptDes", deptDes);
			param.put("dept_type", request.getParameter("dept_type"));
			deptService.updateDeptByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}
	/**
	 * 获取全部部门信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getAllDept")
	public void getAllDept(HttpServletRequest request, HttpServletResponse response, Model model) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		List<Map<String, String>> deptList = null;
		try {
			deptList = deptService.getAllDept();
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, deptList);
	}
	
}
