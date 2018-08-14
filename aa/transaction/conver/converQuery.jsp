<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.yl.common.user.pojo.User"%>
<%@ page import="com.yl.common.util.ConfigUtil"%>
<%
	String webpath = request.getContextPath();
	User user = (User) request.getSession().getAttribute("userInfo");
	request.setAttribute("roleLevel", user.getRoleLevel());
	
	//获取录音地址
	String sountFile = ConfigUtil.getConfigKey("SOUNT_FILE");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>通话查询</title>
<script type="text/javascript" src="<%=webpath%>/transaction/conver/converQuery.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	var sountFile = '<%=sountFile%>';
	var userLevel = '<%=user.getRoleLevel()%>';
	$(function(){
		loadPage();
		
		if(userLevel != "1"){
			$("#kf_info_div").hide();
		}
	});
</script>
<style type="text/css">
.top_search {
	height: 40px;
	width: 96.6%;
	margin: 0px auto;
	margin-left: 0px;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background: #FFF;
	margin-top: 20px;
	text-align: left;
	line-height: 40px;
	padding-left: 40px;
	min-width: 1138px;
}
</style>
</head>

<body>
	<div id="right_ctn">
		<div class="right_m">
			<div class="box_t">
				<span class="name">通话查询</span>
			</div>
			<!-- 中央区域 -->
			<div id="work_panel" style="width:100%;height:92%;">
				<!--查询-->
				<div class="top_search">
					<div id="kf_info_div" style="float:left;margin-top:6px;margin-right: 10px;">
						<input name="kf_param" id="kf_param"
							class="easyui-combobox" style="width:200px;"
							data-options="url: '<%=webpath%>/conver/getUserInfoByRoleAndDept.action?roleLevel=2&deptType=61',
												method: 'post',
												valueField:'MAXACCEPT',
												textField:'USER_NAME',
												groupField:'group',
												label: '客服姓名:',
												labelPosition: 'left',
												panelHeight:'auto',
												editable:false">
					</div>
					<input class="easyui-textbox" name="order_id" id="order_id" style="width:200px;" data-options="label:'工单编号:'">
					<input name="talk_flag_param" id="talk_flag_param" class="easyui-combobox" style="width:200px;"
									data-options=" url: '<%=webpath%>/order/getCommonCode.action?codeKey=TALK_FLAG',
														method: 'post',
														valueField:'CODE_ID',
														textField:'CODE_NAME',
														groupField:'group',
														label: '接通状态:',
														labelPosition: 'left',
														panelHeight:'auto',
														editable:false">
					<input name="in_flag_param" id="in_flag_param" class="easyui-combobox" style="width:200px;"
									data-options=" url: '<%=webpath%>/order/getCommonCode.action?codeKey=IN_FLAG',
														method: 'post',
														valueField:'CODE_ID',
														textField:'CODE_NAME',
														groupField:'group',
														label: '呼叫方向:',
														labelPosition: 'left',
														panelHeight:'auto',
														editable:false">
					<input id="begin_time_param" type="text" class="easyui-datebox" style="width:200px;" data-options="label:'通话时间:',editable:false"> 至 
					<input id="end_time_param" type="text" class="easyui-datebox" style="width:120px;" data-options="editable:false"> 
					<a href="javascript:loadPage()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-search'" style="width:80px">查询</a> 
					<a href="#" id="clearQuery" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px">重置</a>
				</div>
				
				<!-- 展示区域 -->
				<div class="space_hx">&nbsp;</div>
				<!-- 修改标记 -->
				<table id="conver_list_table" style="width:98%;height:90%;"></table>
			</div>
		</div>
	</div>


<!-- 播放控件 -->
<bgsound id="snd_ie" src=""/>
</body>
</html>

