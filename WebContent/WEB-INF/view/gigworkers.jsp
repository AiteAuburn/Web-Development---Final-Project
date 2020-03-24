<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, aite.model.ServiceModel" 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <%@ include file="../meta.jsp" %>  
		<title>Available Gig Workers</title>
	</head>
	<body>
    <div class="container">
			<nav class="nav nav-pills nav-fill">
			  <a class="nav-item nav-link active" href="javascript:void(0);">Worker Seeking</a>
			  <a class="nav-item nav-link" href="${pageContext.request.contextPath}/gigtasks">Tasks Hunting</a>
			  <a class="nav-item nav-link" href="${pageContext.request.contextPath}/settings">Settings</a>
		  </nav>
		  <div class="serviceList">
		  <%
		  ArrayList<ServiceModel> serviceList = (ArrayList<ServiceModel>) request.getAttribute("serviceList");
		  if(serviceList != null) {
			  for(int i = 0; i < serviceList.size(); i++) {
			    ServiceModel service = serviceList.get(i);
			    String output = "";
			    output += "<div class='service row'>";
		      output += "<div class='avatar'><img src='" + request.getContextPath() + "/img/avatar.png' /></div>";
	        output += "<div class='info'>";
	        output += "<div class='title'>" + service.title + "</div>";
	        output += "<div class='name'>" + service.name + "</div>";
	        output += "<div class='row'>";
	        output += "<div class='price'> $" + service.price + "</div>";
	        output += "<div class='ratings'>" + service.ratings + "</div>";
	        output += "</div>";
	        output += "</div>";
			    output += "</div>";
			    out.print(output);
			  }
			}
			%>
		  </div>
	  </div>
	</body>
</html>