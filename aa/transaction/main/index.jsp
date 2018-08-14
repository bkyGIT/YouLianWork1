<%@page import="com.yl.common.user.pojo.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
	String url = request.getParameter("url");
	
	User user = (User)session.getAttribute("userInfo");
	String deptType = user.getDeptType();
	String roleLevel = user.getRoleLevel();
%>

<!-- 根据登录权限跳转不同页面 -->
<%
	if(deptType.equals("61") && roleLevel.equals("2") && url.equals("/transaction/test/test.jsp")){
		url = "/transaction/kfPerson/kfPerson.jsp";
	}else if(deptType.equals("61") && roleLevel.equals("1") && url.equals("/transaction/test/test.jsp")){
		url = "/transaction/kfManager/kfManager.jsp";
	}else if(deptType.equals("62") && roleLevel.equals("1") && url.equals("/transaction/test/test.jsp")){
		url = "/transaction/fixManager/fixManager.jsp";
	}else if(deptType.equals("62") && roleLevel.equals("2") && url.equals("/transaction/test/test.jsp")){
		url = "/transaction/fixPerson/fixPerson.jsp";
	}else if(url.equals("/transaction/test/test.jsp")){
		url = "/transaction/analysis/analysis.jsp";
	}
%>

<!-- 引入公共js -->
<jsp:include page="/common/jsp/commonHeader.jsp" flush="true" />
<script type="text/javascript" src="<%=webpath%>/transaction/main/index.js"></script>
<jsp:include page="<%=url%>"/>


<style type="text/css">
.top_search {
	height: 40px;
	margin: 0px auto;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background: #FFF;
	margin-top: 20px;
	text-align: left;
	line-height: 40px;
}

.menu_before {
	margin-left: 30px;
}

.menu_botton {
	margin-left: 10px;
}
a{
	color:blue;
}
a:hover{
	color:red;
	border-bottom: solid 1px;
}
</style>

<!-- 下发开发软电话弹窗功能 -->
<div class="easyui-window" title="来电弹窗" id="phone_window" style="height:580px;width:900px;" data-options="modal:true,collapsible:false,minimizable:false,inline:false,cls:'c2',closed:true,onClose:closeWindow">
	<!-- 弹窗头部 -->
	<div style="padding-left:20px;background-color: #cffdbe;height:30px;">
		<div style="float:left;"><span style="font-size:20px;font-weight: bold;">来电号码：</span><span id="phone_num" style="font-size:20px;font-weight: bold;color:red;"></span></div>
		<div style="float: right;margin-right:20px;margin-top:6px;"><a href="javascript:self_query();">手动查询</a></div>
	</div>
	<!-- 用户信息、维护信息部分 -->
	<div style="width:100%;">
		<div  style="float:left;width:100%;">
			<div style="float:left;width:50%;">
				<div id="cust_info" class="easyui-panel" title="用户信息" style="float:left;width:100%;height:160px;padding:10px;">
					<!-- <div style="margin-bottom:10px">
						<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
					</div>
					<div style="margin-bottom:10px">
						<span style="font-weight: bold;">常用电话：</span><input class="easyui-textbox" readonly="readonly" name="normal_phone" style="width:150px;" data-options="required:true,validType:'mobile'">
						<a href="javascript:copy_phone();" style="border-bottom: solid 1px blue;"><span style="color:blue;">将来电号码设为常用</span></a> 
						<a style="border-bottom: solid 1px blue;" href="javascript:change_phone();"><span style="color:blue;">手动修改常用号码</span></a>
					</div>
					<div style="margin-bottom:10px">
						<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
					</div>
					<div style="margin-bottom:10px">
						<span style="font-weight: bold;">缴费信息：</span><span></span>
					</div> -->
				</div>
			</div>
			<div style="float:right;width:50%;">
				<div id="fix_info" class="easyui-panel" title="维护信息" style="float:left;width:100%;height:160px;padding:10px;">
					<input type="hidden" id="reback_order_id"/>
					<div style="margin-bottom:10px;float: left;">
						<span style="font-weight: bold;">工单类型：</span>
						<input name="index_order_status" id="index_order_status"
											class="easyui-combobox" style="width:150px;"
											data-options="
												url: '<%=webpath%>/order/getCommonCode.action?codeKey=ORDER_TYPE',
												method: 'post',
												valueField:'CODE_ID',
												textField:'CODE_NAME',
												groupField:'group',
												labelPosition: 'left',
												panelHeight:'auto',
												editable:false,
												required:true">
					</div>
					<div style="margin-bottom:10px;display: none;float: right;" id="fix_type_div">
						<span style="font-weight: bold;">维护类型：</span>
						<input name="index_fix_type" id="index_fix_type"
											class="easyui-combobox" style="width:100px;"
											data-options="
												url: '<%=webpath%>/order/getCommonCode.action?codeKey=FIX_TYPE',
												method: 'post',
												valueField:'CODE_ID',
												textField:'CODE_NAME',
												groupField:'group',
												labelPosition: 'left',
												panelHeight:'auto',
												editable:false">
					</div>
					<div style="margin-bottom:10px;float: left;">
						<span style="font-weight: bold;">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</span>
						<input class="easyui-textbox" id="index_order_mark" multiline="true" style="width:150px;height:40px">
					</div>
					<div style="margin-bottom:10px;float: right;">
						<span style="font-weight: bold;">联系电话：</span>
						<input class="easyui-textbox" name="conn_phone" id="conn_phone" style="width:100px;" data-options="required:true,validType:'mobile'">
					</div>
					<div style="margin-bottom:10px;float: right;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveOrder();" style="width:83px">创建工单</a>
					</div>
					
				</div>
			</div>
		</div>
		<div style="float:left;width:25%;">
			<div id="up_cust_info" class="easyui-panel" title="楼上信息" style="float:left;width:100%;height:170px;padding:10px;">
				<!-- <div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">常用电话：</span><span></span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">缴费信息：</span><span></span>
				</div> -->
			</div>
		</div>
		<div style="float:left;width:25%;">
			<div id="low_cust_info" class="easyui-panel" title="楼下信息" style="float:left;width:100%;height:170px;padding:10px;">
				<!-- <div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">常用电话：</span><span></span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">缴费信息：</span><span></span>
				</div> -->
			</div>
		</div>
		<div style="float:left;width:25%;">
			<div id="left_cust_info" class="easyui-panel" title="左邻信息" style="float:left;width:100%;height:170px;padding:10px;">
				<!-- <div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">常用电话：</span><span></span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">缴费信息：</span><span></span>
				</div> -->
			</div>
		</div>
		<div style="float:right;width:25%;">
			<div id="right_cust_info" class="easyui-panel" title="右舍信息" style="float:right;width:100%;height:170px;padding:10px;">
				<!-- <div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户姓名：</span><span>战三</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">常用电话：</span><span></span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">客户地址：</span><span>朝阳区东地天澜小区4栋3单元1101</span>
				</div>
				<div style="margin-bottom:10px">
					<span style="font-weight: bold;">缴费信息：</span><span></span>
				</div> -->
			</div>
		</div>
	</div>
	<div style="float:left;width:100%;">
		<div style="float:left;width:50%;">
			<div class="easyui-panel" style="width:100%;" title="用户信息列表">
				<table id="cust_info_list_table" class="easyui-datagrid" style="width:100%;height:146px"
				data-options="singleSelect:true,collapsible:true">
					<thead>
						<tr>
							<th data-options="field:'ck',width:5,halign:'center',checkbox:true">Item ID</th>
							<th data-options="field:'MAXACCEPT',width:50,halign:'center',hidden:true">序列</th>
							<th data-options="field:'CUST_NAME',width:100,align:'right',halign:'center'">客户名称</th>
							<th data-options="field:'PHONE',width:150,align:'right',halign:'center'">常用电话</th>
							<th data-options="field:'ADRESS',width:200,halign:'center'">地址</th>
							<th data-options="field:'CREATE_TIME',width:150,align:'center',halign:'center'">客户创建时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div style="float:right;width:50%;">
			<div class="easyui-panel" style="width:100%;" title="历史工单列表">
				<table id="order_info_list_table" class="easyui-datagrid" style="width:100%;height:146px"
				data-options="singleSelect:true,collapsible:true">
					<thead>
						<tr>
							<th data-options="field:'ck',width:5,halign:'center',checkbox:true">Item ID</th>
							<th data-options="field:'MAXACCEPT',width:50,halign:'center',hidden:true">序列</th>
							<th data-options="field:'CUST_NAME',width:100,align:'right',halign:'center'">客户名称</th>
							<th data-options="field:'PHONE',width:150,align:'right',halign:'center'">常用电话</th>
							<th data-options="field:'ADRESS',width:200,halign:'center'">地址</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- 手动查询界面 -->
<div class="easyui-window" title="手动查询" id="add_self_window" style="height:400px;width:600px;" data-options="modal:true,collapsible:false,minimizable:false,inline:false,cls:'c2',closed:true">
		<div class="easyui-panel" style="width:100%;height:165px;padding:10px 20px;">
			<div style="margin-bottom:20px;">
				<input class="easyui-textbox" id="add_search_adress" data-options="buttonText:'查询',buttonAlign:'right',buttonIcon:'icon-search',prompt:'输入地址...'" style="width:80%;height:32px;">
				<a href="javascript:add_self();" style="margin-left:20px;">手动输入</a>
			</div>
		<form action="<%=webpath%>/PhoneInterface/addCustInfo.action" id="add_self_form" method="post">
			<div style="margin-bottom:20px;">
				<input type="hidden" id="cust_id" name="cust_id"/>
				<input type="hidden" id="into_phone" name="into_phone"/>
				<div style="margin-bottom:20px;">
					客户姓名：<input class="easyui-textbox" readonly="readonly" id="add_cust_name" name="add_cust_name" style="width:150px;" data-options="required:true">
					常用电话：<input class="easyui-textbox" readonly="readonly" id="add_cust_phone" name="add_cust_phone" style="width:150px;" data-options="required:true,validType:'mobile'">
				</div>
				<div style="margin-bottom:5px;">
					客户地址：<input class="easyui-textbox" readonly="readonly" id="add_cust_adress" name="add_cust_adress" style="width:365px;" data-options="required:true">
					<a href="javascript:save_cust_info();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="margin-left:30px;">保存记录</a>
				</div>
			</div>
		</form>
		</div>
	<div id="fix_info" class="easyui-panel" title="用户列表" style="float:left;width:100%;height:160px;padding:10px;">
		<table id="add_cust_list"></table>
	</div>
</div>


<jsp:include page="interval_order.jsp"/>


<script type="text/javascript">
	var deptType = "<%=deptType%>";
	var roleLevel = "<%=roleLevel%>";
	
	$(function(){
		if(roleLevel == "2" && deptType == "61"){
			//开启来电轮训
			interval_query_phone();
		}
		if(roleLevel == "1" && deptType == "62"){
			//开启维护工单轮训
			interval_query_order();
		}
		
		//工单类型时间
		$("#index_order_status").combobox({
			onSelect: function(param){
				if(param.CODE_ID == "2"){
					$("#fix_type_div").show();
				}else{
					$("#fix_type_div").hide();
				}
			}
		});
		
		//手动输入界面查询控件添加事件
		$("#add_search_adress").textbox({onClickButton:function(){
			var search_adress = $("#add_search_adress").textbox("getValue");
			query_cust_info_list(search_adress);
		}});
	});
	
	//定时电话
	function interval_query_phone(){
		setInterval("query_phone()",2000);
		//query_phone();
	}
	
	//定时工单
	function interval_query_order(){
		setInterval("query_order()",10000);
		//query_phone();
	}
	
	//查询来电号码
	function query_phone(){
		$.ajax({
			url : webpath + "/PhoneInterface/queryPhone.action?Math="+Math.random(),
			type : "post",
			dataType : "json",
			success : function(data) {
				var resultData = data.resultData;
				if(resultData != null){
					$("#reback_order_id").val(resultData.reback_order_id);
					$("#phone_num").text(resultData.PHONE);
					$("#conn_phone").textbox("setValue", resultData.PHONE);
					//弹窗
					$("#phone_window").window("open");
					//遮罩
					shade();
					
					//加载用户列表
					if(resultData.PHONE != undefined && resultData.PHONE != "")
						loadCustInfoList(resultData);
				}
			}
		});
	}
	
	function query_order(){
		$.ajax({
			url : webpath + "/fixOrder/queryOrder.action",
			type : "post",
			data: {"orderStatus": "22,27"},
			dataType : "json",
			success : function(data) {
				var resultCode = data.resultCode;
				var resultMsg = data.resultMsg;
				if(resultCode == "0000"){
					var resultData = data.resultData;
					interval_load(resultData);
				}
			}
		});
	}
	//加载用户列表
	function loadCustInfoList(resultData){
		$("#cust_info_list_table").datagrid({
			queryParams: {
				phone: resultData.PHONE,
			},
            border: false,  
            locale: "zh_CN",  
            striped: true,  
             sortOrder: "asc",  
             collapsible: false,  
             url: webpath+"/PhoneInterface/getCustInfoList.action",  
             columns: [[  
                 { field: 'ck', width: '5%', algin: 'center',checkbox:true },  
                 { field: 'CUST_NAME', title: '客户名称', width: '15%', align: 'center'},
                 { field: 'PHONE', title: '常用电话', width: '20%', align: 'center'},  
                 { field: 'ADRESS', title: '地址', width: '41%', align: 'center', 
                 	formatter: function(value,row,index){
                 		return "<span title='" + value + "'>" + value + "</span>";
                 	}
                 }, 
                 { field: 'MAXACCEPT', title: '解除绑定', width: '20%', align: 'center',
                 	formatter:function(value,row,index){
                 		return "<a style=\"color:red;\" href=\"javascript:removeRelase(" +value+ "," +row.PHONE+ ")\">解绑</a>";
                 	}
                 }, 
             ]],
             rownumbers: false, 
             singleSelect: true, 
             onLoadSuccess:function(data){  
				if(data.rows.length > 0){
					$('#cust_info_list_table').datagrid('selectRow',0);
				}else{
					clearCustInfo();
				}
			 },
			 onSelect: function(rowIndex, rowData){
			 	//加载用户及周边信息
			 	loadCustInfo(rowIndex, rowData);
			 	//加载工单记录列表
				loadOrderInfoList(rowIndex, rowData);
			 }
		});
	}
	//解绑号码与用户关系
	function removeRelase(maxaccept, table_phone){
		var phone_num = $("#phone_num").text();
		var normal_phone = $("#normal_phone").textbox("getValue");
		//解绑
		$.ajax({
			url : webpath + "/PhoneInterface/removeRelase.action",
			type : "post",
			dataType : "json",
			data: {"phone_num": phone_num, "normal_phone": normal_phone, "table_phone": table_phone, "maxaccept": maxaccept},
			success : function(data) {
				var resultCode = data.resultCode;
				var resultMsg = data.resultMsg;
				if(resultCode == "0000"){
					//从新加载用户列表
					var resultData = {"PHONE": $("#phone_num").text()};
					loadCustInfoList(resultData);
					
				}else{
					$.messager.alert(" ", "解绑失败，请重试！", "error");
				}
			}
		});
	}
	//加载用户及周边信息
	function loadCustInfo(rowIndex, rowData){
		var normal_phone = rowData.PHONE;
		if(normal_phone == undefined)
			normal_phone = "";
		/**加载用户信息**/
		var cust_info = [];
		cust_info.push("<input type=\"hidden\" id=\"custID\" value=\"" +rowData.MAXACCEPT+ "\"/>\n");
		cust_info.push("<div style=\"margin-bottom:10px\">\n");
		cust_info.push("	<span style=\"font-weight: bold;\">客户姓名：</span><span id=\"custName\">" +rowData.CUST_NAME+ "</span>\n");
		cust_info.push("</div>\n");
		cust_info.push("<div style=\"margin-bottom:10px\">\n");
		cust_info.push("<span style=\"font-weight: bold;\">常用电话：</span><input class=\"easyui-textbox\" id=\"normal_phone\" value=\"" +normal_phone+ "\" readonly=\"readonly\" name=\"normal_phone\" style=\"width:100px;\" data-options=\"required:true,validType:'mobile'\">\n");
		cust_info.push("	<a href=\"javascript:copy_phone();\" style=\"border-bottom: solid 1px blue;\"><span style=\"color:blue;\">将来电号码设为常用</span></a> \n");
		cust_info.push("<a style=\"border-bottom: solid 1px blue;\" href=\"javascript:change_phone();\"><span style=\"color:blue;\">手动修改常用号码</span></a>\n");
		cust_info.push("</div>\n");
		cust_info.push("<div style=\"margin-bottom:10px\">\n");
		cust_info.push("	<span style=\"font-weight: bold;\">客户地址：</span><span id=\"cust_adress\">" +rowData.ADRESS+ "</span>\n");
		cust_info.push("</div>\n");
		cust_info.push("<div style=\"margin-bottom:10px\">\n");
		cust_info.push("	<span style=\"font-weight: bold;\">缴费信息：</span><span></span>\n");
		cust_info.push("</div>\n");
		$("#cust_info").html(cust_info.join(""));
		//从新加载input解决js写出没有easyui样式
		$("#normal_phone").textbox();
		
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
						$("#up_cust_info").html(html.join(""));
					}else{
						$("#up_cust_info").html("");
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
						$("#low_cust_info").html(html.join(""));
					}else{
						$("#low_cust_info").html("");
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
						$("#left_cust_info").html(html.join(""));
					}else{
						$("#left_cust_info").html("");
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
						$("#right_cust_info").html(html.join(""));
					}else{
						$("#right_cust_info").html("");
					}
				}else{
					$("#up_cust_info").html("");
					$("#low_cust_info").html("");
					$("#left_cust_info").html("");
					$("#right_cust_info").html("");
				}
			}
		});
	}
	//将来电号码设为常用
	function copy_phone(){
		var into_phone = $("#phone_num").text();
		$("#normal_phone").textbox("setValue", into_phone);
	}
	//手动修改常用号码
	function change_phone(){
		$("#normal_phone").textbox("readonly", "");
	}
	
	//加载工单历史记录
	function loadOrderInfoList(rowIndex, rowData){
		$("#order_info_list_table").datagrid({
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
	
	//工单保存
	function saveOrder(){
		var custID = $("#custID").val();
		if(custID == "" || custID == undefined || custID == null){
			alert("没有客户信息，无法创建工单！");
			return;
		}
		
		//获取参数
		var order_status = $("#index_order_status").combobox("getValue");
		var fix_type = $("#index_fix_type").combobox("getValue");
		var order_mark = $("#index_order_mark").textbox("getValue");
		var conn_phone = $("#conn_phone").textbox("getValue");
		var cust_name = $("#custName").text();
		var cust_adress = $("#cust_adress").text();
		if(order_status == "" || order_mark == "" || conn_phone == ""){
			alert("工单填写不全，无法创建工单！");
			return;
		}
		
		var normal_phone = $("#normal_phone").textbox("getValue");
		var checkData = $("#cust_info_list_table").datagrid("getChecked");
		if(checkData.length != 1){
			$.messager.alert(' ', "必须选择一个用户信息！");
			return;
		}
		//判断客服是否修改了常用联系方式
		var normal_flag = "0";
		if(normal_phone != checkData[0].PHONE)
			normal_flag = "1";
		var add_cust_flag = "0";//新增客户信息标识，需要查询接口的都要新增，此处后续需要变成动态
		
		//工单提交，同时修改常用联系方式
		$.ajax({
			url : webpath + "/PhoneInterface/saveOrder.action",
			type : "post",
			dataType : "json",
			data: {"order_type": order_status, "fix_type": fix_type, "order_mark": order_mark, "conn_phone": conn_phone, 
					"custID": custID, "cust_name": cust_name, "cust_adress": cust_adress, "normal_phone": normal_phone, 
					"normal_flag": normal_flag, "add_cust_flag": add_cust_flag, "phone_num": $("#phone_num").text(), "reback_order_id": $("#reback_order_id").val()}, 
			success : function(data) {
				var resultCode = data.resultCode;
				var resultMst = data.resultMsg;
				if(resultCode == "0000"){
					$("#phone_window").window("close");
					$.messager.alert(' ', "工单创建成功！", 'info');
					//清空选项
					$("#index_order_status").combobox("setValue", "");
					$("#index_fix_type").combobox("setValue", "");
					$("#index_order_mark").textbox("setValue", "");
				}else{
					$.messager.alert(' ', resultMst, 'error');
				}
			}
		});
	}
	//清除用户信息
	function clearCustInfo(){
		$("#cust_info").html("");
		$("#up_cust_info").html("");
		$("#low_cust_info").html("");
		$("#left_cust_info").html("");
		$("#right_cust_info").html("");
		//清空历史工单
		var checkData = $("#order_info_list_table").datagrid("getRows");
        for(var ix = checkData.length - 1; ix >= 0; ix--){
            $("#order_info_list_table").datagrid('deleteRow',ix);
        }
	}
	
	//显示手动查询弹窗
	function self_query(){
		$("#into_phone").val($("#phone_num").text());
		$("#add_self_window").window("open");
	}
	//根据地址查询客户列表模糊查询
	function query_cust_info_list(query_adress){
		if(query_adress == ""){
			$.messager.alert(" ", "请输入地址！", "error");
			return;
		}
		
		$("#add_cust_list").datagrid({
			queryParams: {
				query_adress: query_adress,
			},
            border: false,  
            locale: "zh_CN",  
            striped: true,  
            sortOrder: "asc",  
            collapsible: false,  
            url: webpath+"/PhoneInterface/getCustInfoListByAdress.action",  
            columns: [[  
            	{ field: 'ck', width: '5%', algin: 'center',checkbox:true }, 
                { field: 'MAXACCEPT', title: '序列', align: 'center',hidden:true}, 
                { field: 'CUST_NAME', title: '客户名称', width: '80', align: 'center'},
                { field: 'PHONE', title: '常用电话', width: '100', align: 'center'},  
                { field: 'ADRESS', title: '地址', width: '200', align: 'center',formatter: function(value,row,index){
     				return "<span title='" + value + "'>" + value + "</span>";
     			}}, 
                { field: 'CREATE_TIME', title: '创建时间', width: '150', align: 'center'}
            ]],
            rownumbers: false,
            singleSelect: true,
            onLoadSuccess:function(data){  
				if(data.rows.length > 0){
					$('#add_cust_list').datagrid('selectRow',0);
				}
			 },
			 onSelect: function(rowIndex, rowData){
			 	//加载用户信息
			 	addLoadCustInfo(rowIndex, rowData);
			 }
		});
	}
	
	//查询页面加载用户信息
	function addLoadCustInfo(rowIndex, rowData){
		readonly();
		$("#add_cust_name").textbox("setValue", rowData.CUST_NAME);
		$("#add_cust_phone").textbox("setValue", rowData.PHONE);
		$("#add_cust_adress").textbox("setValue", rowData.ADRESS);
		$("#cust_id").val(rowData.MAXACCEPT);
		
		
	}
	//手动输入方法
	function add_self(){
		$("#add_cust_name").textbox("setValue", "");
		$("#add_cust_phone").textbox("setValue", "");
		$("#add_cust_adress").textbox("setValue", "");
		$("#cust_id").val("");
		
		$("#add_cust_name").textbox('readonly', false);
		$("#add_cust_phone").textbox('readonly', false);
		$("#add_cust_adress").textbox('readonly', false);
	}
	//保存用户信息
	function save_cust_info(){
		$("#add_self_form").form("submit", {
			onSubmit : function() {
				return $(this).form('enableValidation').form('validate');
			},
			success : function(data) {
				data = eval("(" + data + ")");
				if(data.resultCode == "0000"){
					$("#add_self_window").window("close");
					$.messager.alert(" ", "保存成功！", "info");
					$("#cust_info_list_table").datagrid("reload");
				}else{
					$.messager.alert(" ", "保存失败，请重试！", "error");
				}
			}
		});
	}
	//手动查询页面数据只读方法
	function readonly(){
		$("#add_cust_name").textbox('readonly', true);
		$("#add_cust_phone").textbox('readonly', true);
		$("#add_cust_adress").textbox('readonly', true);
	}
	//来电弹窗关闭调用
	function closeWindow(){
		cancelShade();
	}
	
	//开启遮罩
	function shade(){
		//左侧遮罩
		var leftShade = parent.frames["menu"].document.getElementById("left_shade");
		leftShade.style.display="";
		//顶部遮罩
		var topShade = parent.frames["topFrame"].document.getElementById("top_shade");
		topShade.style.display="";
	}
	//取消遮罩
	function cancelShade(){
		//去掉左侧遮罩
		var leftShade = parent.frames["menu"].document.getElementById("left_shade");
		leftShade.style.display="none";
		
		var topShade = parent.frames["topFrame"].document.getElementById("top_shade");
		topShade.style.display="none";
	}
</script>
