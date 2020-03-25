<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, java.util.Date, java.text.DateFormat, java.text.SimpleDateFormat, aite.model.TaskModel" 
    
%>
<%
	TaskModel task = (TaskModel) request.getAttribute("task");
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	Date date = formatter.parse(task.createTime);
	String time = new SimpleDateFormat("MMM dd, yyyy").format(date);
	String errorMsg = (String) request.getAttribute("errorMsg");
	String action = request.getContextPath() + "/gigtasks/apply";
	System.out.println("taskStatus: " + task.applyStatus);
	if( task.applyStatus != null && task.applyStatus.equalsIgnoreCase("o")){
	  action = request.getContextPath() + "/gigtasks/apply_cancel";
	}
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
      <a href="${pageContext.request.contextPath}/gigtasks"><h1>&larr;</h1></a>
      </div>
      <div class="task_detail">
        <div class="header">
          <h2 class="title">Demand Request Detail</h2>
	        <div class="info">
	          <h6 class="time">Posted on <%= time %></h6>
	          <h6 class="location"><%= task.location %></h6>
	        </div>
        </div>
        
        <div class="description">
          <h3>Title</h3>
          <p><%= task.title %></p>
	        <h3>Brief Description</h3>
	        <p><%= task.description %></p>
        </div>
        
        <form action="<%= action %>" method="post">
	        <h1 class="page-title">Apply</h1>
          <input type="hidden" name="tid" value="<%= task.tid%>">
	        <h5 class="label">Price Quote</h5>
	        <% 
	        if( task.applyStatus != null && task.applyStatus.equalsIgnoreCase("o")){
	          out.println("You have applied this with price quote of $" + task.quote);
	          out.println("<input type=\"hidden\" name=\"action\" value=\"cancel\">");
	          out.println("<input type=\"submit\" value=\"Cancel My Offer\"/>");
	        } else {
	          out.println("<input type=\"text\" name=\"price\" placeholder=\"Give a Quote\">");
	          out.println("<input type=\"hidden\" name=\"action\" value=\"apply\">");
	          out.println("<input type=\"submit\" value=\"Send Offer\"/>");
	        }
	        %>
          <% 
          if(errorMsg != null && errorMsg.length() > 0) {
            out.println(String.format("<div class=\"errorMsg\">%s</div>", errorMsg));
          }
          %>
        </form>
      </div>
    </div>
  </body>
</html>