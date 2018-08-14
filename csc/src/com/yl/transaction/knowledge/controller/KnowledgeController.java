package com.yl.transaction.knowledge.controller;

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

import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.enums.CommonCode;
import com.yl.common.util.DBUtil;
import com.yl.transaction.knowledge.service.KnowledgeService;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController{

	@Resource
	private KnowledgeService knowledgeService;

	@Resource
	private PublicDao publicDao;
	
	/**
	 * 加载知识库列表
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/knowledgeList")
	public void knowledgeList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		List<Map<String, String>> treeList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> knowList = null;
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try{
			knowList = knowledgeService.getAllknowledgeType();
			for(Map<String, String> map : knowList){
				Map<String, String> treeMap = new HashMap<String, String>();
				CommonCode cc = CommonCode.valueOf(map.get("CODE_KEY"));
				treeMap.put("id", map.get("CODE_KEY"));
				treeMap.put("text", cc.getType());
				treeList.add(treeMap);
			}
		}catch(Exception e){
			result.setResultCode("9999");
			result.setResultMsg("加载知识库列表失败！");
			logger.error("加载知识库列表失败！" + e);
		}
		write(response, treeList);
	}
	/**
	 * 获取知识库信息
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/showKnowledgeInfo")
	public void showKnowledgeInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String codeKey = request.getParameter("konwID");
		List<Map<String, String>> knowList = null;
		result.setResultCode("0000");
		result.setResultMsg("操作成功！！");
		try{
			knowList = knowledgeService.getKonwledgeByKey(codeKey);
			result.setResultData(knowList);
		}catch(Exception e){
			result.setResultCode("9999");
			result.setResultMsg("加载知识库列表失败！");
			logger.error("加载知识库列表失败！" + e);
		}
		write(response, knowList);
	}
	/**
	 * 添加知识库
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addKnowledge")
	public void addKnowledge(HttpServletRequest request, HttpServletResponse response, Model model) {
		String codeKey = request.getParameter("knowledge_code");
		String codeName = request.getParameter("knowledge_name");
		String codeID = request.getParameter("knowledge_id");
		result.setResultCode("0000");
		result.setResultMsg("操作成功！！");
		try{
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("codeKey", codeKey);
			param.put("codeName", codeName);
			param.put("codeID", codeID);
			knowledgeService.addKnowledge(param);
		}catch(Exception e){
			result.setResultCode("9999");
			result.setResultMsg("添加知识库列表失败！"+ e);
			logger.error("添加知识库列表失败！" + e);
		}
		write(response, result);
	}
	/**
	 * 修改知识库
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/editKnowledge")
	public void editKnowledge(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maxaccept = request.getParameter("knowledge_maxaccept");
		String codeName = request.getParameter("edit_name");
		String codeID = request.getParameter("edit_id");
		result.setResultCode("0000");
		result.setResultMsg("操作成功！！");
		try{
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", maxaccept);
			param.put("codeName", codeName);
			param.put("codeID", codeID);
			knowledgeService.updateKnowledgeByMaxaccept(param);
		}catch(Exception e){
			result.setResultCode("9999");
			result.setResultMsg("修改知识库列表失败！"+ e);
			logger.error("修改知识库列表失败！" + e);
		}
		write(response, result);
	}
	/**
	 * 删除知识库
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/delKnowledge")
	public void delKnowledge(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maxaccept = request.getParameter("ids");
		if(maxaccept.contains(",")){
			maxaccept = maxaccept.substring(0, maxaccept.length()-1);
		}
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		try{
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", maxaccept);
			knowledgeService.delKnowledgeByMaxaccept(maxaccept);
		}catch(Exception e){
			result.setResultCode("9999");
			result.setResultMsg("修改知识库列表失败！"+ e);
			logger.error("修改知识库列表失败！" + e);
		}
		write(response, result);
	}
}
