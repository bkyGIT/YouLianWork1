<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>
<html>
<head>
<title>My JSP 'test.jsp' starting page</title>
<!-- 引入jquery -->
<script type="text/javascript" src="<%=webpath%>/common/js/jquery-easyui-1.5.5.1/jquery.min.js"></script>
<!-- 引入easyui js -->
<script type="text/javascript" src="<%=webpath%>/common/js/jquery-easyui-1.5.5.1/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$.ajax({
		url : "http://192.168.0.100:8080/test/page/asp/json.jsp",
		type : "post",
		dataType : "json",
		success : function(data) {
			alert(data.name);
		}
	});
</script>
</head>

<body>
</body>
</html>
