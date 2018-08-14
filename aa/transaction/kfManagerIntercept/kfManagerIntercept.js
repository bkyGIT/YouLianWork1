function loadPage(kfManagerMaxaccept) {
	var kfManagerId = kfManagerMaxaccept;
	$("#order_list_table").datagrid({
		queryParams : {
			begin_time : $("#creat_time_param").datebox('getValue'),
			end_time : $("#end_time_param").datebox('getValue'),
			fix_type : $("#fix_type_param").combobox('getValue'),
			orderChannel : $("#order_channel").combobox("getValue"),
			kfManagerId : kfManagerId
		},
		border : false,
		locale : "zh_CN",
		striped : true,
		sortOrder : "asc",
		collapsible : false,
		url : webpath + "/order/returnOrderList.action",
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
		},{
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
		pagination : true,// 表示在datagrid设置分页
		rownumbers : false,
		pageSize: 20
	});
	}

//驳回工单派发弹窗
function showReturnOrder(){
	var checkData = $("#order_list_table").datagrid("getChecked");
	if(checkData.length == 0){
		$.messager.alert(" ", "请选择想要驳回的工单！", "error");
		return;
	}
	
	$.messager.confirm(" ", "确认要驳回选中的工单么？", function(r){
		if (r){
			var ids = [];
			for(var ix=0; ix<checkData.length; ix++){
				ids .push(checkData[ix].MAXACCEPT);
			}
			$.ajax({
				url : webpath + "/order/returnOrder.action",
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

