<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Available Gig Tasks</title>
  </head>
  <body>
    <div class="container">
      <nav class="nav nav-pills nav-fill">
        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/gigworkers">Worker Seeking</a>
        <a class="nav-item nav-link active" href="javascript:void(0);">Tasks Hunting</a>
        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/settings">Settings</a>
      </nav>
      <div>
      <a href="${pageContext.request.contextPath}/logout">Logout</a>
      </div>
    </div>
  </body>
</html>