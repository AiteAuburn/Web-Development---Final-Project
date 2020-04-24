<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String defaultTitle = (String) request.getAttribute("defaultTitle");
  String defaultPrice = (String) request.getAttribute("defaultPrice");
  String defaultDescription = (String) request.getAttribute("defaultDescription");
  String defaultEnabled = (String) request.getAttribute("defaultEnabled");
  String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../meta.jsp"%>
		<title>Edit Service</title>
	</head>
	<body>
		<div class="container">
			<a href="${pageContext.request.contextPath}/settings"><h1>&larr;</h1></a>
			<h1 class="page-title">Service</h1>
			<form action="${pageContext.request.contextPath}/user/service"
				method="post">
				<h5 class="label">Title</h5>
				<input type="text" name="title" value="${defaultTitle}"
					placeholder="Service title">
				<h5 class="label">Price</h5>
				<input type="text" name="price" value="${defaultPrice}"
					placeholder="Service price">
				<h5 class="label">Description</h5>
				<textarea name="description"
					placeholder="Describe your service here...">${defaultDescription}</textarea>
				<h5 class="label">Enabled</h5>
				<% if (defaultEnabled == null || defaultEnabled.equalsIgnoreCase("true") == true) { %>
				  <input type='radio' name='enabled' value='true' checked> True
				  <input type='radio' name='enabled' value='false'> False
				<% } else { %>
				  <input type='radio' name='enabled' value='true'> True
				  <input type='radio' name='enabled' value='false' checked> False
				<% } %>
				<% if (errorMsg != null && errorMsg.length() > 0) { %>
	        <div class="errorMsg"><%=errorMsg%></div>
	      <% } %>
				<input type="submit" value="Submit" />
			</form>
		</div>
	</body>
</html>