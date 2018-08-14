<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>左边导航</title>
<jsp:include page="/common/jsp/commonHeader.jsp" flush="true" />
<!--框架高度设置-->
<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		//加载菜单
		loadMenu();
	});

	function loadMenu() {
		$.ajax({
			url : webpath+"/menu/loadMenu.action",
			type : "post",
			dataType : "json",
			success : function(data) {
				var pMenu = data[0].pMenu;
				var sMenu = data[0].sMenu;
				
				var menuHtml = [];
				menuHtml.push("<ul class=\"sidenav\">");
				for(var ix=0; ix<pMenu.length; ix++){
					if(pMenu[ix].menuIndex == 1){
						menuHtml.push("<li class=\"now\">");
					}else{
						menuHtml.push("<li>");
					}
					menuHtml.push("<div class=\"nav_m\">");
					menuHtml.push("<span><a>" +pMenu[ix].menuName+ "</a> </span> <i>&nbsp;</i>");
					menuHtml.push("</div>");
					menuHtml.push("<ul class=\"erji\">");
					for(var iy=0; iy<sMenu.length; iy++){
						
						if(sMenu[iy].pid == pMenu[ix].maxaccept){
							menuHtml.push("<li><span><a href=\"" + webpath + "/transaction/main/index.jsp?url=" + sMenu[iy].menuUrl+ "\" target=\"main\">" +sMenu[iy].menuName+ "</a> </span></li>");
						}
					}
					menuHtml.push("</ul>");
					menuHtml.push("</li>");
				}
				menuHtml.push("</ul>");
				$("#left_ctn").html(menuHtml.join(""));
				
				//绑定方法
				loadBind();
			}
		});
	}
	
	function loadBind(){
		$('.sidenav li').click(function() {
			$(this).siblings('li').removeClass('now');
			$(this).addClass('now');
		});

		$('.erji li').click(function() {
			$(this).siblings('li').removeClass('now_li');
			$(this).addClass('now_li');
		});

		var main_h = $(window).height();
		$('.sidenav').css('height', main_h + 'px');
	}
</script>
<!--框架高度设置-->
</head>

<body>
	<div id="left_ctn">
		
	</div>
	<div id="left_shade" style="width:100%;height:100%;position: absolute;top:0px;z-index:9999;background-color: rgba(234, 234, 234, 1);opacity:0.3;display:none;"></div>
</body>
</html>