function loadPage() {
	
	$("#conver_list_table").datagrid({
		queryParams: {
			begin_time: $("#begin_time_param").datebox('getValue'),
			end_time: $("#end_time_param").datebox('getValue'),
			kf_maxaccept: $("#kf_param").combobox('getValue'),
			order_id: $("#order_id").textbox('getValue'),
			talk_flag: $("#talk_flag_param").combobox("getValue"),
			in_flag: $("#in_flag_param").combobox("getValue")
		},
        border: false,  
        locale: "zh_CN",  
        striped: true,  
         sortOrder: "asc",  
         collapsible: false,  
         url: webpath+"/conver/converList.action",  
         columns: [[  
             { field: 'B_MAXACCEPT', title: '工单编号', align: 'center', width: '6%'},  
             { field: 'B_CUST_NAME', title: '用户名称', width: '6%', align: 'center'},
             { field: 'A_PHONE', title: '通话号码', width: '6%', align: 'center'},  
             { field : 'B_CUST_ADDRESS', title : '客户地址', width : '15%', align : 'center',
            	 formatter:function(value,row,index){
            		 if(value == undefined)
            			 value = "";
            		 return "<span title='" + value + "'>" + value + "</span>";
            	 }
             }, 
             { field: 'A_OPR_NAME', title: '预约客服', width: '6%', align: 'center' },  
             { field: 'A_TALK_FLAG', title: '接通状态', width: '6%', align: 'center',formatter:function(value,row,index){
            	 var flag = "已接通";
            	 if(value == "0"){
            		 flag = "未接通";
            	 }
            	 return flag;
             }},
             { field: 'A_IN_FLAG', title: '呼叫方向', width: '6%', align: 'center',formatter:function(value,row,index){
            	 var flag = "呼出";
            	 if(value == "1"){
            		 flag = "呼入";
            	 }
            	 return flag;
             }},
             { field: 'A_CALL_TIME', title: '呼叫时间', width: '8%', align: 'center',
            	 formatter:function(value,row,index){
            		 if(value == undefined)
            			 value = "";
            		 return "<span title='" + value + "'>" + value + "</span>";
            	 }
             },
             { field: 'A_TALK_TIME', title: '接通时间', width: '8%', align: 'center',
            	 formatter:function(value,row,index){
            		 if(value == undefined)
            			 value = "";
            		 return "<span title='" + value + "'>" + value + "</span>";
            	 }
             },
             { field: 'A_HANGUP_TIME', title: '挂机时间', width: '8%', align: 'center',
            	 formatter:function(value,row,index){
            		 if(value == undefined)
            			 value = "";
            		 return "<span title='" + value + "'>" + value + "</span>";
            	 }
             }, 
             { field: 'A_WAIT_LENGTH', title: '等待时间(秒)', width: '6%', align: 'center'},
             { field: 'A_TALK_LENGTH', title: '通话时长(秒)', width: '6%', align: 'center'},
             { field: 'A_RECORD_FILE', title: '录音试听', width: '6%', align: 'center',formatter:function(value,row,index){
            	 var returnHtml = "";
            	 if(value != "" && value != undefined){
            		 value = value.replace(/\\/g, "/");
            		 returnHtml = "<a href=\"javascript:playSound('" +value+ "');\">播放录音</a>";
            	 }
            	 return returnHtml;
             }}
         ]],
         pagination: true,//表示在datagrid设置分页              
         rownumbers: false,
         singleSelect: true, 
         pageSize:20
	});
}
//播放录音
function playSound(soundPath){
	$("#snd_ie").attr("src", "/sound/" + soundPath);
}

function clearParam(){
	$("#creat_time_param").datebox('setValue',null);
	$("#order_type_param").combobox('setValue',null);
	$("#fix_type_param").combobox('setValue',null);
	
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

function beforeRemoveOrder(){
	$("#before_remove_dialog").text("确认删除工单？");
	$("#before_remove_dialog").window('open');
}


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
function  removeOrder(){
	var checkData = $("#order_list_table").datagrid().datagrid("getChecked");
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
