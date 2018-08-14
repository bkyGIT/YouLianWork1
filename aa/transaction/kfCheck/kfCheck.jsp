<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import=" com.yl.common.user.pojo.User"%>
<%
	String webpath = request.getContextPath();
	User user = (User) request.getSession().getAttribute("userInfo");
	request.setAttribute("roleLevel", user.getRoleLevel());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>考核管理</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/kfCheck/kfCheck.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';

	$(function() {
		/* getChart(); */
		pie();
		column();
	});

	var query = function() {
		getQuery();
	};
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
}
</style>
</head>

<body>
	<div id="right_ctn">
		<div class="right_m">
			<div class="box_t">
				<span class="name">考核查询</span>
			</div>
			<!-- 中央区域 -->
			<div id="work_panel" style="width:100%;height:97%;">
				<!--查询-->
				<div class="top_search">
					<input name="fix_type_param" id="fix_type_param"
						class="easyui-combobox" style="width:200px;"
						data-options="
											url: '<%=webpath%>/order/getFixTypeList.action',
											method: 'post',
											valueField:'CODE_ID',
											textField:'CODE_NAME',
											groupField:'group',
											label: '客服工号:',
											labelPosition: 'left',
											panelHeight:'auto'">

					<input name="order_status" id="order_status"
						class="easyui-combobox" style="width:200px;"
						data-options="
											url: '<%=webpath%>/order/getCommonCode.action?codeKey=ORDER_STATUS&codeIDS=21,22,23,24,25',
											method: 'post',
											valueField:'CODE_ID',
											textField:'CODE_NAME',
											groupField:'group',
											label: '客服姓名:',
											labelPosition: 'left',
											panelHeight:'auto'">

					<input id="begin_time_param" type="text" class="easyui-datebox"
						style="width:200px;" data-options="label:'考核时间:'"> 至 <input
						id="end_time_param" type="text" class="easyui-datebox"
						style="width:120px;"> <a href="javascript:query()"
						class="easyui-linkbutton menu_botton"
						data-options="iconCls:'icon-search'" style="width:80px">查询</a> <a
						href="#" id="clearQuery" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'" style="width:80px">重置</a>
				</div>
				<div class="space_hx">&nbsp;</div>
			</div>
		</div>
	</div>


	<div id="left" style="width: 400px;height:400px;float:left"></div>
	<div id="right" style="width: 700px;height:400px;float:left"></div>



</body>


</html>

