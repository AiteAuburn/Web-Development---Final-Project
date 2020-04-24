<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String defaultDescription = (String) request.getAttribute("defaultDescription");
  String defaultLocation = (String) request.getAttribute("defaultLocation");
  String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
	<head>
	  <%@ include file="../meta.jsp"%>
	  <title>New Task</title>
	</head>
	<body>
		<div class="container">
			<h1>
				<a href="${pageContext.request.contextPath}/settings">&larr;</a>
			</h1>
			<h1 class="page-title">New Task</h1>
			<form action="${pageContext.request.contextPath}/user/newTask"
				method="post">
				<h5 class="label">Task Title</h5>
				<input type="text" name="title" value="${defaultTitle}"
					placeholder="Task title">
				<h5 class="label">Task Location</h5>
				<input type="text" name="location" value="${defaultLocation}"
					placeholder="Task location">
				<h5 class="label">Description</h5>
				<textarea name="description" placeholder="Describe your task here...">${defaultDescription}</textarea>
				<% if (errorMsg != null && errorMsg.length() > 0) { %>
				  <div class="errorMsg"><%=errorMsg%></div>
				<% } %>
				<input type="submit" value="Submit" />
			</form>
		</div>
	</body>
</html>