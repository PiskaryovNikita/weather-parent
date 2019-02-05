<%@page import="com.nixsolutions.webapp.dao.tables.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AddUser Page</title>
</head>
<style type="text/css">
div.infoPos {
  position: absolute;
  top: 0;
  right: 0;
  padding: 20px;
}
form {
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
	<script>
		function checkPass() {
			with (document)
				getElementById('info').innerHTML = (getElementById('pass').value != getElementById('passch').value) ? 'Password again is defferent!'
						: 'OK!';
		}
	</script>
	<% User user = (User)session.getAttribute("user"); %>
	<div class="infoPos">
	<%= user.getRole().getName() + " " + user.getLogin() %>
	(<a href="/logout">Logout</a>)
	</div>
	<h1 style="text-align: center; margin-top: 10%">Add user</h1>
	<form action="/adminAdd" method="post">
		Login:<br> <input type="text" name="login" ><br>
		Password:<br> <input type="password" name="password" id="pass"><br>
		Password again:<br> <input type="password" id="passch" onchange="checkPass()"><br> <b style="color: red"
			id="info"></b><br> Email:<br> <input type="text"
			name="email"><br> First name:<br> <input
			type="text" name="firstName"><br> Last name:<br> <input
			type="text" name="lastName"><br> Birthday:<br> <input
			type="text" name="birthday"><br> Role:<br> <select
			name="role">
			<option value="User">User</option>
			<option value="Admin">Admin</option>
		</select><br> <br> <input type="submit" value="OK">
		<button onclick="window.location.href = 'https://localhost:8080/home';">Cancel</button>
	</form>
</body>
</html>