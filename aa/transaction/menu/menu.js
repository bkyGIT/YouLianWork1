//保存菜单信息
function saveMenu(saveFlag) {
	$("#menu_form").attr("action",
			webpath + "/menu/saveMenu.action?saveFlag=" + saveFlag);

	$('#menu_form').form('submit', {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg);
			if (data[0].resultCode == "0000") {
				showMenuInfo(data[0].menuID);
			}
		}

	});
}

// 添加菜单
function showAddMenu(menuLevel) {
	$("#add_flag").val(menuLevel);
	$("#add_dialog").window('open');

}

// 添加菜单保存
function addMenu() {
	$("#add_menu_bak").val($("#menu_bak").val());
	$("#add_menu_form").attr("action",
			webpath + "/menu/addMenu.action");
	$('#add_menu_form').form('submit', {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			data = eval("(" + data + ")");
			$("#add_dialog").window('close');
			$("#result_dialog").dialog('open');
			$("#result_dialog").html(data[0].resultMsg);
		}

	});
}
//删除菜单前
function beforeDeleteMenu(){
	var menu_level = $("#menu_level").val();
	if(menu_level == "0"){
		$("#before_delete_dialog").text("删除父菜单会连同子菜单一起删除，是否要继续操作？");
	}else{
		$("#before_delete_dialog").text("确认删除菜单？");
	}
	$("#before_delete_dialog").dialog('open');
}
//删除菜单
function deleteMenu(){
	var menu_level = $("#menu_level").val();
	var menuID = $("#menu_bak").val();
	$.ajax({
		url : webpath + "/menu/deleteMenu.action",
		type : "post",
		dataType : "json",
		data : {
			"menuID" : menuID, "menuLevel" : menu_level
		},
		success : function(data) {
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				window.location.reload();
			});
		}
	});
}