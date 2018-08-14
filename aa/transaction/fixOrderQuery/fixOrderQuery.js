function loadPage() {
	var orderStatus = "24,25,26,29,80";
	var order_status = $("#order_status").combobox("getValue");
	if(order_status != "" && order_status != undefined){
		orderStatus = order_status;
	}
	$("#order_list_table").datagrid({
		queryParams : {
			begin_time : $("#begin_time_param").datebox('getValue'),
			end_time : $("#end_time_param").datebox('getValue'),
			fix_type : $("#fix_type_param").combobox('getValue'),
			order_type: $("#order_type_param").combobox('getValue'),
		/*	fix_xq: $("#fix_xq").combobox('getValue'),*/
			orderStatus: orderStatus
		},
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		url : webpath + "/fixOrder/fixOrderQueryList.action",
		columns : [ [ {
			field : 'ck',
			width : '5%',
			algin : 'center',
			checkbox : true
		}, {
			field : 'MAXACCEPT',
			title : '序列',
			align : 'center',
			hidden : true
		}, {
			field : 'ORDER_TYPE_NAME',
			title : '工单类型',
			width : '6%',
			align : 'center'
		}, {
			field : 'FIX_TYPE_NAME',
			title : '维护类型',
			width : '4%',
			align : 'center'
		}, {
			field : 'OPR_NAME',
			title : '预约客服',
			width : '4%',
			align : 'center'
		}, {
			field : 'CUST_NAME',
			title : '预约客户',
			width : '4%',
			align : 'center'
		}, { field: 'PHONE', title: '联系电话', width: '6%', align: 'center' },{
			field : 'CUST_ADDRESS',
			title : '客户地址',
			width : '18%',
			align : 'center'
		}, {
			field : 'FIXUSER_NAME',
			title : '维护人员',
			width : '6%',
			align : 'center'
		}, {
			field : 'ORDER_STATUS_NAME',
			title : '工单状态',
			width : '6%',
			align : 'center'
		}, {
			field : 'ORDER_MARK',
			title : '预约备注信息',
			width : '25%',
			align : 'center', 
			formatter: function(value,row,index){
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'CREAT_TIME',
			title : '工单创建时间',
			width : '10%',
			align : 'center'
		}, {
			field : 'COMMIT_TIME',
			title : '工单提交时间',
			width : '10%',
			align : 'center'
		}
		] ],
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false
	});
}

// 工单派发弹窗
function showSendOrder() {
	var checkData = $("#order_list_table").datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要派发的工单！");
		return;
	}
	var ids = "";
	for ( var ix = 0; ix < checkData.length; ix++) {
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$("#send_ids").val(ids);
	$("#send_order_dialog").window('open');
}

//工单派发
function sendOrder(){
	$("#send_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#send_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}

//工单提交弹窗
function showCommitOrder(){
	var checkData = $("#order_list_table").datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要派发的工单！");
		return;
	}
	var ids = "";
	for ( var ix = 0; ix < checkData.length; ix++) {
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$("#commit_ids").val(ids);
	$("#commit_order_dialog").window('open');
}

//工单提交
function commitOrder(){
	$("#commit_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#commit_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}
