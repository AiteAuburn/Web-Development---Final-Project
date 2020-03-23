<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Register</title>
  </head>
  <body>
    <div class="container">
      <a href="${pageContext.request.contextPath}"><h1>&larr;</h1></a>
      <h1 class="page-title">Register</h1>
      <form action="register" method="post">
        <h5 class="label">Account</h5>
        <input type="text" name="username" placeholder="Username" >
        <h5 class="label">Password</h5>
        <input type="password" name="pwd" placeholder="Password" >
			  <h5 class="label">First Name</h5>
			  <input type="text" id="fname" name="fname" placeholder="first name" >
			  <h5 class="label">Last Name</h5>
			  <input type="text" id="lname" name="lname" placeholder="last name" >
        <input type="submit" value="Register"/>
      </form>
      <% 
      String errorMsg = (String) request.getAttribute("errorMsg");
      if(errorMsg.length() > 0) {
        out.println(errorMsg);
      }
      %>
    </div>
  </body>
</html>