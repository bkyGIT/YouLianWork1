<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yl.common.user.pojo.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String webpath = request.getContextPath();
	User user = (User)session.getAttribute("userInfo");
	String kfManager = user.getMaxaccept();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>驳回工单查询</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/kfManagerIntercept/kfManagerIntercept.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	var kfManagerMaxaccept = '<%=kfManager%>';
	
	$(function() {
		//加载分页
		loadPage(kfManagerMaxaccept);
		
		//工单类型时间
		/* $("#o_order_types").combobox({
			onSelect: function(param){
				if(param.CODE_ID == "2"){
					$("#order_fix_type_div").show();
				}else{
					$("#order_fix_type_div").hide();
				}
			}
		}); */
		
		//当工单类型选择为非维护类型时，设置维护类型选项不可操作
		/* $("#order_type_param").combobox({
			onSelect: function(param){
				if(param.CODE_ID != "2"){
					$("#fix_type_param").combobox({disabled: true});
				}else{
					$("#fix_type_param").combobox({disabled: false});
				}
			}
		}); */
		
	});
	
	var query=function(){
		loadPage(kfManagerMaxaccept);
	};
	
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
	min-width: 1138px;
}

.menu_before {
	margin-left: 30px;
}

.menu_botton {
	margin-left: 10px;
}
</style>
</head>

<body>
	<div id="right_ctn">
		<div class="right_m">
			<div class="hy_list">
				<div class="box_t">
					<span class="name">驳回工单查询</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:92%;">
							<!--查询-->
							<div class="top_search" style="padding-left:20px;">
								<%-- <input name="order_type_param" id="order_type_param"
									class="easyui-combobox" style="width:200px;"
									data-options="
										url: '<%=webpath%>/order/getOrderTypeList.action',
										method: 'post',
										valueField:'CODE_ID',
										textField:'CODE_NAME',
										groupField:'group',
										label: '工单类型:',
										labelPosition: 'left',
										panelHeight:'auto',editable:false"> --%>
								<input name="fix_type_param" id="fix_type_param" 
									class="easyui-combobox" style="width:200px;"
									data-options="
										url: '<%=webpath%>/order/getFixTypeList.action',
										method: 'post',
										valueField:'CODE_ID',
										textField:'CODE_NAME',
										groupField:'group',
										label: '维护类型:',
										labelPosition: 'left',
										panelHeight:'auto',
										editable:false">
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
									<input id="creat_time_param" type="text" class="easyui-datebox" style="width:200px;" data-options="label:'工单创建时间:',editable:false">
									至<input id="end_time_param" type="text" class="easyui-datebox" data-options="editable:false" style="width:120px;">
									
									<a href="javascript:query()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-search'">查询</a>
									<a href="#" id="clearQuery" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">重置</a>
							</div>
							
								<div class="top_search">
									<span class="menu_before"></span> 
									<a href="javascript:showReturnOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">驳回工单</a>  
								</div>
							
							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<!-- 修改标记 -->
							<table id="order_list_table" style="width:98%;height:92%;"></table>
						</div>
			</div>
		</div>
	</div>
</body>
</html>
