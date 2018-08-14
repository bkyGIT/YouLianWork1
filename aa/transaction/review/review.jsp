<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import=" com.yl.common.user.pojo.User" %>
<%
	String webpath = request.getContextPath();
	User user = (User) request.getSession().getAttribute("userInfo");
	String userLevel = user.getRoleLevel();
	String deptType = user.getDeptType();
	request.setAttribute("roleLevel", user.getRoleLevel());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>客服回访查询</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/review/review.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		//加载分页
		loadPage();
		
		//当工单类型选择为非维护类型时，设置维护类型选项不可操作
		$("#order_type_param").combobox({
			onSelect: function(param){
				if(param.CODE_ID != "2"){
					$("#fix_type_param").combobox({disabled: true});
				}else{
					$("#fix_type_param").combobox({disabled: false});
				}
			}
		});
	});
	var query=function(){
		loadPage();
	};
</script>
<style type="text/css">
.top_search {
	height: 40px;
	width:96.6%;
	margin: 0px auto;
	margin-left:0px;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background: #FFF;
	margin-top: 20px;
	text-align: left;
	line-height: 40px;
	padding-left:40px;
	min-width: 1138px;
}

.top_menu{
	height: 40px;
	width:99%;
	margin: 0px auto;
	margin-left:0px;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background: #FFF;
	margin-top: 10px;
	text-align: left;
	line-height: 40px;
	padding-top:10px;
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
					<span class="name">回访查询</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:97%;">
							<!--查询-->
							<div class="top_search">
								<input name="order_type_param" id="order_type_param" class="easyui-combobox" style="width:200px;"
									data-options="url: '<%=webpath%>/order/getOrderTypeList.action',
										method: 'post',
										valueField:'CODE_ID',
										textField:'CODE_NAME',
										groupField:'group',
										label: '工单类型:',
										labelPosition: 'left',
										panelHeight:'auto',editable:false">
										
								<input name="fix_type_param" id="fix_type_param" class="easyui-combobox" style="width:200px;"
									data-options="url: '<%=webpath%>/order/getFixTypeList.action',
										method: 'post',
										valueField:'CODE_ID',
										textField:'CODE_NAME',
										groupField:'group',
										label: '维护类型:',
										labelPosition: 'left',
										panelHeight:'auto',
										editable:false">
										
								<input name="order_status" id="order_status" class="easyui-combobox" style="width:200px;"
								data-options="url: '<%=webpath%>/order/getCommonCode.action?codeKey=ORDER_STATUS&codeIDS=24,29',
									method: 'post',
									valueField:'CODE_ID',
									textField:'CODE_NAME',
									groupField:'group',
									label: '工单状态:',
									labelPosition: 'left',
									panelHeight:'auto',
									editable:false">
									
								<%-- <input class="easyui-combobox" id="fix_xq" style="width:300px;" data-options="
								url: '<%=webpath%>/xq/getAllXqInfo.action',
								method: 'post',
								valueField: 'MAXACCEPT',
								textField: 'XQ_NAME',
								panelWidth: 215,
								panelHeight: 'auto',
								label: '维修小区:',
								labelPosition: 'left'
								"> --%>
								<input id="begin_time_param" type="text" class="easyui-datebox" style="width:200px;" data-options="label:'工单创建时间:',editable:false">
								至
								<input id="end_time_param" type="text" class="easyui-datebox" data-options="editable:false" style="width:120px;">
								
								<a href="javascript:query()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-search'"  style="width:80px">查询</a>
								<a href="#" id="clearQuery" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px">重置</a>
							</div>

							<div class="top_menu">
								<span class="menu_before"></span>  
									<%if("2".equals(userLevel)) {%>
										<a href="javascript:showRevewDialog()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">工单回访</a>
									<%} else {%>
										<a href="javascript:showSendDialog()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">回访流转</a>
									<%} %>
									<a href="javascript:overOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">工单终结</a>
							</div>
							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<!-- 列表 -->
							<table id="order_list_table" style="width:99%;height:92%;" ></table>
						</div>
			</div>
		</div>
	</div>
</body>
<!-- //////////////////////////////////以下为弹窗区域////////////////////////////////////////// -->
<!-- 回访弹窗 -->
<div id="review_order_dialog" class="easyui-window" title="工单回访"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#review_footer'"
	style="width:500px;height:150px;padding:10px;">
	<form id="review_order_form"
		action="<%=webpath%>/order/revewOrder.action" method="post">
		<input type="hidden" name="review_id" id="review_id" />
		<input type="hidden" name="phone" id="phone" />
		<input type="hidden" name="order" id="order" />
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<input name="review_degree" id="review_degree"
						class="easyui-combobox" style="width:300px;;"
						data-options="
							url: '<%=webpath%>/code/getCommonCode.action?codeKey=ORDER_DGREE',
							method: 'post',
							valueField:'CODE_ID',
							textField:'CODE_NAME',
							groupField:'group',
							label: '维护满意度:',
							labelPosition: 'left',
							required:true, 
							panelHeight:'auto'">
				</td>	
			</tr>
		</table>
	</form>
</div>
<div id="review_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:revewOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#review_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>

<!-- 流转弹窗 -->
<div id="send_order_dialog" class="easyui-window" title="工单回访"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#send_footer'"
	style="width:500px;height:150px;padding:10px;">
	<form id="send_order_form"
		action="<%=webpath%>/order/sendRevewOrder.action" method="post">
		<input type="hidden" name="send_review_id" id="send_review_id" />
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<input name="opr_id" id="opr_id"
							class="easyui-combobox" style="width:300px;"
							data-options="
								url: '<%=webpath%>/order/getSonUserByDeptCode.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'USER_NAME',
								groupField:'group',
								label: '转移客服人员:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto'">
				</td>	
			</tr>
		</table>
	</form>
</div>
<div id="send_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:sendOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#send_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>
</html>

