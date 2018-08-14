<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>人员管理</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/personnel/personnel.js"></script>

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
					<span class="name">人员管理</span>
				</div>
					<!-- 中央区域 -->
						<div id="work_panel" style="width:100%;height:92%;">
							<!--查询-->
							<div class="top_search">
								<span class="menu_before"></span> <a
									href="javascript:showAddPersonnel()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-add'">添加人员</a> <a
									href="javascript:beforeDeletePersonnel()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-cancel'">删除人员</a> <a
									href="javascript:beforeModifyPersonnel()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-edit'">编辑人员信息</a>
							</div>

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<table id="personnel_list_table" style="width:100%;height:98%;"></table>

						</div>
			</div>
		</div>
	</div>
</body>
<!-- 添加人员弹窗区域 -->
<div id="add_personnel_dialog" class="easyui-window" title="添加人员"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:300px;padding:10px;">
	<form id="add_personnel_form"
		action="<%=webpath%>/personnel/addPersonnel.action" method="post">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td><input class="easyui-textbox" name="personnel_account"
					style="width:300px;"
					data-options="label:'登录账号:',required:true,validType:'username'"
					id="menu_name"></td>
				<td><input class="easyui-textbox" type="password"
					name="personnel_pwd" style="width:300px;"
					data-options="label:'登录密码:',required:true,validType:'length(this.value,[6,8])'"
					id="menu_name"></td>
			</tr>
			<tr>
				<td><input class="easyui-textbox" name="personnel_name"
					style="width:300px;" data-options="label:'人员名称:',required:true"
					id="menu_name"></td>
				<td><input class="easyui-textbox" name="personnel_phone"
					style="width:300px;" data-options="label:'手机:',validType:'mobile'"
					id="menu_name"></td>
			</tr>
			<tr>
				<td><input class="easyui-textbox" name="personnel_tel"
					style="width:300px;" data-options="label:'电话:',validType:'phone'"
					id="menu_name"></td>
				<td><input class="easyui-textbox" name="personnel_address"
					style="width:300px;" data-options="label:'地址:'," id="menu_name">
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<input name="personnel_role" class="easyui-combobox"
							style="width:300px;;"
							data-options="
								url: '<%=webpath%>/role/roleList.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'ROLE_NAME',
								groupField:'group',
								label: '人员角色:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>
				<td>
					<div>
						<input name="personnel_dept" class="easyui-combobox"
							style="width:300px;;"
							data-options="
								url: '<%=webpath%>/dept/getAllDept.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'DEPT_NAME',
								groupField:'group',
								label: '所属部门:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="vos_account_div">
						<input class="easyui-textbox" name="vos_account"
						style="width:300px;" data-options="label:'vos账号:'"
						id="menu_name">
					</div>	
				</td>
				<td>
					<div id="vos_pwd_div">
						<input class="easyui-textbox" name="vos_pwd"
						style="width:300px;" data-options="label:'vos密码:'," id="menu_name">
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="add_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:addPersonnel()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#add_personnel_dialog').window('close')"
		style="width:80px">取消</a>
</div>

<!-- 删除前弹窗 -->
<div id="before_delete_dialog" class="easyui-dialog" title=" "
	style="width:300px;height:150px;padding:10px"
	data-options="closed:true,
			buttons: [{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$('#before_delete_dialog').window('close');
					deletePersonnel();
				}
			},{
				text:'取消',
				handler:function(){
					$('#before_delete_dialog').window('close');
				}
			}]
		">

</div>

<!-- 修改人员弹窗区域 -->
<div id="modify_personnel_dialog" class="easyui-window" title="添加人员"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:300px;padding:10px;">
	<form id="modify_personnel_form"
		action="<%=webpath%>/personnel/modifyPersonnel.action" method="post">
		<input class="easyui-textbox" type="hidden" name="personnel_maxaccept"
			style="width:300px;" id="m_personnel_maxaccept">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td><input class="easyui-textbox" name="personnel_account"
					style="width:300px;"
					data-options="label:'登录账号:',required:true,validType:'username'"
					id="m_personnel_account"></td>
				<td><input class="easyui-textbox" type="password"
					name="personnel_pwd" style="width:300px;"
					data-options="label:'登录密码:',required:true,validType:'length(this.value,[6,8])'"
					id="m_personnel_pwd"></td>
			</tr>
			<tr>
				<td><input class="easyui-textbox" name="personnel_name"
					style="width:300px;" data-options="label:'人员名称:'"
					id="m_personnel_name"></td>
				<td><input class="easyui-textbox" name="personnel_phone"
					style="width:300px;" data-options="label:'手机:',validType:'mobile'"
					id="m_personnel_phone"></td>
			</tr>
			<tr>
				<td><input class="easyui-textbox" name="personnel_tel"
					style="width:300px;" data-options="label:'电话:',validType:'phone'"
					id="m_personnel_tel"></td>
				<td><input class="easyui-textbox" name="personnel_address"
					style="width:300px;" data-options="label:'地址:',"
					id="m_personnel_address"></td>
			</tr>
			<tr>
				<td>
					<div>
						<input name="personnel_role" id="m_personnel_role"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/role/roleList.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'ROLE_NAME',
								groupField:'group',
								label: '人员角色:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>
				<td>
					<div>
						<input name="personnel_dept" id="m_personnel_dept" class="easyui-combobox"
							style="width:300px;;"
							data-options="
								url: '<%=webpath%>/dept/getAllDept.action',
								method: 'post',
								valueField:'MAXACCEPT',
								textField:'DEPT_NAME',
								groupField:'group',
								label: '所属部门:',
								labelPosition: 'left',
								required:true, 
								panelHeight:'auto',
								editable:false">
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="m_vos_account_div">
						<input class="easyui-textbox" name="vos_account"
						style="width:300px;" data-options="label:'vos账号:'"
						id="m_personnel_vos">
					</div>	
				</td>
				<td>
					<div id="m_vos_pwd_div">
						<input class="easyui-textbox" name="vos_pwd"
						style="width:300px;" data-options="label:'vos密码:'," id="m_personnel_vospwd">
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="add_footer"
	style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		href="javascript:void(0)" onclick="javascript:modifyPersonnel()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#modify_personnel_dialog').window('close')"
		style="width:80px">取消</a>
</div>
</html>
