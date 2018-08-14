<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import=" com.yl.common.user.pojo.User" %>
<%
	String webpath = request.getContextPath();
	User user = (User) request.getSession().getAttribute("userInfo");
	request.setAttribute("roleLevel", user.getRoleLevel());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>维护工单查询</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/fixOrderQuery/fixOrderQuery.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		//加载分页
		loadPage();
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
					<span class="name">工单查询</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:97%;">
							<!--查询-->
							<div class="top_search">
								<input name="order_type_param" id="order_type_param"
									class="easyui-combobox" style="width:200px;"
									data-options="
										url: '<%=webpath%>/order/getOrderTypeList.action',
										method: 'post',
										valueField:'CODE_ID',
										textField:'CODE_NAME',
										groupField:'group',
										label: '工单类型:',
										labelPosition: 'left',
										panelHeight:'auto',editable:false">
									维护类型:
									<input name="fix_type_param" id="fix_type_param"
										class="easyui-combobox" style="width:120px;"
										data-options="
											url: '<%=webpath%>/order/getFixTypeList.action',
											method: 'post',
											valueField:'CODE_ID',
											textField:'CODE_NAME',
											groupField:'group',
											labelPosition: 'left',
											panelHeight:'auto',
											editable:false">
										工单状态
										<input name="order_status" id="order_status"
										class="easyui-combobox" style="width:120px;"
										data-options="
											url: '<%=webpath%>/order/getCommonCode.action?codeKey=ORDER_STATUS&codeIDS=24,25,26,29,80',
											method: 'post',
											valueField:'CODE_ID',
											textField:'CODE_NAME',
											groupField:'group',
											labelPosition: 'left',
											panelHeight:'auto',
											editable:false">
									<%-- 	维修小区:
										<input class="easyui-combobox" id="fix_xq" style="width:150px;" data-options="
										url: '<%=webpath%>/xq/getAllXqInfo.action',
										method: 'post',
										valueField: 'MAXACCEPT',
										textField: 'XQ_NAME',
										panelHeight: 'auto',
										labelPosition: 'left'
										"> --%>
										工单创建时间:
										<input id="begin_time_param" type="text" class="easyui-datebox" data-options="editable:false"
										style="width:120px;">
										至
										<input id="end_time_param" type="text" class="easyui-datebox" data-options="editable:false"
										style="width:120px;">
										
										<a href="javascript:query()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-search'"  style="width:80px">查询</a>
										<a href="#" id="clearQuery" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px">重置</a>
							</div>

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<!-- 列表 -->
							<table id="order_list_table" style="width:99%;height:92%;"></table>
						</div>
			</div>
		</div>
	</div>
</body>
<!-- //////////////////////////////////以下为弹窗区域////////////////////////////////////////// -->
<!-- 派发弹窗 -->
<div id="send_order_dialog" class="easyui-window" title="工单下发"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#send_footer'"
	style="width:500px;height:150px;padding:10px;">
	<form id="send_order_form"
		action="<%=webpath%>/fixOrder/sendOrder.action" method="post">
		<input type="hidden" name="send_ids" id="send_ids" />
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="fix_person" id="order_types"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/fixOrder/getFixPerson.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'USER_NAME',
								groupField:'group',
								label: '维护人员:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
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

<!-- 提交弹窗 -->
<div id="commit_order_dialog" class="easyui-window" title="工单提交"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#commit_footer'"
	style="width:680px;height:210px;padding:10px;">
	<form id="commit_order_form"
		action="<%=webpath%>/fixOrder/commitOrder.action" method="post">
		<input type="hidden" name="commit_ids" id="commit_ids" />
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<input class="easyui-textbox" name="fix_mark" style="width:600px;height:100px" data-options="label:'维护备注信息:',multiline:true" id="fix_mark">
				</td>	
			</tr>
		</table>
	</form>
</div>
<div id="commit_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:commitOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#commit_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>
</html>

