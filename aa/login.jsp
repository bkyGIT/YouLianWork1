<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>登录</title>

<jsp:include page="common/jsp/commonHeader.jsp" flush="true" />
<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		//自适应屏幕宽度
		window.onresize = function() {
			location = location
		};

		var w_height = $(window).height();
		$('.bg_img').css('height', w_height + 'px');

		var bg_wz = 1920 - $(window).width();
		$('.bg_img img').css('margin-left', '-' + bg_wz / 2 + 'px')

		$('.language .lang').click(function() {
			$(this).siblings('.lang_ctn').toggle();
		});
		
		
		//绑定账号tip
		$('#username').tooltip({
			position: 'right',
			showEvent: '',
			content: '<span style="color:white">账号不能为空！</span>',
			onShow: function(){
				$(this).tooltip('tip').css({
					backgroundColor: 'red',
					borderColor: 'red'
				});
			}
		});
		
		//绑定密码tip
		$('#password').tooltip({
			position: 'right',
			showEvent: '',
			content: '<span style="color:white">密码不能为空！</span>',
			onShow: function(){
				$(this).tooltip('tip').css({
					backgroundColor: 'red',
					borderColor: 'red'
				});
			}
		});
		
		//改变tip消失
		$("#username").change(function(){
		  $('#username').tooltip("hide");
		});
		//改变tip消失
		$("#password").change(function(){
		  $('#password').tooltip("hide");
		});
	});
	
	function loginSubmit(){
		var userName = $("#username").val();
		var password = $("#password").val();
		if(userName == ""){
			$('#username').tooltip("show");
			return;
		}
		if(password == ""){
			$('#password').tooltip("show");
			return;
		}
		
		$("#login_form").submit();
	}
	
</script>
</head>


<body>
<!--登录-->
<div class="login">
	<div class="bg_img"><img src="<%=webpath%>/transaction/main/Assets/images/login_bg.jpg"/></div>
	<div class="logo">
    	<span style=" font-size: 30px; color: rgba(101, 101, 101, 1); font-weight: bold;">客服中心呼叫系统</span>
    </div>
    <div class="login_m">
    	<form id="login_form" action="<%=webpath%>/login/userLogin.action" method="post">
    	<ul>
        	<li class="wz">用户名</li>
            <li><input id="username" name="username" type="text"/></li>
            <li class="wz">密码</li>
            <li><input id="password" name="password" type="password"/></li>
            <li class="l_btn">
            	<a href="javascript:loginSubmit();">登录</a>
            </li>
        </ul>
        <span id="msg" style="color: red;">${msg}</span>
        </form>
    </div>
</div>
<!--登录-->
</body>
</html>