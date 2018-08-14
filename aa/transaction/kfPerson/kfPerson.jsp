<%@page import="com.yl.common.user.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
	
	User user = (User)session.getAttribute("userInfo");
	String kfMaxaccept = user.getMaxaccept();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客服人员</title>

<script type="text/javascript" src="<%=webpath%>/transaction/kfManager/kfManager.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	var kfMaxaccept = '<%=kfMaxaccept%>';
	
	$(function() {
		//先根据默认时间查询数据，避免首页只有时间而无数据显示
		getTime();
		//其次可以根据指定时间查询数据
		getChart(kfMaxaccept, '');
	});
	
	var query=function(){
		getChart(kfMaxaccept, '');
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
				<span class="name">客服人员首页</span>
			</div>
			<!-- 中央区域 -->
			<div id="work_panel">
				<!--查询-->
				<div class="top_search">
					<!-- 添加查询受理渠道 -->
					<input name="order_channel" id="order_channel"
						class="easyui-combobox" style="width:200px;"
						data-options="
							url: '<%=webpath%>/kfManager/getChannelList.action',
							method: 'post',
							valueField:'CODE_ID',
							textField:'CODE_NAME',
							groupField:'group',
							label: '受理渠道:',
							labelPosition: 'left',
							panelHeight:'auto',
							editable:false">	
					<input id="begin_time_param" type="text" class="easyui-datebox"
						style="width:200px;" data-options="label:'查询时间:',editable:false"> 至 <input
						id="end_time_param" type="text" class="easyui-datebox" data-options="editable:false"
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
<body>	

	<!-- 图表 -->
	<div id="phoneCount" style="width:75%;height:40%;float:left"></div>
	<div style="min-width:5px;height:200px;float:left"></div>
	<div id="phoneDegree" style="width:24%;height:40%;float:left"></div>
	<div class="space_hx">&nbsp;</div>
	<div id="phoneTime" style="width:75%;height:40%;float:left"></div>
	<div style="min-width:5px;height:200px;float:left"></div>
	<div id="orderStatus" style="width:24%;height:40%;float:left"></div>
	
</html>