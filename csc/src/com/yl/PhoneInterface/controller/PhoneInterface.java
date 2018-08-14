package com.yl.PhoneInterface.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yl.PhoneInterface.service.PhoneInterfaceService;
import com.yl.common.controller.BaseController;
import com.yl.common.dao.PublicDao;
import com.yl.common.pojo.PageBean;
import com.yl.common.pojo.Result;
import com.yl.common.user.pojo.User;
import com.yl.common.user.pojo.UserView;
import com.yl.common.user.service.UserService;
import com.yl.common.util.ConfigUtil;
import com.yl.common.util.DBUtil;
import com.yl.common.util.DateUtils;
import com.yl.common.util.ProcUtil;
import com.yl.common.util.StrUtil;
import com.yl.login.service.LoginService;

@Controller
@RequestMapping("/PhoneInterface")
public class PhoneInterface extends BaseController {

	@Resource
	private LoginService loginService;

	@Resource
	private PhoneInterfaceService phoneInterfaceService;

	@Resource
	private UserService userService;

	@Resource
	private PublicDao publicDao;

	/**
	 * 呼叫中心登录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/PhoneLogin")
	public void phoneLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		String userName = request.getParameter("UserName");
		String userPass = request.getParameter("UserPass");

		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("userName", userName);
			param.put("pwd", userPass);
			User user = loginService.getUserLogin(param);
			StringBuffer sb = new StringBuffer("1");
			sb.append("|");
			sb.append("SeatID=" + user.getMaxaccept());
			sb.append("|");
			sb.append("SeatGroupID="+user.getDeptCode());
			sb.append("|");
			sb.append("BusinessGroupID=1");
			sb.append("|");
			sb.append("SipServer=" + ConfigUtil.getConfigKey("VOS_SERVER"));
			sb.append("|");
			sb.append("SipPort=" + ConfigUtil.getConfigKey("VOS_PORT"));
			sb.append("|");
			sb.append("SipUser=" + user.getVosAccount());
			sb.append("|");
			sb.append("SipPass=" + user.getVosPwd());
			sb.append("|");
			sb.append("SipExpires=60");
			allWrite(response, sb.toString());
		} catch (Exception e) {
			logger.error(e);
			allWrite(response, 0);
		}

	}

	/**
	 * 设置外呼号码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/setPhoneList")
	@ResponseBody
	public Result setPhoneList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String phone = request.getParameter("phone");
		String orderID = request.getParameter("orderID");
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");

		try {
			UserView user = getUserView(request);
			String seatID = user.getMaxaccept();
			// 判断坐席状态
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", seatID);
			String flag = phoneInterfaceService.getOnlineFlag(param);
			if ("0".equals(flag)) {
				result.setResultCode("0001");
				result.setResultMsg("未签到无法外呼！");
			} else {
				String exePath = ConfigUtil.getConfigKey("EXE_PATH");
				String procName = exePath.substring(exePath.lastIndexOf("\\") + 1, exePath.length());
				boolean exist = ProcUtil.findProcess(procName);
				if (exist) {
					Map<String, String> para = new HashMap<String, String>();
					para.put("maxaccept", DBUtil.getMaxaccept(publicDao));
					para.put("seatID", seatID);
					para.put("phone", phone);
					phoneInterfaceService.setPhoneList(para);

					// 预先记录通话信息表,后续上传根据外呼号码和坐席ID补全通话信息表
					para.put("oprName", user.getUserName());
					String cdID = DBUtil.getMaxaccept(publicDao);
					para.put("maxaccept", cdID);
					para.put("orderID", orderID);
					para.put("inFlag", "0");
					phoneInterfaceService.insertCSInfo(para);

					// 工单表记录话单ID
					/*para.put("csID", cdID);
					phoneInterfaceService.setOrderOutCSID(para);*/

				} else {
					result.setResultCode("0002");
					result.setResultMsg("外呼程序已掉线，请重新签退再操作！");
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
	 * 获取号码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/CallPhoneList")
	public void callPhoneList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = request.getParameter("Type");
		String seatID = request.getParameter("SeatID");

		try {
			String callPhone = "";
			Map<String, String> param = new HashMap<String, String>();
			param.put("type", type);
			param.put("seatID", seatID);
			List<Map<String, String>> phoneList = phoneInterfaceService.getPhoneList(param);
			if (phoneList != null && phoneList.size() > 0) {
				callPhone = phoneList.get(0).get("PHONE");
				param.put("maxaccept", phoneList.get(0).get("MAXACCEPT"));
				param.put("status", "1");
				phoneInterfaceService.updatePhoneStatus(param);
			}
			allWrite(response, callPhone);

		} catch (Exception e) {
			logger.error(e);
		}

	}

	/**
	 * 获取在线状态
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/OnlineSeatList")
	public void onlineSeatList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = request.getParameter("Type");
		String seatID = request.getParameter("SeatID");

		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("seatID", seatID);
			if ("setoffline".equals(type))
				param.put("flag", "0");
			else
				param.put("flag", "1");
			// 操作坐席状态表
			phoneInterfaceService.insertOnlineFlag(param);
			allWrite(response, 1);
		} catch (Exception e) {
			logger.error(e);
		}

	}
	/**
	 * 前台获取在线状态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadOnlineStatus")
	@ResponseBody
	public Result loadOnlineStatus(HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);

		try {
			UserView userView = this.getUserView(request);
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", userView.getMaxaccept());
			String onLineFlag = phoneInterfaceService.getOnlineFlag(param);
			result.setResultData(onLineFlag);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}

	/**
	 * 保存通话记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/SaveCallRecord")
	public void saveCallRecord(HttpServletRequest request, HttpServletResponse response, Model model) {
		String SeatGroupID = request.getParameter("SeatGroupID");// 部门ID
		String BusinessGroupID = request.getParameter("BusinessGroupID");// 组ID
		String SeatID = request.getParameter("SeatID");// 坐席ID
		String Phone = request.getParameter("Phone");// 电话号码
		String CallTime = request.getParameter("CallTime");// 呼叫时间
		String TalkTime = request.getParameter("TalkTime");// 通话时间
		String HangupTime = request.getParameter("HangupTime");// 挂机时间
		String WaitLength = request.getParameter("WaitLength");// 等待时长
		String TalkLength = request.getParameter("TalkLength");// 通话时长
		String TalkFlag = request.getParameter("TalkFlag");// 是否通话
		String RecordFile = request.getParameter("RecordFile");// 录音文件名
		String InFlag = request.getParameter("InFlag");// 是否为呼入
		String TypeID = request.getParameter("TypeID");// 类型ID
		String TheGuid = request.getParameter("TheGuid");// GUID
		String CallType = request.getParameter("CallType");// 呼叫类型

		try {
			UserView user = userService.getUserView(SeatID);
			Map<String, String> param = new HashMap<String, String>();
			String csID = DBUtil.getMaxaccept(publicDao);
			param.put("maxaccept", csID);
			param.put("SeatGroupID", SeatGroupID);
			param.put("BusinessGroupID", BusinessGroupID);
			param.put("SeatID", SeatID);
			param.put("oprName", user.getUserName());
			param.put("Phone", Phone);
			param.put("CallTime", CallTime);
			param.put("TalkTime", TalkTime);
			param.put("HangupTime", HangupTime);
			param.put("WaitLength", WaitLength);
			param.put("TalkLength", TalkLength);
			if ("False".equals(TalkFlag))
				param.put("TalkFlag", "0");
			else {
				param.put("TalkFlag", "1");
			}
			param.put("RecordFile", RecordFile);
			if ("False".equals(InFlag))
				param.put("InFlag", "0");
			else
				param.put("InFlag", "1");
			param.put("TypeID", TypeID);
			param.put("TheGuid", TheGuid);
			param.put("CallType", CallType);
			// 判断是否有待回填的话单，系统外呼、呼入的会预先创建话单，需补填。软电话外呼的新增话单(此处还有个BUG，当系统外呼后意外终端，比如点击签退或进程结束软电话时，软电话时不会补填通话记录的，下次相同坐席用软电话外呼相同号码回补填上个记录)
			List<Map<String, String>> csList = phoneInterfaceService.getCSBySeatAndPhone(param);
			if (csList != null && csList.size() > 0) {
				param.put("csID", csList.get(0).get("MAXACCEPT"));
				phoneInterfaceService.updateCSInfo(param);
			} else {// 软电话主动外呼的需要重新创建话单
				phoneInterfaceService.insertCSAllInfo(param);
				//获取CSID为空的订单
				List<Map<String, String>> orderList = phoneInterfaceService.getOrderInfoByInfo(param);
				if(orderList != null && orderList.size()>0){
					String orderID = orderList.get(0).get("MAXACCEPT");
					param.put("orderID", orderID);
					param.put("csID", csID);
					phoneInterfaceService.setOrderCSID(param);
				}
			}
			allWrite(response, 1);
		} catch (Exception e) {
			logger.error(e);
			allWrite(response, 0);
		}

	}

	/**
	 * 通话录音上传
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param file
	 */
	@RequestMapping("/UploadRecord")
	public void iploadRecord(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("file") CommonsMultipartFile file) {

		try {
			String dateDir = DateUtils.dateFormat(new Date(), "yyyy-MM-dd");
			String path = ConfigUtil.getConfigKey("SOUNT_FILE") + "/" + dateDir;
			String filePath = ConfigUtil.getConfigKey("SOUNT_FILE") + "/" + dateDir + "/" + file.getOriginalFilename();

			File dirPath = new File(path);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}

			File dealPath = new File(filePath);
			// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
			file.transferTo(dealPath);
			allWrite(response, 1);
		} catch (Exception e) {
			logger.error(e);
			allWrite(response, 0);
		}

	}

	/**
	 * 坐席签到
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginPhone")
	@ResponseBody
	public Result loginPhone(HttpServletRequest request, HttpServletResponse response, Model model, String seatID) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");

		/*String exePath = ConfigUtil.getConfigKey("EXE_PATH");
		String procName = exePath.substring(exePath.lastIndexOf("\\") + 1, exePath.length());
		boolean exist = ProcUtil.findProcess(procName);*/
		try {
			/*if (exist) {
				// 存在，那么就先杀死该进程
				ProcUtil.killProc(procName);
				// 在启动
				ProcUtil.startProc(exePath);
			} else {
				ProcUtil.startProc(exePath);
			}*/
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("seatID", seatID);
			param.put("flag", "1");
			// 操作坐席状态表
			phoneInterfaceService.insertOnlineFlag(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}

	/**
	 * 坐席签退
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/logoutPhone")
	@ResponseBody
	public Result logoutPhone(HttpServletRequest request, HttpServletResponse response, Model model, String seatID) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");

		/*String exePath = ConfigUtil.getConfigKey("EXE_PATH");
		String procName = exePath.substring(exePath.lastIndexOf("\\") + 1, exePath.length());
		boolean exist = ProcUtil.findProcess(procName);*/
		try {
			/*if (exist) {
				// 存在，那么就先杀死该进程
				ProcUtil.killProc(procName);
			} else {
				result.setResultCode("0000");
				result.setResultMsg("当前状态即为签退，不需要重复签退!");
			}*/
			// 操作坐席状态表
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("seatID", seatID);
			param.put("flag", "0");
			phoneInterfaceService.insertOnlineFlag(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("catalina.home"));
	}

	/**
	 * 弹屏接收,设置来电号码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/SetPopWindow")
	public void setPopWindow(HttpServletRequest request, HttpServletResponse response, Model model) {
		String seatID = request.getParameter("SeatID");
		String phone = request.getParameter("Phone");

		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("seatID", seatID);
			param.put("phone", phone);
			phoneInterfaceService.insertIntoPhone(param);

			allWrite(response, 1);
		} catch (Exception e) {
			logger.error(e);
			allWrite(response, 0);
		}

	}
	/**
	 * 设置坐席置忙置闲
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/setSeatOnlineState")
	@ResponseBody
	public Result setSeatOnlineState(HttpServletRequest request, HttpServletResponse response, Model model) {
		String status = request.getParameter("status");

		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			UserView userView = this.getUserView(request);
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("seatID", userView.getMaxaccept());
			param.put("status", status);
			phoneInterfaceService.setPhoneStatus(param);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
	/**
	 * 前台获取忙闲状态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadSeatOnlineState")
	@ResponseBody
	public Result loadSeatOnlineState(HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			UserView userView = this.getUserView(request);
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", userView.getMaxaccept());
			List<Map<String, String>> statusList = phoneInterfaceService.getPhoneStatus(param);
			if(statusList != null && statusList.size()>0){
				String status = statusList.get(0).get("STATUS");
				result.setResultData(status);
			}else{
				result.setResultData(1);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}
		return result;
	}
	/**
	 * 软电话获取坐席示闲示忙状态
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/GetSeatOnlineState")
	public void GetSeatOnlineState(HttpServletRequest request, HttpServletResponse response, Model model) {
//		String type = request.getParameter("Type");
		String seatID = request.getParameter("SeatID");

		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", seatID);
			List<Map<String, String>> statusList = phoneInterfaceService.getPhoneStatus(param);
			if(statusList != null && statusList.size()>0){
				allWrite(response, statusList.get(0).get("STATUS"));
			}else{
				allWrite(response, 1);
			}
		} catch (Exception e) {
			logger.error(e);
			allWrite(response, 0);
		}

	}

	/**
	 * 轮训查询是否有来电
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPhone")
	@ResponseBody
	public Result queryPhone(HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		UserView user = this.getUserView(request);
		try {
			//预留空工单编码
			String maxaccept = DBUtil.getMaxaccept(publicDao);
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", user.getMaxaccept());
			param.put("status", "0");
			List<Map<String, String>> phoneList = phoneInterfaceService.getIntoPhoneBySeatID(param);
			if (phoneList != null && phoneList.size() > 0) {
				Map<String, String> inPhone = phoneList.get(0);
				inPhone.put("reback_order_id", maxaccept);
				result.setResultData(inPhone);

				// 改变呼入状态为已提取
				param.put("status", "1");
				param.put("maxaccept", phoneList.get(0).get("MAXACCEPT"));
				phoneInterfaceService.updateIntoPhoneStatus(param);
				
				//预留工单
				Map<String, String> orderParam = new HashMap<String, String>();
				orderParam.put("maxaccept", maxaccept);
				orderParam.put("oprID", user.getMaxaccept());
				orderParam.put("oprName", user.getUserName());
				orderParam.put("deptCode", user.getDeptCode());
				orderParam.put("phone", phoneList.get(0).get("PHONE"));
				orderParam.put("rebackStatus", "0");
				phoneInterfaceService.insertRebackOrder(orderParam);
				
				//插入通话记录
				param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				param.put("orderID", maxaccept);
				param.put("phone", phoneList.get(0).get("PHONE"));
				param.put("oprName", user.getUserName());
				param.put("seatID", user.getMaxaccept());
				param.put("inFlag", "1");
				phoneInterfaceService.insertCSInfo(param);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return result;
	}

	/**
	 * 根据号码获取用户列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCustInfoList")
	@ResponseBody
	public PageBean getCustInfoList(HttpServletRequest request, HttpServletResponse response, Model model, String phone) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		PageBean pageBean = new PageBean();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("phone", phone);
			List<Map<String, String>> custList = phoneInterfaceService.getCustInfo(param);
			if (custList != null && custList.size() > 0) {
				pageBean.setRows(custList);
				pageBean.setSuccess("success");
			} else {
				pageBean.setTotal("0");
				pageBean.setRows(new ArrayList<Map<String, String>>());
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return pageBean;
	}

	/**
	 * 根据地址获取客户列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getCustInfoListByAdress")
	@ResponseBody
	public PageBean getCustInfoListByAdress(HttpServletRequest request, HttpServletResponse response, Model model, String query_adress) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		PageBean pageBean = new PageBean();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("query_adress", query_adress);
			List<Map<String, String>> custList = phoneInterfaceService.getCustInfoByLikeAdress(param);
			if (custList != null && custList.size() > 0) {
				pageBean.setRows(custList);
				pageBean.setSuccess("success");
			} else {
				/*************** 调用计费接口处 **************/

				/*************** 调用计费接口处 **************/
				pageBean.setTotal("0");
				pageBean.setRows(new ArrayList<Map<String, String>>());
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return pageBean;
	}

	/**
	 * 增加客户信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param query_adress
	 * @return
	 */
	@RequestMapping("/addCustInfo")
	@ResponseBody
	public Result addCustInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String cust_id = request.getParameter("cust_id");
		String into_phone = request.getParameter("into_phone");
		String add_cust_name = request.getParameter("add_cust_name");
		String add_cust_phone = request.getParameter("add_cust_phone");
		String add_cust_adress = request.getParameter("add_cust_adress");

		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			Map<String, String> param = new HashMap<String, String>();
			// 根据客户ID判断，有客户ID新增映射表，没有新增映射表和客户表
			if (StringUtils.isBlank(cust_id)) {
				cust_id = DBUtil.getMaxaccept(publicDao);
				param.put("maxaccept", cust_id);
				param.put("cust_name", add_cust_name);
				param.put("normal_phone", add_cust_phone);
				param.put("cust_adress", add_cust_adress);
				phoneInterfaceService.inserCustInfo(param);
			}

			// 插入常用号码
			param.put("maxaccept", cust_id);
			param.put("r_phone", add_cust_phone);
			List<Map<String, String>> conList = phoneInterfaceService.getConvertPhoneList(param);
			if (conList == null || conList.size() < 1) {
				param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				param.put("conn_phone", add_cust_phone);
				param.put("custID", cust_id);
				phoneInterfaceService.insertConvertPhone(param);
			}

			// 插入来电号码
			if (!into_phone.equals(add_cust_phone)) {
				param.put("maxaccept", cust_id);
				param.put("r_phone", into_phone);
				List<Map<String, String>> inList = phoneInterfaceService.getConvertPhoneList(param);
				if (inList == null || inList.size() < 1) {
					param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
					param.put("conn_phone", into_phone);
					param.put("custID", cust_id);
					phoneInterfaceService.insertConvertPhone(param);
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
	 * 根据模糊地址获取周边客户信息
	 * 
	 * @param likeAdress
	 * @param adress
	 * @return
	 */
	public Map<String, Map<String, String>> getAroundInfo(String likeAdress, String adress) {
		Map<String, Map<String, String>> aroundInfo = new HashMap<String, Map<String, String>>();
		Map<String, String> leftCust = new HashMap<String, String>();
		Map<String, String> rightCust = new HashMap<String, String>();

		/** 获取左邻右舍 **/
		Map<String, String> param = new HashMap<String, String>();
		param.put("likeAdress", likeAdress);
		List<Map<String, String>> dyInfo = phoneInterfaceService.getCustDyInfo(param);
		for (int ix = 0; ix < dyInfo.size(); ix++) {
			Map<String, String> map = dyInfo.get(ix);
			if (map.get("ADRESS").equals(adress)) {
				leftCust = dyInfo.get(ix - 1);
				if (dyInfo.size() > (ix + 1)) {
					rightCust = dyInfo.get(ix + 1);
					aroundInfo.put("rightCust", rightCust);
				}
				aroundInfo.put("leftCust", leftCust);
			}
		}

		/** 获取楼上楼下 **/
		if (adress.indexOf("单元") != -1) {
			String upNum = "";
			String lowNum = "";
			String strNum = StrUtil.filterChinese(adress.substring(adress.indexOf("单元"), adress.length()));
			if (strNum.length() == 4) {
				strNum = strNum.substring(0, 2);

				// 判断楼层是否开头包含0
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(strNum);
					if (strNum.indexOf(num + "") > 0) {
						upNum = strNum.substring(0, strNum.indexOf(num + "")) + (num + 1) + strNum.substring(2);
						lowNum = strNum.substring(0, strNum.indexOf(num + "")) + (num - 1) + strNum.substring(2);
					} else {
						upNum = num + 1 + strNum.substring(2);
						lowNum = (num - 1) + strNum.substring(2);
					}
				}
			} else {
				strNum = strNum.substring(0, 1);

				// 判断楼层是否开头包含0
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(strNum);
					if (strNum.indexOf(num + "") > 0) {
						upNum = strNum.substring(0, strNum.indexOf(num + "")) + (num + 1) + strNum.substring(1);
						lowNum = strNum.substring(0, strNum.indexOf(num + "")) + (num - 1) + strNum.substring(1);
					} else {
						upNum = num + 1 + strNum.substring(1);
						lowNum = (num - 1) + strNum.substring(1);
					}
				}
			}

			String upAdress = likeAdress + upNum;
			String lowAdress = likeAdress + lowNum;
			// 获取楼下
			param.put("likeAdress", lowAdress);
			List<Map<String, String>> lowList = phoneInterfaceService.getCustDyInfo(param);
			aroundInfo.put("lowCust", lowList.get(0));
			// 获取楼上
			param.put("likeAdress", upAdress);
			List<Map<String, String>> upList = phoneInterfaceService.getCustDyInfo(param);
			aroundInfo.put("upCust", upList.get(0));
		}

		return aroundInfo;
	}

	/**
	 * 前台加载客户周边信息
	 * 
	 * @param likeAdress
	 * @param adress
	 * @return
	 */
	@RequestMapping("/loadAroundInfo")
	@ResponseBody
	public Result loadAroundInfo(HttpServletRequest request, HttpServletResponse response, Model model, String adress) {

		Map<String, Map<String, String>> aroundInfo = new HashMap<String, Map<String, String>>();

		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			if (adress.indexOf("单元") != -1) {
				/** 获取左邻右舍 **/
				Map<String, String> param = new HashMap<String, String>();

				String upadress = getDYInfo(adress, "up");
				param.put("likeAdress", upadress);
				List<Map<String, String>> upInfo = phoneInterfaceService.getCustDyInfo(param);
				if(upInfo!= null && upInfo.size()>0)
					aroundInfo.put("upCust", upInfo.get(0));

				String lowadress = getDYInfo(adress, "low");
				param.put("likeAdress", lowadress);
				List<Map<String, String>> lowInfo = phoneInterfaceService.getCustDyInfo(param);
				if(lowInfo!= null && lowInfo.size()>0)
					aroundInfo.put("lowCust", lowInfo.get(0));

				String leftadress = getDYInfo(adress, "left");
				param.put("likeAdress", leftadress);
				List<Map<String, String>> leftInfo = phoneInterfaceService.getCustDyInfo(param);
				if(leftInfo!= null && leftInfo.size()>0)
					aroundInfo.put("leftCust", leftInfo.get(0));

				String rightadress = getDYInfo(adress, "right");
				param.put("likeAdress", rightadress);
				List<Map<String, String>> rightInfo = phoneInterfaceService.getCustDyInfo(param);
				if(rightInfo!= null && rightInfo.size()>0)
					aroundInfo.put("rightCust", rightInfo.get(0));

			}

			result.setResultData(aroundInfo);
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return result;
	}

	/**
	 * 获取周围门号方法
	 * 
	 * @param adress
	 * @param flag
	 * @return
	 */
	public String getDYInfo(String adress, String flag) {
		String likeAdress = adress.substring(0, adress.indexOf("单元") + 2);
		String strNum = StrUtil.filterChinese(adress.substring(adress.indexOf("单元"), adress.length()));
		String returnAdress = "";
		if ("up".equals(flag)) {
			String upNum = "";
			String endNum = "";
			if (strNum.length() == 4) {
				endNum = strNum.substring(2);
				strNum = strNum.substring(0, 2);
				// 判断楼层是否开头包含0
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(strNum);
					if (strNum.indexOf(num + "") > 0) {
						upNum = strNum.substring(0, strNum.indexOf(num + "")) + (num + 1) + endNum;
					} else {
						upNum = num + 1 + endNum;
					}
				}
			} else if (strNum.length() == 3) {
				endNum = strNum.substring(1);
				strNum = strNum.substring(0, 1);

				// 判断楼层是否开头包含0
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(strNum);
					if (strNum.indexOf(num + "") > 0) {
						upNum = strNum.substring(0, strNum.indexOf(num + "")) + (num + 1) + endNum;
					} else {
						upNum = num + 1 + endNum;
					}
				}
			}
			returnAdress = likeAdress + upNum;
		} else if ("low".equals(flag)) {
			String lowNum = "";
			String endNum = "";
			if (strNum.length() == 4) {
				endNum = strNum.substring(2);
				strNum = strNum.substring(0, 2);
				// 判断楼层是否开头包含0
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(strNum);
					if (strNum.indexOf(num + "") > 0) {
						lowNum = strNum.substring(0, strNum.indexOf(num + "")) + (num - 1) + endNum;
					} else {
						lowNum = (num - 1) + endNum;
					}
				}
			} else if (strNum.length() == 3) {
				endNum = strNum.substring(1);
				strNum = strNum.substring(0, 1);

				// 判断楼层是否开头包含0
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(strNum);
					if (strNum.indexOf(num + "") > 0) {
						lowNum = strNum.substring(0, strNum.indexOf(num + "")) + (num - 1) + endNum;
					} else {
						lowNum = (num - 1) + endNum;
					}
				}
			}

			returnAdress = likeAdress + lowNum;
		} else if ("left".equals(flag)) {
			String leftNum = "";
			String endNum = "";
			if (strNum.length() == 4) {
				endNum = strNum.substring(2);
				strNum = strNum.substring(0, 2);
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(endNum);
					// 判断楼层是否开头包含0
					if (endNum.indexOf(num + "") > 0) {
						leftNum = strNum + endNum.substring(0, endNum.indexOf(num + "")) + (num - 1);
					} else {
						leftNum = strNum + (num - 1);
					}
				}
			} else if (strNum.length() == 3) {
				endNum = strNum.substring(1);
				strNum = strNum.substring(0, 1);

				// 判断楼层是否开头包含0
				if (!"".equals(endNum)) {
					int num = Integer.parseInt(endNum);
					if (endNum.indexOf(num + "") > 0) {
						leftNum = strNum + endNum.substring(0, endNum.indexOf(num + "")) + (num - 1);
					} else {
						leftNum = strNum + (num - 1);
					}
				}
			}

			returnAdress = likeAdress + leftNum;
		} else if ("right".equals(flag)) {
			String rightNum = "";
			String endNum = "";
			if (strNum.length() == 4) {
				endNum = strNum.substring(2);
				strNum = strNum.substring(0, 2);
				if (!"".equals(strNum)) {
					int num = Integer.parseInt(endNum);
					// 判断楼层是否开头包含0
					if (endNum.indexOf(num + "") > 0) {
						rightNum = strNum + endNum.substring(0, endNum.indexOf(num + "")) + (num + 1);
					} else {
						rightNum = strNum + (num + 1);
					}
				}
			} else if (strNum.length() == 3) {
				endNum = strNum.substring(1);
				strNum = strNum.substring(0, 1);

				// 判断楼层是否开头包含0
				if (!"".equals(endNum)) {
					int num = Integer.parseInt(endNum);
					if (endNum.indexOf(num + "") > 0) {
						rightNum = strNum + endNum.substring(0, endNum.indexOf(num + "")) + (num + 1);
					} else {
						rightNum = strNum + (num + 1);
					}
				}
			}

			returnAdress = likeAdress + rightNum;
		}

		return returnAdress;
	}

	/**
	 * 轮训查询是否有来电(bak)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bak")
	@ResponseBody
	public Result bak(HttpServletRequest request, HttpServletResponse response, Model model) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserView user = this.getUserView(request);
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", user.getMaxaccept());
			param.put("status", "0");
			List<Map<String, String>> phoneList = phoneInterfaceService.getIntoPhoneBySeatID(param);
			if (phoneList != null && phoneList.size() > 0) {
				Map<String, String> intoMap = phoneList.get(0);
				String phone = intoMap.get("PHONE");
				// 加载客户以及周边信息
				param.put("phone", phone);
				List<Map<String, String>> custList = phoneInterfaceService.getCustInfo(param);
				if (custList != null && custList.size() > 0) {
					Map<String, String> custInfo = custList.get(0);
					resultMap.put("custInfo", custList);

					String adress = custInfo.get("ADRESS");
					if (adress.indexOf("单元") != -1) {
						String likeAdress = adress.substring(0, adress.indexOf("单元") + 2);
						Map<String, Map<String, String>> aroundInfo = getAroundInfo(likeAdress, adress);
						resultMap.put("leftCust", aroundInfo.get("leftCust"));
						resultMap.put("rightCust", aroundInfo.get("rightCust"));
						resultMap.put("lowCust", aroundInfo.get("lowCust"));
						resultMap.put("upCust", aroundInfo.get("upCust"));
					}
				}
				resultMap.put("phone", phoneList.get(0));
				result.setResultData(resultMap);

				// 改变呼入状态为已提取
				param.put("status", "1");
				param.put("maxaccept", phoneList.get(0).get("MAXACCEPT"));
				phoneInterfaceService.updateIntoPhoneStatus(param);
			}
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return result;
	}

	/**
	 * 获取历史工单记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getOrderInfoList")
	@ResponseBody
	public PageBean getOrderInfoList(HttpServletRequest request, HttpServletResponse response, Model model, String custID) {
		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		PageBean pageBean = new PageBean();
		try {
			Map<String, String> param = new HashMap<String, String>();
			// 加载客户以及周边信息
			param.put("custID", custID);
			List<Map<String, String>> orderList = phoneInterfaceService.getOrderInfoByCustID(param);
			pageBean.setRows(orderList);
			pageBean.setSuccess("success");
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return pageBean;
	}

	/**
	 * 工单保存
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveOrder")
	@ResponseBody
	public Result saveOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		String order_type = request.getParameter("order_type");
		String fix_type = request.getParameter("fix_type");
		String order_mark = request.getParameter("order_mark");
		String conn_phone = request.getParameter("conn_phone");
		String custID = request.getParameter("custID");
		String cust_name = request.getParameter("cust_name");
		String cust_adress = request.getParameter("cust_adress");
		String normal_phone = request.getParameter("normal_phone");
		String normal_flag = request.getParameter("normal_flag");
		String add_phone_flag = request.getParameter("add_phone_flag");
		String phone_num = request.getParameter("phone_num");
		String reback_order_id = request.getParameter("reback_order_id");

		Result result = new Result();
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			UserView user = getUserView(request);
			Map<String, String> param = new HashMap<String, String>();
			param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			param.put("opr_id", user.getMaxaccept());
			param.put("opr_name", user.getUserName());
			param.put("opr_dept_id", user.getDeptCode());
			param.put("order_channel", "70");
			param.put("order_status", "20");
			param.put("order_type", order_type);
			if ("2".equals(order_type)) {
				param.put("fix_type", fix_type);
			}
			param.put("order_mark", order_mark);
			param.put("conn_phone", conn_phone);
			param.put("custID", custID);
			param.put("cust_name", cust_name);
			param.put("cust_adress", cust_adress);
			param.put("normal_phone", normal_phone);

			if ("1".equals(normal_flag)) {// 变更了常用联系电话
				// 修改客户表
				phoneInterfaceService.updateNormalPhone(param);
				// 修改映射表
				// phoneInterfaceService.updateConvertPhone(param);
			}

			if ("1".equals(add_phone_flag)) {// 未知的号码
				// 添加联系电话至映射表
				phoneInterfaceService.insertConvertPhone(param);
			}

			/**增加来电、常用联系电话、联系电话关联**/
			param.put("r_phone", phone_num);
			param.put("maxaccept", custID);
			List<Map<String, String>> numList = phoneInterfaceService.getConvertPhoneList(param);
			if(numList == null || numList.size() == 0){
				param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				param.put("conn_phone", phone_num);
				phoneInterfaceService.insertConvertPhone(param);
			}
			
			param.put("r_phone", normal_phone);
			List<Map<String, String>> normalList = phoneInterfaceService.getConvertPhoneList(param);
			if(normalList == null || normalList.size() == 0){
				param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				param.put("conn_phone", normal_phone);
				phoneInterfaceService.insertConvertPhone(param);
			}
			
			param.put("r_phone", conn_phone);
			List<Map<String, String>> connList = phoneInterfaceService.getConvertPhoneList(param);
			if(connList == null || connList.size() == 0){
				param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
				param.put("conn_phone", conn_phone);
				phoneInterfaceService.insertConvertPhone(param);
			}
			/**增加来电、常用联系电话、联系电话关联 end**/
			
			//param.put("maxaccept", DBUtil.getMaxaccept(publicDao));
			/*phoneInterfaceService.inserCustInfo(param);*/
			
			// 回填工单
			param.put("maxaccept", reback_order_id);
			param.put("reback_status", "1");
			phoneInterfaceService.rebackOrder(param);
			
			
		} catch (Exception e) {
			logger.error(e);
			result.setResultCode("9999");
			result.setResultMsg("操作失败!" + e);
		}

		return result;
	}

	/**
	 * 关系解绑
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param custID
	 * @return
	 */
	@RequestMapping("/removeRelase")
	@ResponseBody
	public Result removeRelase(HttpServletRequest request, HttpServletResponse response, Model model, String phone_num, String normal_phone,
			String table_phone, String maxaccept) {
		result.setResultCode("0000");
		result.setResultMsg("操作成功！");
		result.setResultData(null);
		try {
			Map<String, String> param = new HashMap<String, String>();

			param.put("maxaccept", maxaccept);
			param.put("phone_num", phone_num);
			param.put("normal_phone", normal_phone);
			param.put("table_phone", table_phone);
			phoneInterfaceService.removePhoneRelase(param);

			// 当解绑是常用联系方式，查询映射表找一条最近的联系方式
			if (normal_phone.equals(table_phone)) {
				List<Map<String, String>> phoneList = phoneInterfaceService.getConvertPhoneList(param);
				if (phoneList != null && phoneList.size() > 0) {
					param.put("custID", maxaccept);
					param.put("normal_phone", phoneList.get(0).get("R_PHONE"));
					phoneInterfaceService.updateNormalPhone(param);
				} else {// 当前只有唯一联系方式，将客户电话改为空，情况非常少
					param.put("custID", maxaccept);
					param.put("normal_phone", "");
					phoneInterfaceService.updateNormalPhone(param);
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
	 * 发送二次拨号（转移）
	 * @param request
	 * @param response
	 * @param model
	 * @param file
	 */
	@RequestMapping("/GetTurnCode")
	public void GetTurnCode(HttpServletRequest request, HttpServletResponse response, Model mode) {
		String seatID = request.getParameter("SeatID");
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("seatID", seatID);
			List<Map<String, String>> phoneList = phoneInterfaceService.getTrunPhoneList(param);
			if(phoneList != null && phoneList.size() > 0){
				Map<String, String> phone = phoneList.get(0);
				//设置已转移状态
				phoneInterfaceService.updateTrunPhone(param);
				allWrite(response, "*" +phone.get("TRUN_PHONE") + "#");
			}else{
				allWrite(response, "");
			}
		} catch (Exception e) {
			logger.error(e);
			allWrite(response, "");
		}

	}
}
