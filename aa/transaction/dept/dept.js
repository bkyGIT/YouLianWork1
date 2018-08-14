function loadPage() {
	$("#dept_list_table").datagrid({
		border : false,
		locale : "zh_CN",
		striped : true,
		collapsible : false,
		url : webpath + "/dept/deptList.action",
		columns : [ [ {
			field : 'ck',
			width : '5%',
			algin : 'center',
			checkbox : true
		}, {
			field : 'MAXACCEPT',
			title : '序列',
			width : '10%',
			align : 'center',
			hidden : true
		}, {
			field : 'DEPT_NAME',
			title : '部门名称',
			width : '25%',
			align : 'center'
		}, {
			field : 'DEPT_DES',
			title : '部门描述',
			width : '25%',
			align : 'center'
		}, {
			field : 'DEPT_TYPE',
			title : '部门类型',
			width : '25%',
			align : 'center',
			formatter: function(value,row,index){
					return getCodeName('DEPT_TYPE',value);
			}
		} ] ],
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false,
		singleSelect : true,

	});
}

function showAddDept() {
	$("#add_dept_form").form("clear");
	$("#add_dept_dialog").window('open');
}

// 添加部门
function addDept() {
	$("#add_dept_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#add_dept_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function() {
				$("#dept_list_table").datagrid("reload");
			});
		}

	});
}

function beforeDeleteDept() {
	/*var checkData = $("#dept_list_table").datagrid().datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要删除的部门！");
		return;
	}
	
	$("#before_delete_dialog").text("确认删除部门？");
	$("#before_delete_dialog").window('open');*/
	
	var checkData = $("#dept_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(" ", "请先选择要删除的部门！", "error");
		return;
	}
	
	$.messager.confirm(" ", "确认要取消选中的部门么？", function(r){
		if (r){
			var ids = "";
			for(var ix=0; ix<checkData.length; ix++){
				ids = checkData[ix].MAXACCEPT + "," + ids;
			}
			
			$.ajax({
				url : webpath + "/dept/delDept.action",
				type : "post",
				dataType : "json",
				data : {
					ids : ids
				},
				success : function(data) {
					$.messager.alert(' ', data[0].resultMsg, 'info', function(){
						$("#dept_list_table").datagrid("reload");
					});
				}
			});
		}
	});
}

// 删除部门
/*function deleteDept() {
	var checkData = $("#dept_list_table").datagrid().datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要删除的部门！");
		return;
	}
	var ids = "";
	for ( var ix = 0; ix < checkData.length; ix++) {
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$.ajax({
		url : webpath + "/dept/delDept.action",
		type : "post",
		dataType : "json",
		data : {
			"ids" : ids
		},
		success : function(data) {
			$.messager.alert(' ', data[0].resultMsg, 'info', function() {
				$("#dept_list_table").datagrid("reload");
			});
		}
	});
}*/

function beforeModifyDept() {
	var checkData = $("#dept_list_table").datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要修改的部门！");
		return;
	}
	$("#modify_dept_dialog").window('open');
	$("#m_dept_maxaccept").textbox("setValue", checkData[0].MAXACCEPT);
	$("#m_dept_name").textbox("setValue", checkData[0].DEPT_NAME);
	$("#m_dept_des").textbox("setValue", checkData[0].DEPT_DES);
	$("#m_dept_type").combobox("setValue", checkData[0].DEPT_TYPE);
}

// 修改人员
function modifyDept() {
	$("#modify_dept_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#modify_dept_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function() {
				$("#dept_list_table").datagrid("reload");
			});
		}

	});
}
