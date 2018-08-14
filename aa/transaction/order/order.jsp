<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yl.common.user.pojo.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String webpath = request.getContextPath();
	User user = (User) request.getSession().getAttribute("userInfo");
	String roleLevel = user.getRoleLevel();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>工单派发</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/order/order.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	
	$(function() {
		//加载分页
		loadPage();
		
		//工单类型时间
		$("#o_order_types").combobox({
			onSelect: function(param){
				if(param.CODE_ID == "2"){
					$("#order_fix_type_div").show();
				}else{
					$("#order_fix_type_div").hide();
				}
			}
		});
		
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
					<span class="name">工单派发</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:92%;">
							<!--查询-->
							<div class="top_search" style="padding-left:20px;">
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
									<input id="creat_time_param" type="text" class="easyui-datebox" style="width:200px;" data-options="label:'工单创建时间:',editable:false">
									至<input id="end_time_param" type="text" class="easyui-datebox" data-options="editable:false" style="width:120px;">
									
									<a href="javascript:query()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-search'">查询</a>
									<a href="#" id="clearQuery" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">重置</a>
							</div>
							
							<%if("2".equals(roleLevel)) {%>
								<div class="top_search">
									<span class="menu_before"></span> 
									<!-- <a href="javascript:showAddOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-add'">添加工单</a>   -->
									<a href="javascript:beforeModifyOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">编辑工单信息</a>  
									<a href="javascript:showSendOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">派发工单</a>  
									<a href="javascript:beforeRemoveOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">取消工单</a>
									<a href="javascript:overOrder()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">工单终结</a>
								</div>
							<%} %>
							

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<!-- 修改标记 -->
							<table id="order_list_table" style="width:98%;height:92%;"></table>
						</div>
			</div>
		</div>
	</div>
</body>


<!-- 添加工单弹窗区域 -->
<div id="add_order_dialog" class="easyui-window" title="添加工单"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:300px;padding:10px;">
	<form id="add_order_form"
		action="<%=webpath%>/order/addOrder.action" method="post">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="order_types" id="order_types"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/order/getOrderTypeList.action',
								method: 'post',
								valueField:'CODE_ID',
								textField:'CODE_NAME',
								groupField:'group',
								label: '工单类型:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>	
				<td>
					<div>
						<input name="order_fix_type" id="order_fix_type"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/order/getFixTypeList.action',
								method: 'post',
								valueField:'CODE_ID',
								textField:'CODE_NAME',
								groupField:'group',
								label: '维护类型:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<div>
						<input name="order_status" id="order_status"
							class="easyui-combobox" style="width:300px;"
							data-options="
								url: '<%=webpath%>/order/getOrderStatus.action',
								method: 'post',
								valueField:'CODE_ID',
								textField:'CODE_NAME',
								groupField:'group',
								label: '工单状态:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>	
			</tr>
			<tr style="height:100px">
				<td colspan="2" ><input class="easyui-textbox" name="order_mark"
					style="width:600px;height:100px" data-options="label:'预约备注信息:',multiline:true"
					id="menu_name"></td>
			</tr>
		</table>
	</form>
</div>
<div id="add_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:addOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#add_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>



<!-- 取消前弹窗 -->
<div id="before_remove_dialog" class="easyui-dialog" title=" "
	style="width:300px;height:150px;padding:10px"
	data-options="closed:true,
			buttons: [{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$('#before_remove_dialog').window('close');
					removeOrder();
				}
			},{
				text:'取消',
				handler:function(){
					$('#before_remove_dialog').window('close');
				}
			}]
		">

</div>

<!-- 编辑工单信息弹窗区域 -->
<div id="modify_order_dialog" class="easyui-window" title="修改工单"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:300px;padding:10px;">
	<form id="modify_order_form"
		action="<%=webpath%>/order/modifyOrder.action" method="post">
		<input class="easyui-textbox" type="hidden" name="order_maxaccept"
			style="width:300px;" id="o_order_maxaccept">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="order_types" id="o_order_types"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/order/getOrderTypeList.action',
								method: 'post',
								valueField:'CODE_ID',
								textField:'CODE_NAME',
								groupField:'group',
								label: '工单类型:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>
				<td>
					<div id="order_fix_type_div">
						<input name="order_fix_type" id="o_order_fix_type" class="easyui-combobox"
							style="width:300px;"
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
					</div>
				</td>
			</tr>
			<tr>
				<td>
						<input class="easyui-textbox" name="order_phone"
							style="width:300px;" data-options="label:'联系电话:',validType:'mobile'"
							id="o_order_phone">
				</td>
			</tr>
			<tr style="height:100px">
				<td colspan="2" ><input class="easyui-textbox" name="order_mark"
					style="width:600px;height:100px" data-options="label:'预约备注信息:',multiline:true"
					id="o_order_mark"></td>
			</tr>
		</table>
	</form>
</div>
<div id="add_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:modifyOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#modify_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>

<!-- 派发工单弹窗区域 -->
<div id="send_order_dialog" class="easyui-window" title="派发工单"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:350px;height:150px;padding:10px;">
	<form id="modify_order_form"
		action="<%=webpath%>/order/modifyOrder.action" method="post">
		<input class="easyui-textbox" type="hidden" name="order_maxaccept"
			style="width:300px;" id="o_order_maxaccept">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="fix_dept_id" id="fix_dept_id"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/order/getFixdeptidList.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'DEPT_NAME',
								groupField:'group',
								label: '派发部门:',
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
<div id="add_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:sendOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#send_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>
</html>
