<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, java.util.Date, java.text.DateFormat, java.text.SimpleDateFormat, aite.model.OrderModel" 
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Announcement</title>
  </head>
  <body>
    <div class="container">
      <h1><a href="${pageContext.request.contextPath}/settings">&larr;</a></h1>
      <h1 class="page-title">Announcement</h1>
      <c:import var="announcementInfo" url="../announcement.xml"/>
      <c:import url="../announcement.xsl" var="xslt"/>
      <x:transform xml="${announcementInfo}" xslt="${xslt}"/>
    </div>
  </body>
</html>