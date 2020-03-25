<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, java.util.Date, java.text.DateFormat, java.text.SimpleDateFormat, aite.model.OrderModel, aite.service.ERRORCODE" 
    
%>
<%
	OrderModel order = (OrderModel) request.getAttribute("order");
  int uid = (int) request.getAttribute("uid");
  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
  Date orderDate = formatter.parse(order.createTime);
  String orderTime = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(orderDate);
  String erCode = request.getParameter("errorCode");
  int errorCode = 0;
  if(erCode != null){
    errorCode = Integer.parseInt(erCode);
  }
  String errorMsg = ERRORCODE.getMsg(errorCode);
  boolean showReviewSection = order.status.equals("o") || ( order.status.equals("ww") && uid == order.workerUID) || ( order.status.equals("wr") && uid == order.requesterID);
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>Order Detail</title>
  </head>
  <body>
    <div class="container">
      <div class="row">
      <a href="${pageContext.request.contextPath}/user/orders"><h1>&larr;</h1></a>
      </div>
      <div class="order_detail">
        <div class="header">
          <h2 class="title">Order - <%= order.title %></h2>
	        <div class="info">
	          <h6 class="time"><img src='${pageContext.request.contextPath}/img/clock.png' /><%= orderTime %></h6>
	          <h6 class="location"><img src='${pageContext.request.contextPath}/img/marker.png' /><%= order.location %></h6>
	        </div>
        </div>
        <div class="description"><%= order.description %></div>
      </div>
      <% if( showReviewSection ) { %>
       <hr />
       <form action="${pageContext.request.contextPath}/user/orders/review" method="post">
        <h4 class="reviewText">Review</h4>
        <h5 class="label">Ratings ( 0 - 5 )</h5>
        <input type="hidden" name="oid" value="<%= order.oid %>"/>
        <input type="text" name="ratings" placeholder="0 - 5" onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
        <h5 class="label">Comment</h5>
        <textarea name="comment" placeholder="Write down this experience ...">${defaultComment}</textarea>
          <% 
          if(errorMsg != null && errorMsg.length() > 0) {
            out.println(String.format("<div class=\"errorMsg\">%s</div>", errorMsg));
          }
          %>
        <input type="submit" value="Submit Review (Order Completed)" />
      </form>
      <% } else { %>
	      <% if(order.status.equals("e")){%>
	       <hr />
	        <div class="text-center">
	          <h2>Order Completed!</h2>
	        </div>
	       <hr />
	      <% } else {%>
         <hr />
          <div class="text-center">
            <h2> Waiting for his/her review</h2>
          </div>
         <hr />
	      
	      <% } %>
          <% if(order.workerReviewRatings != -1) {
          Date date = formatter.parse(order.requesterReviewTime);
          String time = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(date);%>
	          <div class="review">
	            <div class="header row">
		            <div class="avatar"><img src='${pageContext.request.contextPath}/img/avatar.png' /></div>
		            <div class="info">
			            <div class="name"><%= order.workerName %></div>
                  <div class="ratings"><span class="star">&#x2605</span> <%= order.workerReviewRatings %></div>
                  <div class="time"><%= time %></div>
		            </div>
		          </div>
              <div class="comment"><%= order.workerReview %></div>
	          </div>
          <% }%>
          <% if(order.requesterReviewRatings != -1) {
          Date date = formatter.parse(order.requesterReviewTime);
          String time = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(date);%>
            <div class="review">
              <div class="header row">
                <div class="avatar"><img src='${pageContext.request.contextPath}/img/avatar.png' /></div>
                <div class="info">
                  <div class="name"><%= order.requesterName %></div>
                  <div class="ratings"><span class="star">&#x2605</span> <%= order.requesterReviewRatings %></div>
                  <div class="time"><%= time %></div>
                </div>
              </div>
              <div class="comment"><%= order.requesterReview %></div>
            </div>
          <% }%>
        <% }%>
    </div>
  </body>
</html>