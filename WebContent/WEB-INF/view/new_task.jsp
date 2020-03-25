<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String defaultDescription = (String) request.getAttribute("defaultDescription");
String defaultLocation = (String) request.getAttribute("defaultLocation");

String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>New Task</title>
  </head>
  <body>
    <div class="container">
      <a href="${pageContext.request.contextPath}/settings"><h1>&larr;</h1></a>
      <h1 class="page-title">New Task</h1>
      <form action="${pageContext.request.contextPath}/user/newTask" method="post">
        <h5 class="label">Task Title</h5>
        <input type="text" name="title" value="${defaultTitle}" placeholder="Task title" >
        <h5 class="label">Task Location</h5>
        <input type="text" name="location" value="${defaultLocation}" placeholder="Task location" >
			  <h5 class="label">Description</h5>
			  <textarea name="description" placeholder="Describe your task here...">${defaultDescription}</textarea>
	      <% 
        if(errorMsg != null && errorMsg.length() > 0) {
          out.println(String.format("<div class=\"errorMsg\">%s</div>", errorMsg));
        }
	      %>
        <input type="submit" value="Submit" />
      </form>
    </div>
  </body>
</html>