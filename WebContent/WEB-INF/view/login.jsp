<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Login</title>
  </head>
  <body>
    <div class="container">
    <a href="${pageContext.request.contextPath}"><h1>&larr;</h1></a>
      <h1 class="page-title">Login</h1>
      <form action="login" method="post">
        <h5 class="label">Account</h5>
			  <input type="text" name="username" value="admin">
        <h5 class="label">Password</h5>
        <input type="password" name="pwd" value="admin" placeholder="Password" >
	      <% 
	      String errorMsg = (String) request.getAttribute("errorMsg");
	      if(errorMsg.length() > 0) {
	        out.println(String.format("<div class=\"errorMsg\">%s</div>", errorMsg));
	      }
	      %>
        <input type="submit" value="Continue"/>
      </form>
    </div>
  </body>
</html>