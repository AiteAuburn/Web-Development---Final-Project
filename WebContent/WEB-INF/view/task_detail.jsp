<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="aite.model.TaskModel"%>
<%@ page import="aite.model.ApplyModel"%>
<%@ page import="aite.service.ERRORCODE"%>
<%
	TaskModel task = (TaskModel) request.getAttribute("task");
  int uid = (int) request.getAttribute("uid");
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	Date date = formatter.parse(task.createTime);
	String time = new SimpleDateFormat("MMM dd, yyyy").format(date);
	String erCode = request.getParameter("errorCode");
	int errorCode = 0;
	if(erCode != null){
	  errorCode = Integer.parseInt(erCode);
	}
	String errorMsg = ERRORCODE.getMsg(errorCode);
	String action = request.getContextPath() + "/gigtasks/apply";
	if( task.applyStatus != null && task.applyStatus.equalsIgnoreCase("o")){
	  action = request.getContextPath() + "/gigtasks/apply_cancel";
	}
%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Demand Request Detail</title>
  </head>
  <body>
    <div class="container">
      <div class="row">
      <a href="javascript: window.history.go(-1);"><h1>&larr;</h1></a>
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
	        <pre><%= task.description %></pre>
        </div>
        <% if ( uid != task.uid) { %>
        <form action="<%= action %>" method="post">
	        <h1 class="page-title">Apply</h1>
          <input type="hidden" name="tid" value="<%= task.tid%>">
	        <h5 class="label">Price Quote</h5>
	        <% if( task.applyStatus != null && task.applyStatus.equalsIgnoreCase("o")){ %>
	          <p>You have applied this with price quote of $ <%= task.quote %></p>
	          <input type="hidden" name="action" value="cancel">
	          <input type="submit" value="Cancel My Offer"/>
	        <% } else { %>
	          <input type="text" name="price" placeholder="Give a Quote">
	          <input type="hidden" name="action" value="apply">
	          <input type="submit" value="Send Offer"/>
	        <% } %>
	        <% if (errorMsg != null && errorMsg.length() > 0) { %>
            <div class="errorMsg"><%=errorMsg%></div>
          <% } %>
        </form>
        
        <% } else { %>
          <h1 class="page-title">ApplyList</h1>
          <%
			      ArrayList<ApplyModel> applyList = (ArrayList<ApplyModel>) request.getAttribute("applyList");
			      if(applyList != null) {
			        for(int i = 0; i < applyList.size(); i++) {
			          ApplyModel apply = applyList.get(i);
			          Date applyDate = formatter.parse(apply.createTime);
			          String applyTime = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(applyDate);
			          String output = "";
			          output += "<div class='apply row'>";
			          output += "<div class='avatar'><img src='" + request.getContextPath() + "/img/avatar.png' /></div>";
			          output += "<div class='info'>";
			          output += "<div class='title'>" + apply.name + "</div>";
			          output += "<div class='time'>" + applyTime + "</div>";
		            output += "<div class='quote'>$" + apply.quote + "</div>";
			          output += "</div>";
	              output += "<div class='decision row'>";
	              output += "<button class=\"btn accept full-width\" onclick=\"window.location.href='" + request.getContextPath() +"/gigtasks/accept?tid=" + task.tid +"&aid=" + apply.aid + "'\">Accept</button>";
	              output += "<button class=\"btn decline full-width\" onclick=\"window.location.href='" + request.getContextPath() +"/gigtasks/reject?tid=" + task.tid +"&aid=" + apply.aid + "'\">Cancel</button>";
	              output += "</div>";
			          output += "</div>";
			          out.print(output);
			        }
			      }
			    %>
          <% if (errorMsg != null && errorMsg.length() > 0) { %>
            <div class="errorMsg"><%=errorMsg%></div>
          <% } %>
        <% } %>
      </div>
    </div>
  </body>
</html>