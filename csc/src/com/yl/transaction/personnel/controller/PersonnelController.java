package com.yl.transaction.personnel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.pojo.PageBean;
import com.yl.common.util.DBUtil;
import com.yl.transaction.personnel.service.PersonnelService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController extends BaseController{

	@Resource
	private PersonnelService personnelService;
	
	@Resource
	private PublicDao publicDao;
	
	@RequestMapping("/personnelList")
	public void personnelList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", Integer.parseInt(page));
		param.put("rows", Integer.parseInt(rows));
		//获取分页数据
		List<Map<String, String>> personnelList = personnelService.getAllPersonnel(param);
		//获取总数量
		String total = personnelService.getTotalPersonnel();
		PageBean pageBean = new PageBean();
		pageBean.setTotal(total);
		pageBean.setRows(personnelList);
		pageBean.setSuccess("success");
		pageWrite(response, pageBean);
	}
	/**
	 * 增加人员
	 * @param request
	 * @param response
	 * @param model
	 */
	/*@RequestMapping("/addPersonnel")
	public void addPersonnel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String account = request.getParameter("personnel_account");
		String pwd = request.getParameter("personnel_pwd");
		String name = request.getParameter("personnel_name");
		String phone = request.getParameter("personnel_phone");
		String tel = request.getParameter("personnel_tel");
		String address = request.getParameter("personnel_address");
		String role = request.getParameter("personnel_role");
		String dept = request.getParameter("personnel_dept");
		Map<String, String> param = new HashMap<String, String>();
		param.put("account", account);
		param.put("pwd", pwd);
		param.put("name", name);
		param.put("phone", phone);
		param.put("tel", tel);
		param.put("address", address);
		param.put("role", role);
		param.put("dept", dept);
		param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			List<Map<String, String>> person = personnelService.getPersonnelByAccount(account);
			if(person.size() < 1){
				personnelService.addPersonnel(param);
			}else{
				result.setResultCode("9999");
				result.setResultMsg("登录账户已存在，添加失败!");
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}*/
	
	/**
	 * 增加人员(bky:增加vos账号和密码绑定)
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addPersonnel")
	public void addPersonnel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String account = request.getParameter("personnel_account");
		String pwd = request.getParameter("personnel_pwd");
		String name = request.getParameter("personnel_name");
		String phone = request.getParameter("personnel_phone");
		String tel = request.getParameter("personnel_tel");
		String address = request.getParameter("personnel_address");
		String role = request.getParameter("personnel_role");
		String dept = request.getParameter("personnel_dept");
		
		//bky:增加vos账号及密码
		String vos = request.getParameter("vos_account");
		String vospwd = request.getParameter("vos_pwd");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("account", account);
		param.put("pwd", pwd);
		param.put("name", name);
		param.put("phone", phone);
		param.put("tel", tel);
		param.put("address", address);
		param.put("role", role);
		param.put("dept", dept);
		param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
		
		param.put("vos", vos);
		param.put("vospwd", vospwd);
		
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			List<Map<String, String>> person = personnelService.getPersonnelByAccount(account);
			if(person.size() < 1){
				//判断vos账号是否已存在
				List<Map<String, String>> vosAccount = personnelService.getPersonnelByVos(vos);
				if(vosAccount.size() < 1){
					personnelService.addPersonnel(param);
				}else{
					result.setResultCode("9999");
					result.setResultMsg("vos账号已存在，添加失败!");
				}
			}else{
				result.setResultCode("9999");
				result.setResultMsg("登录账户已存在，添加失败!");
			}
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
	@RequestMapping("/delPersonnel")
	public void delPersonnel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String ids = request.getParameter("ids");
		if(ids.contains(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			personnelService.delPersonnelInIDS(ids);
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
	@RequestMapping("/modifyPersonnel")
	public void modifyPersonnel(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maxaccept = request.getParameter("personnel_maxaccept");
		String account = request.getParameter("personnel_account");
		String pwd = request.getParameter("personnel_pwd");
		String name = request.getParameter("personnel_name");
		String phone = request.getParameter("personnel_phone");
		String tel = request.getParameter("personnel_tel");
		String address = request.getParameter("personnel_address");
		String role = request.getParameter("personnel_role");
		String dept = request.getParameter("personnel_dept");
		
		//bky:添加vos账号及密码绑定
		String vos = request.getParameter("vos_account");
		String vospwd = request.getParameter("vos_pwd");
		
		Map<String, String> param = new HashMap<String, String>();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try {
			param.put("maxaccept", maxaccept);
			param.put("account", account);
			param.put("pwd", pwd);
			param.put("name", name);
			param.put("phone", phone);
			param.put("tel", tel);
			param.put("address", address);
			param.put("role", role);
			param.put("dept", dept);
			
			param.put("vos", vos);
			param.put("vospwd", vospwd);
			
			personnelService.updatePersonnelByID(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		write(response, result);
	}
}
