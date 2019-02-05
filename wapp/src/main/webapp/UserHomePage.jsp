<%@page import="com.nixsolutions.webapp.dao.tables.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<style type="text/css">
body {
	padding: 50px;
	background: #ccc;
	position: fixed;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}
</style>
<body>
	<% User user = (User) session.getAttribute("user");%>
	<h1>
		Hello,
		<%=user.getFirstName()%></h1><br><br>
	Click <a href="http://localhost:8080/logout">here</a> to logout.
</body>
</html>