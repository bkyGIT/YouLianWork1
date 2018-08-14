function loadPage(konwID) {
	$("#work_panel").show();
	$("#knowledge_list_table").datagrid({
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		url : webpath + "/knowledge/showKnowledgeInfo.action?konwID="+konwID,
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
		},  {
			field : 'CODE_KEY',
			title : '编码值',
			width : '10%',
			align : 'center',
			hidden : true
		},{
			field : 'CODE_NAME',
			title : '编码名称',
			width : '10%',
			align : 'center'
		}, {
			field : 'CODE_ID',
			title : '编码',
			width : '10%',
			align : 'center'
		} ] ],
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false,
		singleSelect : true,

	});
}
//展示添加界面
function showAddKnowledge(){
	var select = $("#knowledge_tree").tree('getSelected');
	$("#knowledge_code").val(select.id);
	$("#add_knowledge_dialog").window('open');
}
//添加
function addKnowledge(){
	$("#add_knowledge_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#add_knowledge_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#knowledge_list_table").datagrid("reload");
			});
		}

	});
}
//弹出修改界面
function showEditKnowledge(){
	var checkData = $("#knowledge_list_table").datagrid('getChecked');
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要修改的数据！");
		return;
	}
	
	$("#knowledge_maxaccept").val(checkData[0].MAXACCEPT);
	$("#edit_name").textbox("setValue", checkData[0].CODE_NAME);
	$("#edit_id").textbox("setValue", checkData[0].CODE_ID);
	$("#edit_knowledge_dialog").window('open');
}
//修改
function editKnowledge(){
	$("#edit_knowledge_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#edit_knowledge_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#knowledge_list_table").datagrid("reload");
			});
		}

	});
}
//删除弹窗
function beforeDeleteKnowledge(){
	/*$("#before_delete_dialog").text("确认删除人员？");
	$("#before_delete_dialog").window('open');*/
	
	var checkData = $("#knowledge_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(" ", "请先选择要删除的数据！", "error");
		return;
	}
	
	$.messager.confirm(" ", "确认要取消选中的数据么？", function(r){
		if (r){
			var ids = "";
			for(var ix=0; ix<checkData.length; ix++){
				ids = checkData[ix].MAXACCEPT + "," + ids;
			}
			
			$.ajax({
				url : webpath + "/knowledge/delKnowledge.action",
				type : "post",
				dataType : "json",
				data : {
					ids : ids
				},
				success : function(data) {
					$.messager.alert(' ', data[0].resultMsg, 'info', function(){
						$("#knowledge_list_table").datagrid("reload");
					});
				}
			});
		}
	});
}
//删除
/*function deleteKnowledge(){
	var checkData = $("#knowledge_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要删除的数据！");
		return;
	}
	var ids = "";
	for(var ix=0; ix<checkData.length; ix++){
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$.ajax({
		url : webpath + "/knowledge/delKnowledge.action",
		type : "post",
		dataType : "json",
		data : {
			"ids" : ids
		},
		success : function(data) {
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#knowledge_list_table").datagrid("reload");
			});
		}
	});
}*/