<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="aite.model.TaskModel"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../meta.jsp"%>
		<title>Available Gig Tasks</title>
	</head>
	<body>
		<div class="container">
			<nav class="nav nav-pills nav-fill">
				<a class="nav-item nav-link"
					href="${pageContext.request.contextPath}/gigworkers">Worker
					Seeking</a> <a class="nav-item nav-link active"
					href="javascript:void(0);">Tasks Hunting</a> <a
					class="nav-item nav-link"
					href="${pageContext.request.contextPath}/settings">Settings</a>
			</nav>
			<div class="taskList">
				<%
				  ArrayList<TaskModel> taskList = (ArrayList<TaskModel>) request.getAttribute("taskList");
				  if (taskList != null) {
				    for (TaskModel task : taskList) {
				      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				      Date date = formatter.parse(task.createTime);
				      String time = new SimpleDateFormat("MMM dd, yyyy").format(date);
				      String output = "";
				      output += "<div class='task'>";
				      output += "<div class='title'><a href='" + request.getContextPath() + "/gigtasks?tid=" + task.tid + "'>"
				          + task.title + "</a></div>";
				      output += "<div class='name'>" + task.name + "</div>";
				      output += "<div class='time'>" + time + "</div>";
				      output += "</div>";
				      out.print(output);
				    }
				  }
				%>
			</div>
		</div>
	</body>
</html>