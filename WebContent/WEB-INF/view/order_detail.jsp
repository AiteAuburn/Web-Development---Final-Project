<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="aite.model.OrderModel"%>
<%@ page import="aite.service.ERRORCODE"%>
<%
  OrderModel order = (OrderModel) request.getAttribute("order");
  int uid = (int) request.getAttribute("uid");
  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
  Date orderDate = formatter.parse(order.createTime);
  String orderTime = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(orderDate);
  String erCode = request.getParameter("errorCode");
  int errorCode = 0;
  if (erCode != null) {
    errorCode = Integer.parseInt(erCode);
  }
  String errorMsg = ERRORCODE.getMsg(errorCode);
  boolean showReviewSection = order.status.equals("o") || (order.status.equals("ww") && uid == order.workerUID)
      || (order.status.equals("wr") && uid == order.requesterID);
%>
<!DOCTYPE html>
<html>
	<head>
	 <%@ include file="../meta.jsp"%>
	 <title>Order Detail</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<h1>
				  <a href="${pageContext.request.contextPath}/user/orders">&larr;</a>
				</h1>
			</div>
			<div class="order_detail">
				<div class="header">
					<h2 class="title">
						Order -
						<%=order.title%></h2>
					<div class="info">
						<h6 class="time">
							<img src='${pageContext.request.contextPath}/img/clock.png' /><%=orderTime%></h6>
						<h6 class="location">
							<img src='${pageContext.request.contextPath}/img/marker.png' /><%=order.location%></h6>
					</div>
				</div>
				<div class="description"><%=order.description%></div>
			</div>
			<% if (showReviewSection) { %>
				<hr />
				<form action="${pageContext.request.contextPath}/user/orders/review"
					method="post">
					<h4 class="reviewText">Review</h4>
					<h5 class="label">Ratings ( 0 - 5 )</h5>
					<input type="hidden" name="oid" value="<%=order.oid%>" /> <input
						type="text" name="ratings" placeholder="0 - 5"
						onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
					<h5 class="label">Comment</h5>
					<textarea name="comment" placeholder="Write down this experience ...">${defaultComment}</textarea>
		      <% if (errorMsg != null && errorMsg.length() > 0) { %>
		        <div class="errorMsg"><%=errorMsg%></div>
		      <% } %>
					<input type="submit" value="Submit Review (Order Completed)" />
				</form>
			<% } else { %>
				<% if (order.status.equals("e")) { %>
					<hr />
					<div class="text-center">
						<h2>Order Completed!</h2>
					</div>
					<hr />
					<%
					  if (order.workerReviewRatings != -1) {
					    Date date = formatter.parse(order.requesterReviewTime);
					    String time = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(date);
					%>
						<div class="review">
							<div class="header row">
								<div class="avatar">
									<img src='${pageContext.request.contextPath}/img/avatar.png' />
								</div>
								<div class="info">
									<div class="name"><%=order.workerName%></div>
									<div class="ratings">
										<%
										  for (int i = 0; i < 5; i++) {
										    if (i < order.workerReviewRatings)
										      out.println("<span class=\"star\">&#x2605</span>");
										    else
										      out.println("<span class=\"star\">&#x2606</span>");
										  }
										%>
									</div>
									<div class="time"><%=time%></div>
								</div>
							</div>
							<div class="comment"><%=order.workerReview%></div>
						</div>
				 <% } %>
					<%
					  if (order.requesterReviewRatings != -1) {
					    Date date = formatter.parse(order.requesterReviewTime);
					    String time = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(date);
					%>
					<div class="review">
						<div class="header row">
							<div class="avatar">
								<img src='${pageContext.request.contextPath}/img/avatar.png' />
							</div>
							<div class="info">
								<div class="name"><%=order.requesterName%></div>
								<div class="ratings">
									<%
									  for (int i = 0; i < 5; i++) {
									    if (i < order.requesterReviewRatings)
									       out.println("<span class=\"star\">&#x2605</span>");
									    else
									       out.println("<span class=\"star\">&#x2606</span>");
									  }
									%>
								</div>
								<div class="time"><%=time%></div>
							</div>
						</div>
						<div class="comment"><%=order.requesterReview%></div>
					</div>
					<% } %>
				<% } else { %>
				<hr />
				<div class="text-center">
					<h2>Waiting for his/her review</h2>
				</div>
				<hr />
			<% }  } %>
		</div>
	</body>
</html>