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
      <a href="${pageContext.request.contextPath}/settings"><h1>&larr;</h1></a>
      <h1 class="page-title">Service</h1>
      <form action="${pageContext.request.contextPath}/user/service" method="post">
        <h5 class="label">Title</h5>
        <input type="text" name="title" placeholder="Service title" >
        <h5 class="label">Price</h5>
        <input type="text" name="price" placeholder="Service price" >
			  <h5 class="label">Description</h5>
			  <textarea name="description" placeholder="Describe your service here..."></textarea>
        <h5 class="label">Enabled</h5>
        <input type="radio" name="enabled" value="true" > True
        <input type="radio" name="enabled" value="false" > False
        <input type="submit" value="Submit" />
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