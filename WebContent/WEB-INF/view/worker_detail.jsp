<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, java.util.Date, java.text.DateFormat, java.text.SimpleDateFormat,aite.model.WorkerModel,aite.model.RequestModel, aite.service.ERRORCODE" 
    
%>
<%
  int uid = (int) request.getAttribute("uid");
  WorkerModel service = (WorkerModel) request.getAttribute("service");
  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
  String erCode = request.getParameter("errorCode");
  int errorCode = 0;
  if(erCode != null){
    errorCode = Integer.parseInt(erCode);
  }
  String errorMsg = ERRORCODE.getMsg(errorCode);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Worker detail</title>
  </head>
  <body>
    <div class="container">
      <div class="row">
      <a href="javascript: window.history.go(-1);"><h1>&larr;</h1></a>
      </div>
      <div class="worker_detail">
        <div class="header row">
          <div class="avatar">
            <img src='${pageContext.request.contextPath}/img/avatar.png' />
          </div>
          <div class="info">
            <div class="name"><%= service.name %></div>
            <div class="ratings"><span class="star">&#x2605</span><%= service.ratings %></div>
          </div>
        </div>
        <div class="description">
	        <h3 class="title"><%= service.title %></h3>
	        <p><%= service.description %></p>
        </div>
        <div class="ratings row">
          <h3 class="title">Ratings</h3>
          <h3 class="star">&#x2605</h3>
          <h3 class="score"><a href="${pageContext.request.contextPath}/user/comments?uid=<%=service.uid%>"><%= service.ratings %> &rarr;</a></h3>
        </div>
          <% 
          if(errorMsg != null && errorMsg.length() > 0) {
            out.println(String.format("<div class=\"errorMsg\">%s</div>", errorMsg));
          }
          %>
        <% if ( service.rid != 0 && service.requestStatus.equalsIgnoreCase("o") ) { %>
	        <form action="${pageContext.request.contextPath}/gigworkers/request_cancel" method="post">
            <h1 class="page-title">Your Request</h1>
            <h5 class="label">Price: $<%= service.price %></h5>
            <h5 class="label">Location: <%= service.requestLocation %></h5>
            <h5 class="label">Description</h5>
            <%= service.requestDescription %>
            <input type="hidden" name="sid" value="<%= service.sid%>"/>
	          <input type="submit" value="Cancel Request"/>
	        </form>
	      <% } else if ( uid != service.uid ) { %>
          <form action="${pageContext.request.contextPath}/gigworkers/request" method="post">
          <h1 class="page-title">Request Apply</h1>
            <input type="hidden" name="sid" value="<%= service.sid%>"/>
		        <h5 class="label">Location</h5>
		        <input type="text" name="location" placeholder="Your location" >
		        <h5 class="label">Description</h5>
            <textarea name="description" placeholder="Describe your request here..."></textarea>
            <input type="submit" value="Send Request ($<%= service.price %> / One Time)"/>
          </form>
        <% } else { %>
          <h1 class="page-title">Request List</h1>
          <div class="requestList">
	          <%
	            ArrayList<RequestModel> requestList = (ArrayList<RequestModel>) request.getAttribute("requestList");
	            if(requestList != null) {
	              for(int i = 0; i < requestList.size(); i++) {
	                RequestModel rqst = requestList.get(i);
	                Date requestDate = formatter.parse(rqst.createTime);
	                String applyTime = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(requestDate);
	                String output = "";
	                output += "<div class='request row'>";
	                output += "<div class='avatar'><img src='" + request.getContextPath() + "/img/avatar.png' /></div>";
	                output += "<div class='info'>";
	                output += "<div class='title'>" + rqst.name + "</div>";
	                output += "<div class='time'>" + applyTime + "</div>";
	                output += "<div class='price'>$" + rqst.price + "</div>";
	                output += "</div>";
	                output += "<div class='decision row'>";
	                output += "<button class=\"btn accept full-width\" onclick=\"window.location.href='" + request.getContextPath() +"/gigworkers/accept?sid=" + service.sid +"&rid=" + rqst.rid + "'\">Accept</button>";
	                output += "<button class=\"btn decline full-width\" onclick=\"window.location.href='" + request.getContextPath() +"/gigworkers/reject?sid=" + service.sid +"&rid=" + rqst.rid + "'\">Cancel</button>";
	                output += "</div>";
	                output += "</div>";
	                out.print(output);
	              }
	            }
	          %>
          </div>
        <% } %>
      </div>
    </div>
  </body>
</html>