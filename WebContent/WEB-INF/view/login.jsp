<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Login</title>
  </head>
  <body>
      <form action="login" method="post">
			  <label for="account">Account:</label><br>
			  <input type="text" name="username" value="admin"><br>
			  <label for="pwd">Password:</label><br>
			  <input type="text" name="pwd" value="admin"><br><br>
        <input type="submit" value="Submit"/>
      </form>
  </body>
</html>