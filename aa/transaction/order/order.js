function loadPage() {
		$("#order_list_table").datagrid({
			queryParams: {
				creat_time: $("#creat_time_param").datebox('getValue'),
				end_time: $("#end_time_param").datebox('getValue'),
				order_type: $("#order_type_param").combobox('getValue'),
				fix_type: $("#fix_type_param").combobox('getValue'),
			},
            border: false,  
            locale: "zh_CN",  
            striped: true,  
             sortOrder: "asc",  
             collapsible: false,  
             url: webpath+"/order/orderList.action",  
             columns: [[  
                 { field: 'ck', width: '5%', algin: 'center',checkbox:true },  
                 { field: 'MAXACCEPT', title: '工单编码', align: 'center',width: '5%'},  
                 { field: 'ORDER_TYPE', title: '工单编码', align: 'center',hidden:true},  
                 { field: 'ORDER_TYPE_NAME', title: '工单类型', width: '6%', align: 'center',
                	 formatter: function(value,row,index){
         				return value;
         			}
                 },
                 { field: 'FIX_TYPE_NAME', title: '维护类型', width: '6%', align: 'center'},  
                 { field: 'OPR_NAME', title: '预约客服', width: '6%', align: 'center' },  
                 { field: 'CUST_NAME', title: '预约客户', width: '6%', align: 'center' },
                 { field: 'PHONE', title: '联系电话', width: '8%', align: 'center' },
                 { field : 'CUST_ADDRESS', title : '客户地址', width : '15%', align : 'center'
        		}, 
                 { field: 'ORDER_STATUS_NAME', title: '工单状态', width: '6%', align: 'center' }, 
                 { field: 'ORDER_MARK', title: '预约备注信息', width: '24%', align: 'center',formatter: function(value,row,index){
     				return "<span title='" + value + "'>" + value + "</span>";
     			}},
                 { field: 'CREAT_TIME', title: '工单创建时间', width: '10%', align: 'center' }
             ]],
             pagination: true,//表示在datagrid设置分页              
             rownumbers: false, 
             pageSize: 20
		});
	}

function showAddOrder(){
	$("#add_order_dialog").window('open');
	$("#order_status").combobox('setValue',"20").combobox('readonly',true);;
	
}

//添加工单
function addOrder(){
	$("#add_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#add_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}

/*function beforeRemoveOrder(){
	$("#before_remove_dialog").window('open');
	$("#before_remove_dialog").text("确认取消工单？");
}*/


function beforeModifyOrder(){
	var checkData = $("#order_list_table").datagrid("getChecked");	
	if(checkData.length != 1){
		$.messager.alert(' ', "请选择一个要修改的工单！");
		return;
	}
	
	$("#modify_order_dialog").window('open');
	$("#o_order_maxaccept").textbox("setValue", checkData[0].MAXACCEPT);
	$('#o_order_types').combobox('select', checkData[0].ORDER_TYPE);
	$('#o_order_fix_type').combobox('select', checkData[0].FIX_TYPE);
	$('#o_order_phone').textbox("setValue", checkData[0].PHONE);
	$('#o_order_mark').textbox("setValue", checkData[0].ORDER_MARK);
	
	
}

//修改人员
function modifyOrder(){
	$("#modify_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#modify_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data[0].resultMsg, 'info', function(){
				$("#order_list_table").datagrid("reload");
			});
		}

	});
}


//取消工单
function  beforeRemoveOrder(){
	/*var checkData = $("#order_list_table").datagrid().datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要取消的工单！");
		return;
	}
	var ids = "";
	for(var ix=0; ix<checkData.length; ix++){
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	
	$.ajax({
		url : webpath + "/order/removeOrder.action",
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
	});*/
	
	var checkData = $("#order_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(" ", "请先选择要取消的工单！", "error");
		return;
	}
	
	$.messager.confirm(" ", "确认要取消选中的工单么？", function(r){
		if (r){
			var ids = "";
			for(var ix=0; ix<checkData.length; ix++){
				ids = checkData[ix].MAXACCEPT + "," + ids;
			}
			
			$.ajax({
				url : webpath + "/order/removeOrder.action",
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

//工单派发弹窗
function showSendOrder(){
	var checkData = $("#order_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要派发的工单！");
		return;
	}
	$("#send_order_dialog").window('open');
}


function sendOrder(){
	$("#modify_order_form").form("submit", {
		onSubmit : function() {
			var checkData = $("#order_list_table").datagrid("getChecked");
			var ids = [];
			for(var ix=0; ix<checkData.length; ix++){
				ids .push(checkData[ix].MAXACCEPT);
			}
			$.ajax({
				url : webpath + "/order/sendToFix.action",
				type : "post",
				dataType : "json",
				traditional: true,
				data : {
					ids : ids,
					fix_dept_id:$("#fix_dept_id").combobox("getValue")
				},
				success : function(data) {
					$('#send_order_dialog').window('close');
					$.messager.alert(' ', data.resultMsg, 'info', function(){
						$("#order_list_table").datagrid("reload");
					});
				}
			});
		},
		success : function(data) {
		}
	});
}

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
