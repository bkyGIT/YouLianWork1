function loadPage() {
	var orderStatus = "24,29";
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
			orderStatus: orderStatus
		},
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		singleSelect:true,
		url : webpath + "/order/orderReviewList.action",
		columns : [ [ {
			field : 'ck',
			width : '5%',
			algin : 'center',
			checkbox : true
		}, {
			field : 'MAXACCEPT',
			title : '工单编号',
			align : 'center',
			width : '6%'
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
			width : '5%',
			align : 'center'
		}, {
			field : 'CUST_NAME',
			title : '预约客户',
			width : '5%',
			align : 'center'
		},{ 
			field: 'PHONE', title: '联系电话', width: '6%', align: 'center' }, {
			field : 'CUST_ADDRESS',
			title : '客户地址',
			width : '13%',
			align : 'center',
			formatter: function(value,row,index){
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'FIXUSER_NAME',
			title : '维护人员',
			width : '6%',
			align : 'center'
		}, {
			field : 'ORDER_STATUS',
			title : '工单状态码',
			align : 'center',
			hidden : true
		}, {
			field : 'ORDER_STATUS_NAME',
			title : '工单状态',
			align : 'center',
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
			width : '8%',
			align : 'center',
			formatter: function(value,row,index){
				if(value == undefined)
					value = "";
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'RETURN_VISIT_TIME',
			title : '回访时间',
			width : '8%',
			align : 'center',
			formatter: function(value,row,index){
				if(value == undefined)
					value = "";
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}
		] ],
		rowStyler:function(index,row){  
	        if (row.ORDER_STATUS == '29'){  
	            return 'background-color:pink;color:blue;font-weight:bold;'; 
	        }  
	    },
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false,
		pageSize: 20
	});
}

// 工单回访弹窗
function showRevewDialog() {
	var checkData = $("#order_list_table").datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要回访的工单！");
		return;
	}
	var status = checkData[0].ORDER_STATUS;
	$("#order").val(status);
	//该回访页面显示未回访工单(orderStatus=24,29),所以暂无需加判断条件
	/*if(status != "24"){
		$.messager.alert(' ', "该工单已回访完毕，不需要再回访！");
		return;
	}*/
	/*if(status != "24" && status != '29'){
		$.messager.alert(' ', "该工单已回访完毕，不需要再回访！");
		return;
	}*/
	var id = checkData[0].MAXACCEPT;
	$("#review_id").val(id);
	var phone = checkData[0].PHONE;
	//添加外呼
	$.ajax({
		url : webpath + "/PhoneInterface/setPhoneList.action",
		type : "post",
		dataType : "json",
		data : {
			"phone" : phone, "orderID": id
		},
		success : function(data) {
			if(data.resultCode != "0000"){
				$.messager.alert(' ', data.resultMsg, 'error');
			}else{
				$("#review_order_dialog").window('open');
			}
		}
	});
	
}

//工单回访
function revewOrder(){
	$("#review_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#review_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}
//工单流转
function showSendDialog() {
	var checkData = $("#order_list_table").datagrid("getChecked");
	if (checkData.length < 1) {
		$.messager.alert(' ', "请先选择要派发的工单！");
		return;
	}
	var status = checkData[0].ORDER_STATUS;
	if(status != "24"){
		$.messager.alert(' ', "该工单已回访完毕，不需要再回访！");
		return;
	}
	var id = checkData[0].MAXACCEPT;
	$("#send_review_id").val(id);
	
	$("#send_order_dialog").window("open");
}

//工单流转
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
/**
 * 工单终结
 */
function overOrder(){
	var checkData = $("#order_list_table").datagrid("getChecked");
	if(checkData.length == 0){
		$.messager.alert(" ", "请选择想要终结的工单！", "error");
		return;
	}
	
	$.messager.confirm(" ", "确认要终结选中的工单么？", function(r){
		if (r){
			var ids = [];
			for(var ix=0; ix<checkData.length; ix++){
				ids .push(checkData[ix].MAXACCEPT);
			}
			$.ajax({
				url : webpath + "/order/overOrder.action",
				type : "post",
				dataType : "json",
				traditional: true,
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