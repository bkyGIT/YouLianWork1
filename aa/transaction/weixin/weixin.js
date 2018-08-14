function loadPage() {
	
	$("#weixin_list_table").datagrid({
		queryParams: {
			begin_time : $("#begin_time_param").datebox('getValue'),
			end_time : $("#end_time_param").datebox('getValue'),
			order_type: $("#order_type_param").combobox('getValue'),
			orderStatus: "20"
		},
        border: false,  
        locale: "zh_CN",  
        striped: true,  
         sortOrder: "asc",  
         collapsible: false,  
         url: webpath+"/order/weixinList.action",  
         columns: [[  
             { field: 'ck', width: '5%', algin: 'center',checkbox:true },  
             { field: 'MAXACCEPT', title: '序列', align: 'center',hidden:true},  
             { field: 'CUST_ID', title: '客户ID', align: 'center',hidden:true},
             { field: 'ORDER_TYPE_NAME', title: '工单类型', width: '10%', align: 'center',
            	 formatter: function(value,row,index){
     				return value;
     			}
             },
             { field: 'FIX_TYPE_NAME', title: '维护类型', width: '6%', align: 'center' }, 
             { field: 'CUST_NAME', title: '预约客户', width: '6%', align: 'center' },
             { field: 'PHONE', title: '联系电话', width: '8%', align: 'center' },
             { field : 'CUST_ADDRESS', title : '客户地址', width : '20%', align : 'center'}, 
             { field: 'ORDER_STATUS_NAME', title: '工单状态', width: '6%', align: 'center' }, 
             { field: 'ORDER_MARK', title: '预约备注信息', width: '24%', align: 'center',formatter: function (value) {
                 return "<span title='" + value + "'>" + value + "</span>";
             } },
             { field: 'CREAT_TIME', title: '工单创建时间', width: '10%', align: 'center'},
             { field: ' ', title: '查看用户详情', width: '10%', align: 'center', formatter: function (value,row,index) {
                 return "<a href=\"javascript:showCustInfoDiv('" +row.CUST_ID+ "', '0')\">查看</a>";
             }}
         ]],
         pagination: true,//表示在datagrid设置分页              
         rownumbers: false 
	});
}
//展示详情区域
function showCustInfoDiv(custID, flag){
	if(flag == "0"){
		$("#weixin_content_panel").hide();
		$("#weixin_cust_info").show();
		wx_loadCustInfoList(custID);
	}else{
		$("#weixin_content_panel").show();
		$("#weixin_cust_info").hide();
	}
}

//加载用户列表
function wx_loadCustInfoList(custID){
	$("#wx_cust_info_list_table").datagrid({
		queryParams: {
			custID: custID,
		},
        border: false,  
        locale: "zh_CN",  
        striped: true,  
         sortOrder: "asc",  
         collapsible: false,  
         url: webpath+"/weixin/queryUserList.action",  
         columns: [[  
             { field: 'ck', width: '5%', algin: 'center',checkbox:true },  
             { field: 'CUST_NAME', title: '客户名称', width: '15%', align: 'center'},
             { field: 'PHONE', title: '常用电话', width: '20%', align: 'center'},  
             { field: 'ADRESS', title: '地址', width: '41%', align: 'center', 
             	formatter: function(value,row,index){
             		return "<span title='" + value + "'>" + value + "</span>";
             	}
             }
         ]],
         rownumbers: false, 
         singleSelect: true, 
         onLoadSuccess:function(data){  
			if(data.rows.length > 0){
				$('#wx_cust_info_list_table').datagrid('selectRow',0);
			}else{
				wx_clearCustInfo();
			}
		 },
		 onSelect: function(rowIndex, rowData){
		 	//加载用户及周边信息
		 	wx_loadCustInfo(rowIndex, rowData);
		 	//加载工单记录列表
			wx_loadOrderInfoList(rowIndex, rowData);
		 }
	});
}

//清除用户信息
function wx_clearCustInfo(){
	$("#wx_cust_info").html("");
	$("#wx_up_cust_info").html("");
	$("#wx_low_cust_info").html("");
	$("#wx_left_cust_info").html("");
	$("#wx_right_cust_info").html("");
	//清空历史工单
	var checkData = $("#wx_order_info_list_table").datagrid("getRows");
    for(var ix = checkData.length - 1; ix >= 0; ix--){
        $("#wx_order_info_list_table").datagrid('deleteRow',ix);
    }
}
//加载用户及周边信息
function wx_loadCustInfo(rowIndex, rowData){
	var normal_phone = rowData.PHONE;
	if(normal_phone == undefined)
		normal_phone = "";
	/**加载用户信息**/
	var cust_info = [];
	cust_info.push("<input type=\"hidden\" id=\"wx_custID\" value=\"" +rowData.MAXACCEPT+ "\"/>\n");
	cust_info.push("<div style=\"margin-bottom:10px\">\n");
	cust_info.push("	<span style=\"font-weight: bold;\">客户姓名：</span><span id=\"wx_custName\">" +rowData.CUST_NAME+ "</span>\n");
	cust_info.push("</div>\n");
	cust_info.push("<div style=\"margin-bottom:10px\">\n");
	cust_info.push("<span style=\"font-weight: bold;\">常用电话：</span><input class=\"easyui-textbox\" id=\"wx_normal_phone\" value=\"" +normal_phone+ "\" readonly=\"readonly\" name=\"normal_phone\" style=\"width:100px;\" data-options=\"required:true,validType:'mobile'\">\n");
	cust_info.push("</div>\n");
	cust_info.push("<div style=\"margin-bottom:10px\">\n");
	cust_info.push("	<span style=\"font-weight: bold;\">客户地址：</span><span id=\"wx_cust_adress\">" +rowData.ADRESS+ "</span>\n");
	cust_info.push("</div>\n");
	cust_info.push("<div style=\"margin-bottom:10px\">\n");
	cust_info.push("	<span style=\"font-weight: bold;\">缴费信息：</span><span></span>\n");
	cust_info.push("</div>\n");
	$("#wx_cust_info").html(cust_info.join(""));
	//从新加载input解决js写出没有easyui样式
	$("#wx_normal_phone").textbox();
	
	/**加载周边信息**/
	$.ajax({
		url : webpath + "/PhoneInterface/loadAroundInfo.action",
		type : "post",
		dataType : "json",
		data : {"adress": rowData.ADRESS},
		success : function(data) {
			var resultData = data.resultData;
			if(resultData != null){
				//加载楼上信息
				var up_cust_info = resultData.upCust;
				if(up_cust_info != null){
					var html = [];
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户姓名：</span><span>" +up_cust_info.CUST_NAME+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">常用电话：</span><span>" +up_cust_info.PHONE+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户地址：</span><span>" +up_cust_info.ADRESS+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">缴费信息：</span><span></span>\n");
					html.push("</div>\n");
					$("#wx_up_cust_info").html(html.join(""));
				}else{
					$("#wx_up_cust_info").html("");
				}
				
				//加载楼下信息
				var low_cust_info = resultData.lowCust;
				if(low_cust_info != null){
					var html = [];
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户姓名：</span><span>" +low_cust_info.CUST_NAME+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">常用电话：</span><span>" +low_cust_info.PHONE+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户地址：</span><span>" +low_cust_info.ADRESS+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">缴费信息：</span><span></span>\n");
					html.push("</div>\n");
					$("#wx_low_cust_info").html(html.join(""));
				}else{
					$("#wx_low_cust_info").html("");
				}
				
				//加载左邻信息
				var left_cust_info = resultData.leftCust;
				if(left_cust_info != null){
					var html = [];
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户姓名：</span><span>" +left_cust_info.CUST_NAME+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">常用电话：</span><span>" +left_cust_info.PHONE+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户地址：</span><span>" +left_cust_info.ADRESS+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">缴费信息：</span><span></span>\n");
					html.push("</div>\n");
					$("#wx_left_cust_info").html(html.join(""));
				}else{
					$("#wx_left_cust_info").html("");
				}
				
				//加载右舍信息
				var right_cust_info = resultData.rightCust;
				if(right_cust_info != null){
					var html = [];
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户姓名：</span><span>" +right_cust_info.CUST_NAME+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">常用电话：</span><span>" +right_cust_info.PHONE+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">客户地址：</span><span>" +right_cust_info.ADRESS+ "</span>\n");
					html.push("</div>\n");
					html.push("<div style=\"margin-bottom:10px\">\n");
					html.push("	<span style=\"font-weight: bold;\">缴费信息：</span><span></span>\n");
					html.push("</div>\n");
					$("#wx_right_cust_info").html(html.join(""));
				}else{
					$("#wx_right_cust_info").html("");
				}
			}else{
				$("#wx_up_cust_info").html("");
				$("#wx_low_cust_info").html("");
				$("#wx_left_cust_info").html("");
				$("#wx_right_cust_info").html("");
			}
		}
	});
}
//加载工单历史记录
function wx_loadOrderInfoList(rowIndex, rowData){
	$("#wx_order_info_list_table").datagrid({
		queryParams: {
			custID: rowData.MAXACCEPT,
		},
        border: false,  
        locale: "zh_CN",  
        striped: true,  
        sortOrder: "asc",  
        collapsible: false,  
        url: webpath+"/PhoneInterface/getOrderInfoList.action",  
        columns: [[  
            { field: 'MAXACCEPT', title: '序列', align: 'center',hidden:true},  
            { field: 'ORDER_TYPE_NAME', title: '工单类型', width: '80', align: 'center'},  
            { field: 'FIX_TYPE_NAME', title: '维护类型', width: '80', align: 'center'},  
            { field: 'ORDER_STATUS_NAME', title: '工单状态', width: '80', align: 'center'},  
            { field: 'OPR_NAME', title: '预约客服', width: '60', align: 'center'}, 
            { field: 'CUST_ADDRESS', title: '地址', width: '160', align: 'center',formatter: function(value,row,index){
 				return "<span title='" + value + "'>" + value + "</span>";
 			}}, 
            { field: 'COMMIT_TIME', title: '维护完成时间', width: '120', align: 'center',
            	formatter:function(value,row,index){
            		if(value == undefined)
            			value = "";
            		return "<span title='" + value + "'>" + value + "</span>";
            	}
            },
            { field: 'ORDER_MARK', title: '预约备注', width: '200', align: 'center',formatter: function(value,row,index){
 				return "<span title='" + value + "'>" + value + "</span>";
 			}},
            { field: 'FIX_MARK', title: '维护备注', width: '200', align: 'center',formatter: function(value,row,index){
            	var returnStr = "";
            	if(value != undefined)
            		returnStr = "<span title='" + value + "'>" + value + "</span>";
 				return returnStr;
 			}},
 			{ field: 'FIXED_TEMP', title: '维护后温度', width: '60', align: 'center'}
        ]],
        rownumbers: false
	});
}



function showchangeOrder(){
	var checkData = $("#weixin_list_table").datagrid("getChecked");	
	if(checkData.length < 1){
		$.messager.alert(' ', "至少要选择一个转移的工单！");
		return;
	}
	$("#change_order_dialog").window('open');
	
}

//转移工单
function changeOrder(){
	var checkData = $("#weixin_list_table").datagrid("getChecked");	
	var ids = "";
	for(var ix=0; ix<checkData.length; ix++){
		ids = checkData[ix].MAXACCEPT + "," + ids;
	}
	$("#order_id").val(ids);
	
	$("#change_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#change_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#weixin_list_table").datagrid("reload");
			});
		}

	});
}

//工单派发弹窗
function showSendOrder(){
	var checkData = $("#weixin_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要派发的工单！");
		return;
	}
	$("#send_order_dialog").window('open');
}

//工单派发
function sendOrder(){
	$("#fix_dept_id").combobox("getValue");
	var checkData = $("#weixin_list_table").datagrid("getChecked");
	var ids = [];
	for(var ix=0; ix<checkData.length; ix++){
		ids .push(checkData[ix].MAXACCEPT);
	}
	$("#order_maxaccept").val(ids);
	
	$("#send_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#send_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#weixin_list_table").datagrid("reload");
			});
		}
	});
}

function showOverOrder(){
	var checkData = $("#weixin_list_table").datagrid("getChecked");
	if(checkData.length < 1){
		$.messager.alert(' ', "请先选择要处理的工单！");
		return;
	}
	$("#over_order_dialog").window('open');
}
//工单处理
function overOrder(){
	var checkData = $("#weixin_list_table").datagrid("getChecked");
	var ids = [];
	for(var ix=0; ix<checkData.length; ix++){
		ids .push(checkData[ix].MAXACCEPT);
	}
	$("#commit_ids").val(ids);
	
	$("#over_order_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			$('#over_order_dialog').window('close');
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				$("#weixin_list_table").datagrid("reload");
			});
		}
	});
}