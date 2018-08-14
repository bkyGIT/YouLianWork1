<%@page import="com.yl.common.user.pojo.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String webpath = request.getContextPath();
	/* User user = (User)session.getAttribute("userInfo");
	String deptType = user.getDeptType();
	String roleLevel = user.getRoleLevel(); */
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>客服中心呼叫平台</title>

<!-- 引入公共js -->
<jsp:include page="/common/jsp/commonHeader.jsp" flush="true" />
<script type="text/javascript">
	var webpath = '<%=webpath%>';
</script>

<style>
body {
	scrollbar-base-color: #C0D586;
	scrollbar-arrow-color: #FFFFFF;
	scrollbar-shadow-color: DEEFC6;
}
</style>
</head>
<frameset rows="70,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="<%=webpath %>/transaction/main/top.jsp" name="topFrame" scrolling="no">
  <frameset cols="225,*" name="btFrame" frameborder="NO" border="0" framespacing="0">
    <frame src="<%=webpath %>/transaction/main/left.jsp" noresize name="menu" scrolling="yes">
    <frame src="<%=webpath %>/transaction/main/index.jsp?url=/transaction/test/test.jsp" class="frame_r" noresize name="main" scrolling="yes">
    <%-- <%
    	if(deptType.equals("61") && roleLevel.equals("2")){       //客服
    %>
    	<frame src="<%=webpath %>/transaction/main/index.jsp?url=/transaction/kfPerson/kfPerson.jsp" class="frame_r" noresize name="main" scrolling="yes">
    <%
    	}else if(deptType.equals("61") && roleLevel.equals("1")){ //客服管理员
    %>
    	<frame src="<%=webpath %>/transaction/main/index.jsp?url=/transaction/kfManager/kfManager.jsp" class="frame_r" noresize name="main" scrolling="yes">
    <%		
    	}else if(deptType.equals("62") && roleLevel.equals("1")){ //维护管理员
   	%>
   		<frame src="<%=webpath %>/transaction/main/index.jsp?url=/transaction/fixManager/fixManager.jsp" class="frame_r" noresize name="main" scrolling="yes">
   	<%
    	}else if(deptType.equals("62") && roleLevel.equals("2")){ //维护人员
   	%>
   		<frame src="<%=webpath %>/transaction/main/index.jsp?url=/transaction/fixPerson/fixPerson.jsp" class="frame_r" noresize name="main" scrolling="yes">
   	<%
    	}else{  //普通用户
    %>
    	<frame src="<%=webpath %>/transaction/main/index.jsp?url=/transaction/analysis/analysis.jsp" class="frame_r" noresize name="main" scrolling="yes">
    <%
    	}
    %> --%>
    
  </frameset>
</frameset>
<noframes>
<body>您的浏览器不支持框架！</body>
</noframes>
</html>
