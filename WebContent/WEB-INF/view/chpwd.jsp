<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../meta.jsp"%>
	<title>Change Password</title>
</head>
<body>
	<div class="container">
		<h1>
			<a href="javascript: window.history.go(-1);">&larr;</a>
		</h1>
		<h1 class="page-title"></h1>
		<form action="${pageContext.request.contextPath}/user/chpwd"
			method="post">
			<h1 class="page-title">Change Password</h1>
			<h5 class="label">Old Password</h5>
			<input type="text" name="oldpwd" placeholder="Old Password">
			<h5 class="label">New Password</h5>
			<input type="password" name="pwd" placeholder="New Password">
			<h5 class="label">Confirm Password</h5>
			<input type="text" name="confirmpwd" placeholder="Confirm Password">
			<input type="submit" value="Change" />
		</form>
		<% 
      String errorMsg = (String) request.getAttribute("errorMsg");
      if(errorMsg != null && errorMsg.length() > 0) {
        out.println(String.format("<div class=\"errorMsg\">%s</div>", errorMsg));
      }
      %>
	</div>
</body>
</html>