<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0" />
<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
<title>投诉建议查询结果</title>
<style type="text/css">
html,body {
	height: 100%;
}

#wrap {
	height: 100%;
	display: -webkit-box;
	display: -webkit-flex;
	display: flex;
	-webkit-box-orient: vertical;
	-webkit-flex-flow: column;
	flex-flow: column;
}

#header {
	text-align: center;
	background-color: #81a9c3;
	color: #fff;
	width: 100%;
}

#header h1 {
	font-size: 20px;
	height: 44px;
	line-height: 44px;
	margin: 0em;
	color: #fff;
}

#main {
	-webkit-box-flex: 1;
	-webkit-flex: 1;
	flex: 1;
	background-color: #FFFFFF
}

#main div {
	width: 100%;
	margin: 10px;
}

#main div input,select {
	width: 80%;
}

#submit {
	width: 200px;
	margin-left: 25%;
	margin-right: 25%;
}

#telId {
	margin-left: 20%;
	color: #FF0000;
}

#main table {
	width: 100%;
	margin-top: 20%;
	text-align: center;
}

.advise_title{
	background-color: #81a9c3;
	color: #fff;
	font-weight: 20px;
	font-family: 微软雅黑;
}

#footer {
	height: 30px;
	line-height: 30px;
	width: 100%;
	text-align: center;
}

#footer h5 {
	color: white;
}

.con {
	font-size: 28px;
	text-align: center;
}
</style>
</head>

<body>
	<div id="wrap">
        <div id="header">
            <h1>投诉建议查询结果</h1>
        </div>
        <div id="main">
        	<table border="1">
				<c:forEach var="advise" items="${adviseList}">
					<tr class="first">
					  <td>投诉类型：</td><td>${advise.ORDER_TYPE_NAME}</td>
					</tr>
					<tr class="first">
					  <td>客户名称：</td><td>${advise.CUST_NAME}</td>
					</tr>
					<tr class="first">
					  <td>联系电话：</td><td>${advise.PHONE}</td>
					</tr>
					<tr class="first">
					  <td>客户地址：</td><td>${advise.CUST_ADDRESS}</td>
					</tr>
					<tr class="first">
					  <c:if test="${advise.ORDER_STATUS >= 24 }">
					  	<td>回复状态：</td><td>已回复</td>
					  </c:if>
					  <c:if test="${advise.ORDER_STATUS < 24}">
					  	<td>回复状态：</td><td>未回复</td>
					  </c:if>
					</tr>
					<tr class="first">
					  <td>投诉建议内容：</td><td>${advise.ORDER_MARK}</td>
					</tr>
					<tr class="first">
					  <td>投诉建议内容：</td><td>${advise.FIX_MARK}</td>
					</tr>
					<tr class="first">
					  <td>创建时间：</td><td>${advise.CREAT_TIME}</td>
					</tr>
				</c:forEach>
			</table>
        </div>
        <div id="footer">
            <!--<h5>Copyright &copy;<span id="year"></span> </h5>-->
        </div>
    </div>
</body>
</html>
