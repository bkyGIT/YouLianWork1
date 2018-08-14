function loadPage() {
	var orderStatus = "22,27";
	var order_status = $("#order_status").combobox("getValue");
	if(order_status != "" && order_status != undefined){
		orderStatus = order_status;
	}
	$("#order_list_table").datagrid({
		queryParams : {
			begin_time : $("#begin_time_param").datebox('getValue'),
			end_time : $("#end_time_param").datebox('getValue'),
			fix_type : $("#fix_type_param").combobox('getValue'),
			/*fix_xq: $("#fix_xq").combobox('getValue'),*/
			orderStatus: orderStatus
		},
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		url : webpath + "/fixOrder/orderList.action",
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
			width : '6%',
			align : 'center'
		}, {
			field : 'OPR_NAME',
			title : '预约客服',
			width : '6%',
			align : 'center'
		}, {
			field : 'CUST_NAME',
			title : '预约客户',
			width : '6%',
			align : 'center'
		}, { field: 'PHONE', title: '联系电话', width: '8%', align: 'center' },{
			field : 'CUST_ADDRESS',
			title : '客户地址',
			width : '20%',
			align : 'center'
		}, {
			field : 'ORDER_STATUS_NAME',
			title : '工单状态',
			width : '6%',
			align : 'center'
		}, {
			field : 'ORDER_STATUS',
			title : '工单状态',
			width : '6%',
			align : 'center',
			hidden : true
		},{
			field : 'ORDER_MARK',
			title : '预约备注信息',
			width : '24%',
			align : 'center', 
			formatter: function(value,row,index){
				return "<span title='" + value + "'>" + value + "</span>";
			}
		}, {
			field : 'CREAT_TIME',
			title : '工单创建时间',
			width : '8%',
			align : 'center'
		} , {
			field : 'TOFIXDEPT_TIME',
			title : '工单派发时间',
			width : '8%',
			align : 'center'
		}] ],
		rowStyler:function(index,row){  
	        if (row.ORDER_STATUS == '27'){  
	            return 'background-color:pink;color:blue;font-weight:bold;'; 
	        }  
	    },
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
