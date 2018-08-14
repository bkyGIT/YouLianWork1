function loadPage() {
	var orderStatus = "21,22,23,24,25,26,27,28,29,80";
	var order_status = $("#order_status").combobox("getValue");
	var orderDgree = $("#order_dgree").combobox("getValue");
	var deptId = $("#fix_dept_name").combobox("getValue");
	if(order_status != "" && order_status != undefined){
		orderStatus = order_status;
	}
	$("#order_list_table").datagrid({
		queryParams : {
			begin_time : $("#begin_time_param").datebox('getValue'),
			end_time : $("#end_time_param").datebox('getValue'),
			fix_type : $("#fix_type_param").combobox('getValue'),
			order_id: $("#order_id").textbox('getValue'),
			orderStatus: orderStatus,
			/*orderChannel: "70",*/
				
			orderDgree : orderDgree,
			deptId : deptId
		},
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		url : webpath + "/order/orderQueryList.action",
		columns : [ [ {
			field : 'ck',
			width : '5%',
			algin : 'center',
			checkbox : true
		}, {
			field : 'MAXACCEPT',
			title : '工单编码',
			align : 'center',
			width : '5%'
		}, {
			field : 'ORDER_TYPE_NAME',
			title : '工单类型',
			width : '5%',
			align : 'center'
		}, {
			field : 'FIX_TYPE_NAME',
			title : '维护类型',
			width : '5%',
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
		},{ field: 'PHONE', title: '联系电话', width: '6%', align: 'center' }, {
			field : 'CUST_ADDRESS',
			title : '客户地址',
			width : '10%',
			align : 'center',
			formatter: function(value,row,index){
				if(value == undefined)
					value = "";
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'FIXUSER_NAME',
			title : '维护人员',
			width : '4%',
			align : 'center'
		}, {
			field : 'ORDER_STATUS_NAME',
			title : '工单状态',
			width : '6%',
			align : 'center'
		},{
			field : 'ORDER_DGREE_NAME',
			title : '工单满意度',
			width : '6%',
			align : 'center'
		},{
			field : 'FIX_DEPT_NAME',
			title : '维护公司',
			width : '6%',
			align : 'center'
		},{
			field : 'ORDER_MARK',
			title : '预约备注信息',
			width : '16%',
			align : 'center',
			formatter: function(value,row,index){
				if(value == undefined)
					value = "";
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'FIX_MARK',
			title : '维护备注信息',
			width : '16%',
			align : 'center',
			formatter: function(value,row,index){
				if(value == undefined)
					value = "";
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'CREAT_TIME',
			title : '工单创建时间',
			width : '9%',
			align : 'center'
		}, {
			field : 'COMMIT_TIME',
			title : '工单提交时间',
			width : '9%',
			align : 'center'
		}
		] ],
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false, 
		pageSize: 20
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
