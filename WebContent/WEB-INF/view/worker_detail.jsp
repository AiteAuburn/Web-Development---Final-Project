<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, java.util.Date, java.text.DateFormat, java.text.SimpleDateFormat,aite.model.WorkerModel" 
    
%>
<%
  int uid = (int) request.getAttribute("uid");
WorkerModel service = (WorkerModel) request.getAttribute("service");
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Register</title>
  </head>
  <body>
    <div class="container">
      <div class="row">
      <a href="javascript: window.history.go(-1);"><h1>&larr;</h1></a>
      </div>
      <div class="worker_detail">
        <div class="header row">
          <div class="avatar">
            <img src='${pageContext.request.contextPath}/img/avatar.png' />
          </div>
          <div class="info">
            <div class="name"><%= service.name %></div>
            <div class="ratings"><span class="star">&#x2605</span>4.0</div>
          </div>
        </div>
        <div class="description">
	        <h3 class="title"><%= service.title %></h3>
	        <p><%= service.description %></p>
        </div>
        <div class="ratings row">
          <h3 class="title">Ratings</h3>
          <h3 class="star">&#x2605</h3>
          <h3 class="score">4.9 &rarr;</h3>
        </div>
        <% if ( uid != service.uid) { %>
	        <form action="${pageContext.request.contextPath}/gigworkers/request" method="post">
	          <input type="submit" value="Send Request ($<%= service.price %> / One Time)"/>
	        </form>
        <% } else { %>
          <h1 class="page-title">Request List</h1>
        <% } %>
      </div>
    </div>
  </body>
</html>