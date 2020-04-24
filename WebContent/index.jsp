<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="WEB-INF/meta.jsp"%>
	<title>Homepage</title>
</head>
<body>
	<div class="container">
		<h1 class="page-title text-center">Web Development</h1>
		<button class="btn full-width"
			onclick="window.location.href='${pageContext.request.contextPath}/login'">Login</button>
		<button class="btn full-width"
			onclick="window.location.href='${pageContext.request.contextPath}/register'">Register</button>
	</div>
</body>
</html>