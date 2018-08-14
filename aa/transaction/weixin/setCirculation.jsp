<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>投诉流转设置</title>
<script type="text/javascript" src="<%=webpath%>/transaction/weixin/setCirculation.js"></script>
</head>
<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function(){
		loadCir();
	});
</script>

<style type="text/css">
.top_search {
	height: 40px;
	margin: 0px auto;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background: #FFF;
	margin-top: 20px;
	text-align: left;
	line-height: 40px;
}

.menu_before {
	margin-left: 30px;
}

.menu_botton {
	margin-left: 10px;
}
</style>
<body>
	<div id="right_ctn">
		<div class="right_m">
			<div class="hy_list">
				<div class="box_t">
					<span class="name">投诉流转设置</span>
				</div>
				<!-- 中央区域 -->
				<div id="work_panel" style="width:100%;height:92%;">
					<div class="top_search" style="padding-top:8px;">
						<span class="menu_before"></span> 
						<a href="javascript:saveCir();" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-reload'">保存</a>  
					</div>
					<div style="padding:20px;margin-top:10px;height:200px;text-align: left;background-color:rgba(254, 254, 254, 1);">
						<input id="self_work" type="radio" name="work_type" value="0">：客服管理员自己处理
						<br/>
						<br/>
						<br/>
						<input id="other_work" type="radio" name="work_type" value="1">：均发至其他客服人员
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
