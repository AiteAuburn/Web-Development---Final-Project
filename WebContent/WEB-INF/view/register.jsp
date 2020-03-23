<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Register</title>
  </head>
  <body>
      <form action="login" method="post">
			  <label for="fname">Account:</label><br>
			  <input type="text" id="fname" name="fname" value="admin"><br>
			  <label for="lname">Password:</label><br>
			  <input type="text" id="lname" name="lname" value="admin"><br><br>
        <input type="submit" value="Submit"/>
      </form>
  </body>
</html>