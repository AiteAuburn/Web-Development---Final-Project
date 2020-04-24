<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String errorMsg = (String) request.getAttribute("errorMsg"); %>
<!DOCTYPE html>
<html>
	<head>
	  <%@ include file="../meta.jsp"%>
	  <title>Login</title>
	</head>
	<body>
		<div class="container">
			<h1>
				<a href="${pageContext.request.contextPath}">&larr;</a>
			</h1>
			<h1 class="page-title">Login</h1>
			<form action="login" method="post">
				<h5 class="label">Account</h5>
				<input type="text" name="username" value="admin">
				<h5 class="label">Password</h5>
				<input type="password" name="pwd" value="admin"
					placeholder="Password">
				<% if (errorMsg != null && errorMsg.length() > 0) { %>
	        <div class="errorMsg"><%=errorMsg%></div>
	      <% } %>
				<input type="submit" value="Continue" />
			</form>
		</div>
	</body>
</html>