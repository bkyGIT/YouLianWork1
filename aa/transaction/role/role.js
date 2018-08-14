function saveRoleMenu(){
	var treeNode = $("#menuList").tree('getChecked');
	var ids = "";
	for(var ix=0; ix<treeNode.length; ix++){
		ids = treeNode[ix].id + "," + ids;
	}
	$.ajax({
		url : webpath + "/role/saveRoleMenus.action",
		type : "post",
		dataType : "json",
		data : {
			"ids" : ids, "roleID": roleID
		},
		success : function(data) {
			$.messager.alert(' ', data[0].resultMsg);
		}
	});
}

function showAddRole(){
	$("#add_role_dialog").window('open');
}
//添加角色
function addRole(){
	$("#add_role_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#add_role_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#role_list_table").datagrid("reload");
			});
		}

	});
}

function beforeDeleteMenu(){
	$("#before_delete_dialog").text("确认删除角色？");
	$("#before_delete_dialog").dialog('open');
}

//删除角色
function deleteRole(){
	var checkData = $("#role_list_table").datagrid().datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要删除的角色！");
		return;
	}
	var ids = "";
	for(var ix=0; ix<checkData.length; ix++){
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$.ajax({
		url : webpath + "/role/delRoleMenus.action",
		type : "post",
		dataType : "json",
		data : {
			"ids" : ids
		},
		success : function(data) {
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#role_list_table").datagrid("reload");
			});
		}
	});
}