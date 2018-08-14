<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>角色管理</title>
<script type="text/javascript"
	src="<%=webpath%>/transaction/role/role.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	var roleID = "";
	$(function() {
		$("#layout_div").layout('collapse', 'west');
		var role_pager = $("#role_list_table").datagrid().datagrid("getPager");
	});

	function rowformater(value, rowData, rowIndex) {
		return '<a href="#" style="color:red;text-decoration:underline" onclick="changeMenu('
				+ rowData['MAXACCEPT'] + ')">分配菜单</a>';
	}
	function changeMenu(maxaccept) {
		showRoleMenu(maxaccept);
	}
	//加载菜单
	function showRoleMenu(maxaccept) {
		roleID = maxaccept;
		//显示树区域
		$("#layout_div").layout('expand', 'west');
		//加载树
		$("#menuList").tree({
			url : webpath + "/role/roleMenu.action?maxaccept=" + maxaccept,
			loadFilter : function(data) {
				return data;
			}
		});

	}
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
					<span class="name">角色管理</span>
				</div>

				<div id="layout_div" class="easyui-layout"
					style="width:100%;;height:92%;">
					<!-- 菜单列表 -->
					<div id="tee_panel" data-options="region:'west'" title="菜单列表"
						style="width:15%;padding:10px;">
						<div class="easyui-panel" style="padding:5px">
							<ul class="easyui-tree" id="menuList"
								data-options="animate:true,lines:true,checkbox:true"></ul>
						</div>
						<a href="javascript:saveRoleMenu();" style="margin-top:10px;"
							class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
					</div>

					<!-- 中央区域 -->
					<div data-options="region:'center'"
						style="width:70%;padding:10px;background-color: rgba(232, 232, 232, 1);">
						<div id="work_panel"  style="width:100%;height:92%;">
							<!--查询-->
							<div class="top_search">
								<span class="menu_before"></span> <a
									href="javascript:showAddRole()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-add'" id="add_child_nemu">添加角色</a>
								<a href="javascript:beforeDeleteMenu()"
									class="easyui-linkbutton menu_botton"
									data-options="iconCls:'icon-cancel'">删除角色</a>
							</div>

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<table id="role_list_table" style="width:100%;height:98%;"
								data-options="rownumbers:true,singleSelect:true,pagination:true,url:'<%=webpath%>/role/roleList.action',method:'post'">
								<thead>
									<tr>
										<th data-options="field:'ck',checkbox:true"></th>
										<th data-options="field:'MAXACCEPT',width:80,hidden:true">序列</th>
										<th data-options="field:'ROLE_NAME',width:80">角色名称</th>
										<th data-options="field:'ROLE_MENUS',width:100"
											formatter="rowformater">操作</th>
									</tr>
								</thead>
							</table>

						</div>

					</div>

				</div>

			</div>
		</div>
	</div>
</body>
<!-- 添加角色弹窗区域 -->
<div id="add_role_dialog" class="easyui-window" title="添加角色" data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'" style="width:850px;height:180px;padding:10px;">
	<form id="add_role_form" action="<%=webpath%>/role/addRole.action" method="post">
		<table cellpadding="0" cellspacing="0" class="list_hy" style="width: 80%;margin-left:0px;">
			<tr>
				<td>
					<input class="easyui-textbox" name="role_name" style="width:300px" data-options="label:'角色名称:',required:true" id="menu_name">
				</td>
				<td>
					<input class="easyui-textbox" name="role_des" style="width:300px" data-options="label:'描述:'," id="menu_name">
				</td>
			</tr>
			<tr>
				<td>
					<select class="easyui-combobox" data-options="required:true, panelHeight:'auto'" name="role_level" label="角色类型:" labelPosition="left" style="width:95%;" id="role_level">
						<c:set var="userLevels" value="<%= com.yl.common.enums.UserLevel.values() %>"/>
						<c:forEach var="userLevel" items="${userLevels}">
							<option value="${userLevel.code}" >${userLevel.type}</option>
						</c:forEach>
					</select>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="add_footer" style="text-align:left;float: right;padding: 5px 0 0;border: none;">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:addRole()" style="width:80px">保存</a> 
	<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#add_role_dialog').window('close')" style="width:80px">取消</a>
</div>

<!-- 删除前弹窗 -->
<div id="before_delete_dialog" class="easyui-dialog" title=" " style="width:300px;height:150px;padding:10px"
		data-options="closed:true,
			buttons: [{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$('#before_delete_dialog').window('close');
					deleteRole();
				}
			},{
				text:'取消',
				handler:function(){
					$('#before_delete_dialog').window('close');
				}
			}]
		">
	
</div>
</html>
