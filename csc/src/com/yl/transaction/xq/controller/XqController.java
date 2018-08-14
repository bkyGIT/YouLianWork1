package com.yl.transaction.xq.controller;

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
import com.yl.transaction.xq.service.XqService;

@Controller
@RequestMapping("/xq")
public class XqController extends BaseController{

	@Resource
	private XqService xqService;
	
	@RequestMapping("/getAllXqInfo")
	@ResponseBody
	public List<Map<String, String>> getAllXqInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, String>> xqList = null;
		try{
			xqList = xqService.getAllXqInfo();
		}catch(Exception e){
			logger.error(e);
		}
		return xqList;
	}
}
