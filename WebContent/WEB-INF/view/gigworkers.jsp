<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aite.model.WorkerModel"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../meta.jsp"%>
		<title>Available Gig Workers</title>
	</head>
	<body>
		<div class="container">
			<nav class="nav nav-pills nav-fill">
				<a class="nav-item nav-link active" href="javascript:void(0);">Worker
					Seeking</a> <a class="nav-item nav-link"
					href="${pageContext.request.contextPath}/gigtasks">Tasks Hunting</a>
				<a class="nav-item nav-link"
					href="${pageContext.request.contextPath}/settings">Settings</a>
			</nav>
			<div class="serviceList">
				<%
				  ArrayList<WorkerModel> serviceList = (ArrayList<WorkerModel>) request.getAttribute("serviceList");
				  if (serviceList != null) {
				    for (WorkerModel service : serviceList) {
				      String output = "";
				      output += "<div class='service row'>";
				      output += "<div class='avatar'><img src='" + request.getContextPath() + "/img/avatar.png' /></div>";
				      output += "<div class='info'>";
				      output += "<div class='title'><a href='" + request.getContextPath() + "/gigworkers?sid=" + service.sid + "'>"
				          + service.title + "</a></div>";
				      output += "<div class='name'>" + service.name + "</div>";
				      output += "<div class='row'>";
				      output += "<div class='price'> $" + service.price + "</div>";
				      output += "<div class='ratings'><span class=\"star\">&#x2605</span>" + service.ratings + "</div>";
				      output += "</div>";
				      output += "</div>";
				      output += "</div>";
				      out.print(output);
				    }
				  }
				%>
			</div>
		</div>
	</body>
</html>