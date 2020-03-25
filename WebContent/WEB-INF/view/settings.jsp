<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <%@ include file="../meta.jsp" %>  
		<title>Settings</title>
	</head>
	<body>
    <div class="container">
			<nav class="nav nav-pills nav-fill">
			  <a class="nav-item nav-link" href="${pageContext.request.contextPath}/gigworkers">Worker Seeking</a>
			  <a class="nav-item nav-link" href="${pageContext.request.contextPath}/gigtasks">Tasks Hunting</a>
			  <a class="nav-item nav-link active" href="javascript:void(0);">Settings</a>
		  </nav>
      <ul>
        <li>
          <a href="${pageContext.request.contextPath}/user/chpwd">Change Password</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/user/newTask">New Task</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/user/service">My Service</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/user/tasks">My Tasks</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/user/orders">Order History</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/user/comments">Received Reviews</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/announcement">System Announcement</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </li>
      </ul>
	  </div>
	</body>
</html>