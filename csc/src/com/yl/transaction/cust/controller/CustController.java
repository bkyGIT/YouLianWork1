package com.yl.transaction.cust.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yl.common.controller.BaseController;
import com.yl.transaction.cust.service.CustService;

@Controller
@RequestMapping("cust")
public class CustController extends BaseController{

	@Resource
	private CustService custService;
	
	@RequestMapping("/getCustInfoByID")
	@ResponseBody
	public Map<String, String> getCustInfoByID(HttpServletRequest request, HttpServletResponse response, Model model, String custID) {
		Map<String, String> custInfo = null;
		try{
			custInfo = custService.getCustInfoByID(custID);
		}catch(Exception e){
			logger.error(e);
		}
		return custInfo;
	}
}
