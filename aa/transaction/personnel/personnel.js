function loadPage() {
		$("#personnel_list_table").datagrid({
            border: false,  
            locale: "zh_CN",  
            striped: true,  
             sortOrder: "asc",  
             collapsible: false,  
             url: webpath+"/personnel/personnelList.action",  
             columns: [[  
                 { field: 'ck', width: '5%', algin: 'center',checkbox:true },  
                 { field: 'MAXACCEPT', title: '序列', width: '10%', align: 'center',hidden:true},  
                 { field: 'USER_ACCOUNT', title: '登录账号', width: '10%', align: 'center' },  
                 { field: 'USER_PWD', title: '密码', width: '10%', align: 'center',hidden:true},  
                 { field: 'USER_NAME', title: '人员名称', width: '10%', align: 'center' },  
                 { field: 'PHONE', title: '手机', width: '10%', align: 'center' },  
                 { field: 'TEL', title: '电话', width: '10%', align: 'center' }, 
                 { field: 'ADDRESS', title: '地址', width: '10%', align: 'center' },
                 { field: 'PHOUSER_ROLENE', title: '角色ID', width: '10%', align: 'center',hidden:true}, 
                 { field: 'ROLE_NAME', title: '当前角色', width: '10%', align: 'center' },
                 { field: 'DEPT_CODE', title: '所属部门编码', width: '5%', align: 'center',hidden:true},
                 { field: 'DEPT_NAME', title: '所属部门', width: '10%', align: 'center' }
             ]],  
             pagination: true,//表示在datagrid设置分页              
             rownumbers: false,  
             singleSelect: true,  

		});
	}

function showAddPersonnel(){
	$("#add_personnel_form").form("clear");
	$("#add_personnel_dialog").window('open');
}

//添加人员
function addPersonnel(){
	$("#add_personnel_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#add_personnel_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#personnel_list_table").datagrid("reload");
			});
		}

	});
}

/*function beforeDeletePersonnel(){
	var checkData = $("#personnel_list_table").datagrid().datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要删除的人员！");
		return;
	}
	
	$("#before_delete_dialog").text("确认删除人员？");
	$("#before_delete_dialog").window('open');
}*/

//删除角色
function beforeDeletePersonnel(){
	/*var checkData = $("#personnel_list_table").datagrid().datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要删除的人员！");
		return;
	}
	
	var ids = "";
	for(var ix=0; ix<checkData.length; ix++){
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$.ajax({
		url : webpath + "/personnel/delPersonnel.action",
		type : "post",
		dataType : "json",
		data : {
			"ids" : ids
		},
		success : function(data) {
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#personnel_list_table").datagrid("reload");
			});
		}
	});*/
	
	var checkData = $("#personnel_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(" ", "请先选择要删除的人员！", "error");
		return;
	}
	
	$.messager.confirm(" ", "确认要取消选中的人员么？", function(r){
		if (r){
			var ids = "";
			for(var ix=0; ix<checkData.length; ix++){
				ids = checkData[ix].MAXACCEPT + "," + ids;
			}
			
			$.ajax({
				url : webpath + "/personnel/delPersonnel.action",
				type : "post",
				dataType : "json",
				data : {
					ids : ids
				},
				success : function(data) {
					$.messager.alert(' ', data[0].resultMsg, 'info', function(){
						$("#personnel_list_table").datagrid("reload");
					});
				}
			});
		}
	});
}

function beforeModifyPersonnel(){
	var checkData = $("#personnel_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要修改的人员！");
		return;
	}
	$("#modify_personnel_dialog").window('open');
	$("#m_personnel_maxaccept").textbox("setValue", checkData[0].MAXACCEPT);
	$("#m_personnel_account").textbox("setValue", checkData[0].USER_ACCOUNT);
	$("#m_personnel_pwd").textbox("setValue", checkData[0].USER_PWD);
	$("#m_personnel_name").textbox("setValue", checkData[0].USER_NAME);
	$("#m_personnel_phone").textbox("setValue", checkData[0].PHONE);
	$("#m_personnel_tel").textbox("setValue", checkData[0].TEL);
	$("#m_personnel_address").textbox("setValue", checkData[0].ADDRESS);
	$('#m_personnel_role').combobox('select', checkData[0].USER_ROLE);
	$('#m_personnel_dept').combobox('select', checkData[0].DEPT_CODE);
	$('#m_personnel_vos').textbox("setValue", checkData[0].VOS_ACCOUNT);
	$('#m_personnel_vospwd').textbox("setValue", checkData[0].VOS_PWD);
}

//修改人员
function modifyPersonnel(){
	$("#modify_personnel_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#modify_personnel_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#personnel_list_table").datagrid("reload");
			});
		}

	});
}