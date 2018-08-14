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

<title>呼转管理</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/trunPhone/trunPhone.js"></script>

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
					<span class="name">呼转管理</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:97%;">
							<!--查询-->
							<div class="top_search">
								<div style="float: left;margin-left:10px;">
									<span>联系电话:</span>
									<input class="easyui-textbox" name="trun_phone" id="trun_phone" style="width:120px;"">
								</div>
								<div style="float: left;margin-left:10px;">
									<span>部门:</span>
									<input name="trun_dept_id" id="trun_dept_id" class="easyui-combobox" style="width:120px;;"
										data-options="
											url: '<%=webpath%>/dept/getAllDept.action',
											method: 'post',
											valueField:'MAXACCEPT',
											textField:'DEPT_NAME',
											groupField:'group',
											labelPosition: 'left', 
											panelHeight:'auto',
											editable:false">
								</div>
								<div style="float: left;margin-left:10px;">
									<a href="javascript:query()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-search'"  style="width:80px">查询</a>
									<a href="#" id="clearQuery" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px">重置</a>
								</div>
							</div>

							<div class="top_menu">
								<span class="menu_before"></span>  
								<a href="javascript:addConnInfoDialog()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-add'">添加</a>
								<a href="javascript:modifyConnInfoDialog()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-edit'">修改</a>
								<a href="javascript:delConnInfo()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">删除</a>
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
<!-- 添加联系方式弹窗区域 -->
<div id="add_conn_dialog" class="easyui-window" title="添加联系信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:200px;padding:10px;">
	<form id="add_conn_form"
		action="<%=webpath%>/trun/addConnInfo.action" method="post">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<input name="add_dept_id" id="add_dept_id" class="easyui-combobox" style="width:200px;"
						data-options="
							url: '<%=webpath%>/dept/getAllDept.action',
							method: 'post',
							valueField:'MAXACCEPT',
							textField:'DEPT_NAME',
							label: '所属部门:',
							groupField:'group',
							labelPosition: 'left', 
							panelHeight:'auto',
							editable:false,
							required:true">
				</td>	
				<td>
					<input class="easyui-textbox" name="add_conn_phone" id="add_conn_phone" style="width:200px;" 
					data-options="label: '联系电话:', required:true,validType:'mobile'">
				</td>
			</tr>
			
			<tr>
				<td>
					<input class="easyui-textbox" name="add_conn_name" id="add_conn_name" style="width:200px;" 
					data-options="label: '联系人:', required:true">
				</td>	
				<td>
				</td>	
			</tr>
		</table>
	</form>
</div>
<div id="add_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:addConnInfo()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#add_conn_dialog').window('close')"
		style="width:80px">取消</a>
</div>


<!-- 修改联系方式弹窗区域 -->
<div id="modify_conn_dialog" class="easyui-window" title="修改联系信息"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#modify_footer'"
	style="width:700px;height:200px;padding:10px;">
	<form id="modify_conn_form"
		action="<%=webpath%>/trun/modifyConnInfo.action" method="post">
		<input type="hidden" name="modify_id" id="modify_id" />
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td>
					<input name="modify_dept_id" id="modify_dept_id" class="easyui-combobox" style="width:200px;"
						data-options="
							url: '<%=webpath%>/dept/getAllDept.action',
							method: 'post',
							valueField:'MAXACCEPT',
							textField:'DEPT_NAME',
							label: '所属部门:',
							groupField:'group',
							labelPosition: 'left', 
							panelHeight:'auto',
							editable:false,
							required:true">
				</td>	
				<td>
					<input class="easyui-textbox" name="modify_conn_phone" id="modify_conn_phone" style="width:200px;" 
					data-options="label: '联系电话:', required:true,validType:'mobile'">
				</td>
			</tr>
			
			<tr>
				<td>
					<input class="easyui-textbox" name="modify_conn_name" id="modify_conn_name" style="width:200px;" 
					data-options="label: '联系人:', required:true">
				</td>	
				<td>
				</td>	
			</tr>
		</table>
	</form>
</div>
<div id="modify_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:modifyConnInfo()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#modify_conn_dialog').window('close')"
		style="width:80px">取消</a>
</div>


</html>

