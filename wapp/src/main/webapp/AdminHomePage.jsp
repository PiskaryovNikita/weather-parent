<%@page import="java.util.List"%>
<%@page import="com.nixsolutions.webapp.dao.tables.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/library.tld" prefix="mytaglib"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminHome Page</title>
</head>
<style type="text/css">
div.infoPos {
	position: absolute;
	top: 0;
	right: 0;
	padding: 20px;
}

div.tag {
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
	<%
		User user = (User) session.getAttribute("user");
	%>
	<div class="infoPos">
		<%=user.getRole().getName() + " " + user.getLogin()%>
		(<a href="/logout">Logout</a>)
	</div>
	<div class="tag">
		<a href="/addpage">Add new user</a>
		<%List<User> requsers = (List<User>)session.getAttribute("users");%>
		<mytaglib:usertag users="<%= requsers%>" />
	</div>
</body>
</html>