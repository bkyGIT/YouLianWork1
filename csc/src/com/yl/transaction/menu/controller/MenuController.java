package com.yl.transaction.menu.controller;

import java.util.ArrayList;
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
import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.user.pojo.UserView;
import com.yl.common.util.DBUtil;
import com.yl.transaction.menu.pojo.Menu;
import com.yl.transaction.menu.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	@Resource
	private MenuService menuService;

	@Resource
	private PublicDao publicDao;

	/**
	 * 左侧菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/loadMenu")
	public void loadMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Menu> pmList = null;
		List<Menu> smList = null;
		UserView userView = this.getUserView(request);
		if (userView != null) {
			String roleMenus = userView.getRoleMenus();
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", roleMenus);
			// 获取子菜单
			param.put("menuLevel", "1");
			smList = menuService.getMenuInIDByLevel(param);
			
			String pid = "";
			for(Menu son : smList){
				pid = son.getPid() + "," + pid;
			}
			if(pid.contains(","))
				pid= pid.substring(0, pid.length()-1);
			// 获取父菜单
			param.put("menuLevel", "0");
			param.put("maxaccept", pid);
			pmList = menuService.getMenuInIDByLevel(param);
		}
		Map<String, Object> menu = new HashMap<String, Object>();
		menu.put("pMenu", pmList);
		menu.put("sMenu", smList);

		write(response, menu);
	}

	/**
	 * 菜单管理列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/menuList")
	public void menuList(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		List<Menu> pmList = null;
		List<Menu> smList = null;
		UserView userView = this.getUserView(request);
		if (userView != null) {
			// 获取子菜单
			Map<String, String> param = new HashMap<String, String>();
			param.put("menuLevel", "1");
			smList = menuService.getMenuInIDByLevel(param);
			// 获取父菜单
			param.put("menuLevel", "0");
			pmList = menuService.getMenuInIDByLevel(param);

			// 拼接菜单列表
			for (Menu pm : pmList) {
				Map<String, Object> parentMap = new HashMap<String, Object>();
				parentMap.put("id", pm.getMaxaccept());
				parentMap.put("text", pm.getMenuName());
				List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
				for (Menu sm : smList) {
					if (pm.getMaxaccept().equals(sm.getPid())) {
						Map<String, String> childMap = new HashMap<String, String>();
						childMap.put("id", sm.getMaxaccept());
						childMap.put("text", sm.getMenuName());
						childList.add(childMap);
					}
				}
				parentMap.put("children", childList);
				finalList.add(parentMap);
			}
		}
		write(response, finalList);
	}

	/**
	 * 获取菜单信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/showMenuInfo")
	public void showMenuInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maxaccept = request.getParameter("menuID");
		Menu menu = menuService.getMenuByID(maxaccept);

		write(response, menu);
	}

	/**
	 * 保存当前菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/saveMenu")
	public void saveMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
		String saveFlag = request.getParameter("saveFlag");

		String menuName = request.getParameter("menu_name");
		String menuID = request.getParameter("menu_id");
		String menuUrl = request.getParameter("menu_url");
		String showFlag = request.getParameter("show_flag");

		Map<String, String> menuMap = new HashMap<String, String>();
		menuMap.put("menuName", menuName);
		menuMap.put("menuID", menuID);
		menuMap.put("menuUrl", menuUrl);
		menuMap.put("showFlag", showFlag);

		// 修改操作
		if ("save".equals(saveFlag)) {
			menuService.updateMenuByID(menuMap);
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "0000");
		resultMap.put("resultMsg", "操作成功!");
		resultMap.put("menuID", menuID);
		write(response, resultMap);
	}

	/**
	 * 添加菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addMenu")
	public void addMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 新增标识，0添加一级菜单1添加二级菜单
		String addFlag = request.getParameter("add_flag");
		// 上级菜单ID
		String parentMenuID = request.getParameter("add_menu_id");

		String menuName = request.getParameter("menu_name");
		String menuID = request.getParameter("add_menu_id");
		String menuUrl = request.getParameter("menu_url");
		String showFlag = request.getParameter("show_flag");

		Map<String, String> menuMap = new HashMap<String, String>();
		menuMap.put("menuName", menuName);
		menuMap.put("menuID", menuID);
		menuMap.put("menuUrl", menuUrl);
		menuMap.put("showFlag", showFlag);
		menuMap.put("menuLevel", addFlag);

		String maxIndex = "";
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "0000");
		resultMap.put("resultMsg", "操作成功!");
		resultMap.put("menuID", menuID);
		try {
			// 添加下级菜单给父ID赋值，并且获取排序
			if ("1".equals(addFlag)) {
				menuMap.put("parentMenuID", parentMenuID);
				maxIndex = menuService.getMaxIndeByParenID(parentMenuID);
				// 当没有子菜单
				if (StringUtils.isBlank(maxIndex)) {
					Menu pMenu = menuService.getMenuByID(parentMenuID);
					maxIndex = pMenu.getMenuIndex() + "1";
				} else {
					maxIndex = Integer.parseInt(maxIndex) + 1 + "";
				}

			} else {
				menuMap.put("parentMenuID", "");
				maxIndex = menuService.getMaxInde();
				maxIndex = Integer.parseInt(maxIndex) + 1 + "";
			}
			menuMap.put("menuIndex", maxIndex);
			menuMap.put("maxaccept", DBUtil.getMaxaccept(publicDao));

			menuService.addMenu(menuMap);
		} catch (Exception e) {
			logger.error("操作失败!" + e);
			resultMap.put("resultCode", "9999");
			resultMap.put("resultMsg", "操作失败！" + e);
		}

		write(response, resultMap);
	}

	/**
	 * 删除菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/deleteMenu")
	public void deleteMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
		String menuID = request.getParameter("menuID");
		String menuLevel = request.getParameter("menuLevel");

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "0000");
		resultMap.put("resultMsg", "操作成功!");
		try {
			if ("0".equals(menuLevel)) {
				List<Menu> sList = menuService.getMenusByParenID(menuID);
				for (Menu menu : sList) {
					menuService.deleteMenuByID(menu.getMaxaccept());
				}
			}
			menuService.deleteMenuByID(menuID);
		} catch (Exception e) {
			logger.error("操作失败!" + e);
			resultMap.put("resultCode", "9999");
			resultMap.put("resultMsg", "操作失败！" + e);
		}

		write(response, resultMap);
	}
}
