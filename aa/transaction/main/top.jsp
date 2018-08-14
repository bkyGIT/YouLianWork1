<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.yl.common.user.pojo.User"%>
<%
	String webpath = request.getContextPath();
	User user = (User) session.getAttribute("userInfo");
	String userame = user.getUserName();
	String seatID = user.getMaxaccept();
	String roleLevel = user.getRoleLevel();
	String deptType = user.getDeptType();
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头部</title>
<jsp:include page="/common/jsp/commonHeader.jsp" flush="true" />
<script type="text/javascript">
	var webpath = "<%=webpath%>";
	var roleLevel = "<%=roleLevel%>";
	var deptType = "<%=deptType%>";
	var seatID = "<%=seatID%>";
	var closeFlag = true;
	
	$(function(){
		if(roleLevel == "2" && deptType == "61"){
			//获取话机在线状态
			interval_query_online();
			//获取话机忙闲状态
			loadPhoneStatus();
		}
	});
	
	//轮询查询话机在线状态
	function interval_query_online(){
		loadOnlineStatus();
		setInterval("loadOnlineStatus()",2000);
	}
	
	//退出系统
	function cancel(){
		 //开启遮罩
		 shade();
		 parent.frames["main"].$.messager.confirm('确认','退出系统将自动签退！是否继续退出？',function(r){    
		    if (r){
		    	var objShell = new ActiveXObject("wscript.shell");
				objShell.Run("taskkill /f /t /im EnJiaPhone.exe",0);
				objShell = null;
				
		        $.ajax({
					url : webpath + "/login/cancel.action",
					type : "post",
					dataType : "json",
					success : function(data) {
						//软电话退出
						 $.ajax({
							url : webpath + "/PhoneInterface/logoutPhone.action",
							type : "post",
							dataType : "json",
							data: {"seatID": seatID},
							success : function(data) {
								if(data.resultCode != "0000"){
									alert("呼叫软件退出失败！请手动退出！");
								}else{
									closeFlag = false;
									if($.browser.mozilla){
										parent.window.location.assign(webpath+"/login.jsp");
									}else{
										parent.window.location.href(webpath+"/login.jsp");
									}
								}
							}
						});
					}
				});
		    }else{
		    	cancelShade();
		    }  
		}); 
	
		
		/* var dd = parent.frames["main"].document.getElementById("cancel_dialog");
		dd.parentElement.style.display=""; */
		
		/* $.messager.alert(' ', "确认退出系统？", 'info', function(){
			$.ajax({
				url : webpath + "/login/cancel.action",
				type : "post",
				dataType : "json",
				data : {
					"ids" : ids, "roleID": roleID
				},
				success : function(data) { 
					$.messager.alert(' ', data[0].resultMsg);
				}
			});
		}); */
	}
	//开启遮罩
	function shade(){
		//左侧遮罩
		var leftShade = parent.frames["menu"].document.getElementById("left_shade");
		leftShade.style.display="";
		//顶部遮罩
		$("#top_shade").show();
	}
	//取消遮罩
	function cancelShade(){
		//去掉左侧遮罩
		var leftShade = parent.frames["menu"].document.getElementById("left_shade");
		leftShade.style.display="none";
    	$("#top_shade").hide();
	}
	//签到
	function in_btn(){
		//开启遮罩
		 shade();
		 parent.frames["main"].$.messager.confirm('确认','确认签到操作？',function(r){    
		    if (r){
		    	var objShell = new ActiveXObject("wscript.shell");
				objShell.Run("file:///D:/YouLian/EnJiaPhone.exe");
				objShell = null;
		        $.ajax({
					url : webpath + "/PhoneInterface/loginPhone.action",
					type : "post",
					dataType : "json",
					data: {"seatID": seatID},
					success : function(data) { 
						if(data.resultCode != "0000"){
							parent.frames["main"].$.messager.alert(' ', data.resultMsg, 'error', function(){
								cancelShade();
							});
						}else{
							setInto();
							set_xian_btn();
							cancelShade();
						}
					}
				}); 
		    }else{
		    	cancelShade();
		    }  
		}); 
	}
	//签退
	function out_btn(){
		//开启遮罩
		 shade();
		  parent.frames["main"].$.messager.confirm('确认','确认签退操作？',function(r){    
		    if (r){
		    	var objShell = new ActiveXObject("wscript.shell");
				objShell.Run("taskkill /f /t /im EnJiaPhone.exe",0);
				objShell = null;
		        $.ajax({
					url : webpath + "/PhoneInterface/logoutPhone.action",
					type : "post",
					dataType : "json",
					data: {"seatID": seatID},
					success : function(data) {
						if(data.resultCode != "0000"){
							parent.frames["main"].$.messager.alert(' ', data.resultMsg, 'info', function(){
								cancelShade();
							});
						}else{
							parent.frames["main"].$.messager.alert(' ', data.resultMsg, 'info', function(){
								cancelShade();
								set_mang_btn();
								setOuto();
							});
						}
					}
				}); 
		    }else{
		    	cancelShade();
		    }  
		});
	}
	//获取签到签退状态
	function loadOnlineStatus(){
		$.ajax({
			url : webpath + "/PhoneInterface/loadOnlineStatus.action?Math="+ Math.random(),
			type : "post",
			dataType : "json",
			success : function(data) { 
				var status = data.resultData;
				if(data.resultCode == "0000"){
					if(status == "1"){//签到
						setInto();
						//set_xian_btn();
					}else{//签退
						setOuto();
					//	set_mang_btn();
					}
				}
			}
		}); 
	}
	//置闲确定
	function xian_btn(){
		//开启遮罩
		 shade();
		 parent.frames["main"].$.messager.confirm('确认','确认置闲操作？',function(r){    
		    if (r){
		        //置闲
		        set_xian_btn();
		    }else{
		    	cancelShade();
		    }  
		}); 
	}
	//置闲
	function set_xian_btn(){
		$.ajax({
			url : webpath + "/PhoneInterface/setSeatOnlineState.action",
			type : "post",
			dataType : "json",
			data: {"status": "1"},
			success : function(data) { 
				if(data.resultCode != "0000"){
					parent.frames["main"].$.messager.alert(' ', data.resultMsg, 'error', function(){
						cancelShade();
					});
				}else{
					cancelShade();
					//置闲样式
					setXian();
				}
			}
		}); 
	}
	//置忙确定
	function mang_btn(){
		//开启遮罩
		 shade();
		 parent.frames["main"].$.messager.confirm('确认','确认置忙操作？',function(r){    
		    if (r){
		        set_mang_btn();
		    }else{
		    	cancelShade();
		    }  
		}); 
	}
	//置闲
	function set_mang_btn(){
		$.ajax({
			url : webpath + "/PhoneInterface/setSeatOnlineState.action",
			type : "post",
			dataType : "json",
			data: {"status": "0"},
			success : function(data) { 
				if(data.resultCode != "0000"){
					parent.frames["main"].$.messager.alert(' ', data.resultMsg, 'error', function(){
						cancelShade();
						//置忙样式
					});
				}else{
					cancelShade();
					setMang();
				}
			}
		}); 
	}
	//获取话机忙闲
	function loadPhoneStatus(){
		$.ajax({
			url : webpath + "/PhoneInterface/loadSeatOnlineState.action?Math="+ Math.random(),
			type : "post",
			dataType : "json",
			success : function(data) { 
				var status = data.resultData;
				if(data.resultCode == "0000"){
					if(status == "1"){//闲
						setXian();
					}else{//忙
						setMang();
					}
				}
			}
		}); 
	}
	//签到样式修改
	function setInto(){
		//签到选中
		$("#in_btn").removeClass("in_btn");
		$("#in_btn").addClass("in_btn_ing");
		$("#in_btn_a").attr("href", "#");
		//签退释放
		$("#out_btn").removeClass("out_btn_ing");
		$("#out_btn").addClass("out_btn");
		$("#out_btn_a").attr("href", "javascript:out_btn();");
	}
	//签退样式修改
	function setOuto(){
		//签退选中
		$("#out_btn").removeClass("out_btn");
		$("#out_btn").addClass("out_btn_ing");
		$("#out_btn_a").attr("href", "#");
		//签到释放
		$("#in_btn").removeClass("in_btn_ing");
		$("#in_btn").addClass("in_btn");
		$("#in_btn_a").attr("href", "javascript:in_btn();");
	}
	
	//置闲样式修改
	function setXian(){
		//置闲选中
		$("#xian_btn").removeClass("xian_btn");
		$("#xian_btn").addClass("xian_btn_ing");
		$("#xian_btn_a").attr("href", "#");
		//置忙释放
		$("#mang_btn").removeClass("mang_btn_ing");
		$("#mang_btn").addClass("mang_btn");
		$("#mang_btn_a").attr("href", "javascript:mang_btn();");
	}
	//置忙样式修改
	function setMang(){
		//置忙选中
		$("#mang_btn").removeClass("mang_btn");
		$("#mang_btn").addClass("mang_btn_ing");
		$("#mang_btn_a").attr("href", "#");
		//置闲释放
		$("#xian_btn").removeClass("xian_btn_ing");
		$("#xian_btn").addClass("xian_btn");
		$("#xian_btn_a").attr("href", "javascript:xian_btn();");
	}
	//关闭浏览器事件
	function closeEvent() {
		if(closeFlag)
			event.returnValue="退出浏览器系统电话呼入将不再弹屏！是否继续退出？";
	} 
	
	function openWin(){
		//开启轮训
		//parent.main.query_phone();
		parent.frames["main"].document.getElementById("phone_window").parentElement.style.display="";
		var resultData = {PHONE:$("#tan_phone").val()};
		parent.frames["main"].loadCustInfoList(resultData);
		parent.frames["main"].document.getElementById("phone_num").innerHTML = $("#tan_phone").val();
		//shade();
	}
</script>

<style type="text/css">
	.in_btn {
		background: transparent url(Assets/images/phone_tip.png) no-repeat scroll 3px 4px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.in_btn:active {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll 0px 0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.in_btn_ing {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll 0px 0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.out_btn {
		background: transparent url(Assets/images/phone_tip.png) no-repeat scroll -42px 4px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.out_btn:active {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll -46px 0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.out_btn_ing {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll -46px 0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.xian_btn {
		background: transparent url(Assets/images/phone_tip.png) no-repeat scroll -92px 4px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.xian_btn:active {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll -96px -0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.xian_btn_ing {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll -96px -0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.mang_btn {
		background: transparent url(Assets/images/phone_tip.png) no-repeat scroll -143px 4px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.mang_btn:active {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll -147px -0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.mang_btn_ing {
		background: transparent url(Assets/images/phone_tip_click.png) no-repeat scroll -147px -0px;
		width: 45px;
		height: 45px;
		float: left;
	}
	.font_class{
		color:white;
		font-size:12px;
		margin-left:-22px;
	}
	td{
		width:60px;
		text-align: center;
	}
</style>
</head>

<body onbeforeunload="closeEvent();">
<div class="head clearfix">
	<div class="logo" style="color: white; font-size: 20px; padding-top: 20px; padding-left: 35px;width:165px;">
    	<span>呼叫中心管理系统</span>
    </div>
    <%if("2".equals(roleLevel) && "61".equals(deptType)) {%>
	    <div style="float: left;margin-left:20px;">
	    	<table>
	    		<tr>
	    			<td style="height:45px;"><a href="javascript:in_btn();" id="in_btn_a"><img class="in_btn" id="in_btn"/></a></td>
	    			<td style="height:45px;"><a href="javascript:out_btn();" id="out_btn_a"><img class="out_btn" id="out_btn"/></a></td>
	    			<td style="height:45px;"><a href="javascript:xian_btn();" id="xian_btn_a"><img class="xian_btn" id="xian_btn"/></a></td>
	    			<td style="height:45px;"><a href="javascript:mang_btn();" id="mang_btn_a"><img class="mang_btn" id="mang_btn"/></a></td>
	    		</tr>
	    		<tr>
	    			<td align="center"><span class="font_class">&nbsp;签到</span></td>
	    			<td align="center"><span class="font_class">&nbsp;&nbsp;签退</span></td>
	    			<td align="center"><span class="font_class">&nbsp;&nbsp;置闲</span></td>
	    			<td align="center"><span class="font_class">&nbsp;&nbsp;置忙</span></td>
	    		</tr>
	    	</table>
	    </div>
    <%}%>
    
    <input type="text" id="tan_phone"/><a href="javascript:openWin();" style="color:red;">弹窗</a>
    <div style="color: white; font-size: 12px; padding-top: 26px;padding-right:10px;float:right;">
    	<span>尊敬的&nbsp;<%=userame%>&nbsp;用户,您已登录系统，点击<a href="javascript:cancel();" style="color:red;">退出</a>系统</span>
    </div>
</div>

<!-- 遮罩层 -->
<div id="top_shade" style="width:100%;height:100%;position: absolute;top:0px;z-index:9999;background-color: rgba(234, 234, 234, 1);opacity:0.3;display:none;"></div>
</body>
</html>
