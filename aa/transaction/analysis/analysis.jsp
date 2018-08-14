<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>首页分析</title>

<script type="text/javascript" src="<%=webpath%>/transaction/analysis/analysis.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	
	$(function() {
		//先根据默认时间查询数据，避免首页只有时间而无数据显示
		getTime();
		//其次可以根据指定时间查询数据
		getChart();
	});
	
	var query=function(){
		getChart();
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
				<span class="name">首页</span>
			</div>
			<!-- 中央区域 -->
			<div id="work_panel">
				<!--查询-->
				<div class="top_search">
					<input id="begin_time_param" type="text" class="easyui-datebox"
						style="width:200px;" data-options="label:'查询时间:'"> 至 <input
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
	
</body>
	
	<!-- 图表 -->
	<div id="line" style="min-width:100%;height:50%"></div>
	
</html>
