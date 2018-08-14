<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>微信投诉管理</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/weixin/weixin.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		//加载分页
		loadPage();
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
</head>

<body>
	<div id="right_ctn">
		<div class="right_m">
			<div class="hy_list">
				<div class="box_t">
					<span class="name">微信投诉管理</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:92%;">
							<div id="weixin_content_panel" style="height:100%;">
								<!--查询-->
								<div class="top_search">
									<div>
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
											<input id="begin_time_param" type="text" class="easyui-datebox"
											style="width:200px;" data-options="label:'工单创建时间:',editable:false">
											至
											<input id="end_time_param" type="text" class="easyui-datebox" data-options="editable:false"
											style="width:120px;">
											
											<a href="javascript:loadPage()"
										class="easyui-linkbutton menu_botton"
										data-options="iconCls:'icon-search'">查询</a>
											<a href="#" id="clearQuery"
										class="easyui-linkbutton menu_botton"
										data-options="iconCls:'icon-cancel'">重置</a>
									</div>
								</div>
							
								<div class="top_search">
									<span class="menu_before"></span> 
									<a href="javascript:showchangeOrder();" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-reload'">工单转移</a>  
									<a href="javascript:showSendOrder();" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">工单派发</a>  
									<a href="javascript:showOverOrder();" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">工单处理</a>
								</div>
	
								<!-- 展示区域 -->
								<div class="space_hx">&nbsp;</div>
								<!-- 修改标记 -->
								<table id="weixin_list_table" style="width:98%;height:92%;">							
								</table>
							</div>
							
							

							<!-- ------------------用户详情以及四邻展示模块-begin------------------------ -->
							
							<div id="weixin_cust_info" style="display:none;">
								<!-- 弹窗头部 -->
								<div style="padding-left:20px;background-color: #cffdbe;height:30px;">
									<div style="float: left;margin-right:20px;margin-top:6px;"><a href="javascript:showCustInfoDiv('','1');">返回</a></div>
								</div>
								<!-- 用户信息、维护信息部分 -->
								<div style="width:100%;">
									<div  style="float:left;width:100%;">
										<div style="float:left;width:100%;">
											<div id="wx_cust_info" class="easyui-panel" title="用户信息" style="float:left;width:1678px;height:160px;padding:10px;">
												<!-- <div style="margin-bottom:10px">
													<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
												</div>
												<div style="margin-bottom:10px">
													<span style="font-weight: bold;">常用电话：</span><input class="easyui-textbox" readonly="readonly" name="normal_phone" style="width:150px;" data-options="required:true,validType:'mobile'">
													<a href="javascript:copy_phone();" style="border-bottom: solid 1px blue;"><span style="color:blue;">将来电号码设为常用</span></a> 
													<a style="border-bottom: solid 1px blue;" href="javascript:change_phone();"><span style="color:blue;">手动修改常用号码</span></a>
												</div>
												<div style="margin-bottom:10px">
													<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
												</div>
												<div style="margin-bottom:10px">
													<span style="font-weight: bold;">缴费信息：</span><span></span>
												</div> -->
											</div>
										</div>
									</div>
									<div style="float:left;width:25%;">
										<div id="wx_up_cust_info" class="easyui-panel" title="楼上信息" style="float:left;width:419px;height:170px;padding:10px;">
											<!-- <div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">常用电话：</span><span></span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">缴费信息：</span><span></span>
											</div> -->
										</div>
									</div>
									<div style="float:left;width:25%;">
										<div id="wx_low_cust_info" class="easyui-panel" title="楼下信息" style="float:left;width:419px;height:170px;padding:10px;">
											<!-- <div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">常用电话：</span><span></span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">缴费信息：</span><span></span>
											</div> -->
										</div>
									</div>
									<div style="float:left;width:25%;">
										<div id="wx_left_cust_info" class="easyui-panel" title="左邻信息" style="float:left;width:419px;height:170px;padding:10px;">
											<!-- <div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">常用电话：</span><span></span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">缴费信息：</span><span></span>
											</div> -->
										</div>
									</div>
									<div style="float:right;width:25%;">
										<div id="wx_right_cust_info" class="easyui-panel" title="右舍信息" style="float:right;width:419px;height:170px;padding:10px;">
											<!-- <div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">常用电话：</span><span></span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
											</div>
											<div style="margin-bottom:10px">
												<span style="font-weight: bold;">缴费信息：</span><span></span>
											</div> -->
										</div>
									</div>
								</div>
								<div style="float:left;width:100%;">
									<div style="float:left;width:50%;">
										<div class="easyui-panel" style="width:838px;" title="用户信息列表">
											<table id="wx_cust_info_list_table" class="easyui-datagrid" style="width:100%;height:146px"
											data-options="singleSelect:true,collapsible:true">
												<thead>
													<tr>
														<th data-options="field:'ck',width:5,halign:'center',checkbox:true">Item ID</th>
														<th data-options="field:'MAXACCEPT',width:50,halign:'center',hidden:true">序列</th>
														<th data-options="field:'CUST_NAME',width:100,align:'right',halign:'center'">客户名称</th>
														<th data-options="field:'PHONE',width:150,align:'right',halign:'center'">常用电话</th>
														<th data-options="field:'ADRESS',width:200,halign:'center'">地址</th>
														<th data-options="field:'CREATE_TIME',width:150,align:'center',halign:'center'">客户创建时间</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
									<div style="float:right;width:50%;">
										<div class="easyui-panel" style="width:838px;" title="历史工单列表">
											<table id="wx_order_info_list_table" class="easyui-datagrid" style="width:100%;height:146px"
											data-options="singleSelect:true,collapsible:true">
												<thead>
													<tr>
														<th data-options="field:'ck',width:5,halign:'center',checkbox:true">Item ID</th>
														<th data-options="field:'MAXACCEPT',width:50,halign:'center',hidden:true">序列</th>
														<th data-options="field:'CUST_NAME',width:100,align:'right',halign:'center'">客户名称</th>
														<th data-options="field:'PHONE',width:150,align:'right',halign:'center'">常用电话</th>
														<th data-options="field:'ADRESS',width:200,halign:'center'">地址</th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
							</div>
							
							<!-- 手动查询界面 -->
							<div class="easyui-window" title="手动查询" id="add_self_window" style="height:400px;width:600px;" data-options="modal:true,collapsible:false,minimizable:false,inline:false,cls:'c2',closed:true">
									<div class="easyui-panel" style="width:100%;height:165px;padding:10px 20px;">
										<div style="margin-bottom:20px;">
											<input class="easyui-textbox" id="add_search_adress" data-options="buttonText:'查询',buttonAlign:'right',buttonIcon:'icon-search',prompt:'输入地址...'" style="width:80%;height:32px;">
											<a href="javascript:add_self();" style="margin-left:20px;">手动输入</a>
										</div>
									<form action="<%=webpath%>/PhoneInterface/addCustInfo.action" id="add_self_form" method="post">
										<div style="margin-bottom:20px;">
											<input type="hidden" id="cust_id" name="cust_id"/>
											<input type="hidden" id="into_phone" name="into_phone"/>
											<div style="margin-bottom:20px;">
												客户姓名：<input class="easyui-textbox" readonly="readonly" id="add_cust_name" name="add_cust_name" style="width:150px;" data-options="required:true">
												常用电话：<input class="easyui-textbox" readonly="readonly" id="add_cust_phone" name="add_cust_phone" style="width:150px;" data-options="required:true,validType:'mobile'">
											</div>
											<div style="margin-bottom:5px;">
												客户地址：<input class="easyui-textbox" readonly="readonly" id="add_cust_adress" name="add_cust_adress" style="width:365px;" data-options="required:true">
												<a href="javascript:save_cust_info();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="margin-left:30px;">保存记录</a>
											</div>
										</div>
									</form>
									</div>
								<div id="fix_info" class="easyui-panel" title="用户列表" style="float:left;width:100%;height:160px;padding:10px;">
									<table id="add_cust_list"></table>
								</div>
							</div>
							
							<!-- ------------------用户详情以及四邻展示模块-end------------------------ -->
						</div>
			</div>
		</div>
	</div>
</body>


<!-- 转移工单弹窗区域 -->
<div id="change_order_dialog" class="easyui-window" title="添加工单"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#change_footer'"
	style="width:350px;height:150px;padding:10px;">
	<form id="change_order_form"
		action="<%=webpath%>/order/changeWeixinOrder.action" method="post">
		<input type="hidden" name="order_id" id="order_id" />
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="opr_id" id="opr_id"
							class="easyui-combobox" style="width:300px;"
							data-options="
								url: '<%=webpath%>/order/getUserByDeptType.action?deptType=61',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'USER_NAME',
								groupField:'group',
								label: '转移客服人员:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto'">
					</div>
				</td>	
			</tr>
		</table>
	</form>
</div>
<div id="change_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:changeOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#change_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>

<!-- 派发工单弹窗区域 -->
<div id="send_order_dialog" class="easyui-window" title="派发工单"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#send_footer'"
	style="width:350px;height:150px;padding:10px;">
	<form id="send_order_form"
		action="<%=webpath%>/order/sendToFixWeixin.action" method="post">
		<input type="hidden" name="ids"
			style="width:300px;" id="order_maxaccept">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="fix_dept_id" id="fix_dept_id"
							class="easyui-combobox" style="width:300px;"
							data-options="
								url: '<%=webpath%>/order/getFixdeptidList.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'DEPT_NAME',
								groupField:'group',
								label: '派发部门:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto'">
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

<!-- 工单处理弹窗区域 -->
<div id="over_order_dialog" class="easyui-window" title="工单处理"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#over_footer'"
	style="width:680px;height:210px;padding:10px;">
	<form id="over_order_form"
		action="<%=webpath%>/fixOrder/commitOrder.action" method="post">
		<input type="hidden" name="commit_ids"
			style="width:300px;" id="commit_ids">
		<span style="color:red;font-size:12px;">注：保存后工单将完结！</span>
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<div>
						<input name="fix_mark" id="fix_mark" style="width:600px;height:100px" data-options="label:'处理备注:',multiline:true" class="easyui-textbox">
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="over_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:overOrder()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#over_order_dialog').window('close')"
		style="width:80px">取消</a>
</div>

</html>
