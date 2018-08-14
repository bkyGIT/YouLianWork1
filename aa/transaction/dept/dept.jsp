<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
<head>

<title>部门管理</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/dept/dept.js"></script>

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
					<span class="name">部门管理</span>
				</div>

				<div id="layout_div" class="easyui-layout"
					style="width:100%;height:92%;">

					<!-- 中央区域 -->
					<div data-options="region:'center'"
						style="width:70%;padding:10px;background-color: rgba(232, 232, 232, 1);">
						<div id="work_panel" style="width:100%;height:92%;">
							<!--查询-->
							<div class="top_search">
								<span class="menu_before"></span> <a
									href="javascript:showAddDept()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-add'">添加部门</a> <a
									href="javascript:beforeDeleteDept()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-cancel'">删除部门</a> <a
									href="javascript:beforeModifyDept()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-edit'">编辑部门信息</a>
							</div>

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<table id="dept_list_table" style="width:100%;height:98%;"></table>

						</div>

					</div>

				</div>

			</div>
		</div>
	</div>
</body>
<!-- 添加人员弹窗区域 -->
<div id="add_dept_dialog" class="easyui-window" title="添加人员"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:200px;padding:10px;">
	<form id="add_dept_form"
		action="<%=webpath%>/dept/addDept.action" method="post">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td><input class="easyui-textbox" name="dept_name"
					style="width:300px;"
					data-options="label:'部门名称:',required:true"></td>
				<td><input class="easyui-textbox" name="dept_des" style="width:300px;"
					data-options="label:'部门描述:'"></td>
			</tr>
			<tr>
				<td>
					<div>
						<input name="dept_type" id="dept_type"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/code/DEPT_TYPE.action',
								method: 'post',
								valueField:'CODE_ID',
								textField:'CODE_NAME',
								groupField:'group',
								label: '部门类型',
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
		href="javascript:void(0)" onclick="javascript:addDept()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#add_dept_dialog').window('close')"
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
					deleteDept();
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
<div id="modify_dept_dialog" class="easyui-window" title="修改部门"
	data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
	style="width:700px;height:200px;padding:10px;">
	<form id="modify_dept_form"
		action="<%=webpath%>/dept/modifyDept.action" method="post">
		<input class="easyui-textbox" type="hidden" name="dept_maxaccept"
			style="width:300px;" id="m_dept_maxaccept">
		<table cellpadding="0" cellspacing="0" class="list_hy"
			style="width: 99%;margin-left:0px;">
			<tr>
				<td><input class="easyui-textbox" name="dept_name"
					style="width:300px;"
					data-options="label:'部门名称:',required:true"
					id="m_dept_name"></td>
				<td><input class="easyui-textbox" 
					name="dept_des" style="width:300px;"
					data-options="label:'部门描述:'"
					id="m_dept_des"></td>
			</tr>
			<tr>
				<td>
					<div>
						<input name="dept_type" id="m_dept_type"
							class="easyui-combobox" style="width:300px;;"
							data-options="
								url: '<%=webpath%>/code/DEPT_TYPE.action',
								method: 'post',
								valueField:'CODE_ID',
								textField:'CODE_NAME',
								groupField:'group',
								label: '部门类型',
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
		href="javascript:void(0)" onclick="javascript:modifyDept()"
		style="width:80px">保存</a> <a class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
		onclick="javascript:$('#modify_dept_dialog').window('close')"
		style="width:80px">取消</a>
</div>
</html>
