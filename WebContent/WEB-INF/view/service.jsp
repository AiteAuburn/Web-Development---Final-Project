<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String defaultTitle = (String) request.getAttribute("defaultTitle");
String defaultPrice = (String) request.getAttribute("defaultPrice");
String defaultDescription = (String) request.getAttribute("defaultDescription");
String defaultEnabled = (String) request.getAttribute("defaultEnabled");
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
      <a href="${pageContext.request.contextPath}/settings"><h1>&larr;</h1></a>
      <h1 class="page-title">Service</h1>
      <form action="${pageContext.request.contextPath}/user/service" method="post">
        <h5 class="label">Title</h5>
        <input type="text" name="title" value="${defaultTitle}" placeholder="Service title" >
        <h5 class="label">Price</h5>
        <input type="text" name="price" value="${defaultPrice}" placeholder="Service price" >
			  <h5 class="label">Description</h5>
			  <textarea name="description" placeholder="Describe your service here...">${defaultDescription}</textarea>
        <h5 class="label">Enabled</h5>
        <% 
        if(defaultEnabled == null || defaultEnabled.equalsIgnoreCase("true") == true) {
          out.print("<input type='radio' name='enabled' value='true' checked> True");
          out.print("<input type='radio' name='enabled' value='false'> False");
        } else {
          out.print("<input type='radio' name='enabled' value='true'> True");
          out.print("<input type='radio' name='enabled' value='false' checked> False");
          
        }
        %>
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