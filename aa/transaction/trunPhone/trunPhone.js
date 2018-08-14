function loadPage() {
	$("#order_list_table").datagrid({
		queryParams : {
			trun_phone: $("#trun_phone").textbox('getValue'),
			dept_id: $("#trun_dept_id").combobox('getValue')
		},
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		url : webpath + "/trun/loadTrunPhoneList.action",
		columns : [ [ {
			field : 'ck',
			width : '5%',
			algin : 'center',
			checkbox : true
		}, {
			field : 'MAXACCEPT',
			title : '序列',
			align : 'center',
			width : '6%',
			hidden:true
		}, {
			field : 'CONN_PHONE',
			title : '联系电话',
			width : '25%',
			align : 'center'
		}, {
			field : 'CONN_NAME',
			title : '联系人',
			width : '25%',
			align : 'center'
		}, {
			field : 'DEPT_CODE',
			title : '所属部门',
			width : '25%',
			align : 'center',
			hidden:true
		}, {
			field : 'DEPT_NAME',
			title : '所属部门',
			width : '25%',
			align : 'center'
		}, {
			field : 'CREATE_TIME',
			title : '创建时间',
			width : '25%',
			align : 'center'
		}
		] ],
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false,
		pageSize: 20
	});
}

// 添加联系人弹窗
function addConnInfoDialog() {
	$("#add_conn_dialog").window("open");
	
	//清空组件
	$("#add_dept_id").combobox("setValue", "");
	$("#add_conn_phone").textbox("setValue", "");
	$("#add_conn_name").textbox("setValue", "");
}

//联系人添加
function addConnInfo(){
	$("#add_conn_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#add_conn_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}
//工单修改弹窗
function modifyConnInfoDialog() {
	var checkData = $("#order_list_table").datagrid("getChecked");
	if (checkData.length != 1) {
		$.messager.alert(' ', "请先选择一条需要修改的数据！");
		return;
	}
	var id = checkData[0].MAXACCEPT;
	$("#modify_id").val(id);
	$("#modify_conn_dialog").window("open");
	
	//赋值
	$("#modify_dept_id").combobox("setValue", checkData[0].DEPT_CODE);
	$("#modify_conn_phone").textbox("setValue", checkData[0].CONN_PHONE);
	$("#modify_conn_name").textbox("setValue", checkData[0].CONN_NAME);
}

//工单流转
function modifyConnInfo(){
	$("#modify_conn_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#modify_conn_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}

//删除联系
function delConnInfo(){
	var checkData = $("#order_list_table").datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要删除的数据！");
		return;
	}
	$.messager.confirm(" ", "确认要删除选中的数据么？", function(r){
		if (r){
			var ids = "";
			for(var ix=0; ix<checkData.length; ix++){
				ids = checkData[ix].MAXACCEPT + "," + ids;
			}
			$.ajax({
				url : webpath + "/trun/delConnInfo.action",
				type : "post",
				dataType : "json",
				data : {
					ids : ids
				},
				success : function(data) {
					$.messager.alert(' ', data.resultMsg, 'info', function(){
						$("#order_list_table").datagrid("reload");
					});
				}
			});
		}
	});
}
