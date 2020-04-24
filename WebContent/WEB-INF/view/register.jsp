<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String errorMsg = (String) request.getAttribute("errorMsg"); %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../meta.jsp"%>
		<title>Register</title>
	</head>
	<body>
		<div class="container">
			<h1>
				<a href="${pageContext.request.contextPath}">&larr;</a>
			</h1>
			<h1 class="page-title">Register</h1>
			<form action="register" method="post">
				<h5 class="label">Account</h5>
				<input type="text" name="username" placeholder="Username">
				<h5 class="label">Password</h5>
				<input type="password" name="pwd" placeholder="Password">
				<h5 class="label">First Name</h5>
				<input type="text" name="fname" placeholder="first name">
				<h5 class="label">Last Name</h5>
				<input type="text" name="lname" placeholder="last name"> <input
					type="submit" value="Register" />
			</form>
			<% if (errorMsg != null && errorMsg.length() > 0) { %>
	      <div class="errorMsg"><%=errorMsg%></div>
	    <% } %>
		</div>
	</body>
</html>