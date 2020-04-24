<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="aite.model.CommentModel"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../meta.jsp"%>
		<title>Reviews</title>
	</head>
	<body>
		<div class="container">
			<a href="javascript: window.history.go(-1);"><h1>&larr;</h1></a>
			<h1 class="page-title">Reviews</h1>
			<div class="ordereList">
				<%
				  ArrayList<CommentModel> commentList = (ArrayList<CommentModel>) request.getAttribute("commentList");
				  if (commentList != null) {
				    for (int i = 0; i < commentList.size(); i++) {
				      CommentModel comment = commentList.get(i);
				      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				      Date date = formatter.parse(comment.createTime);
				      String time = new SimpleDateFormat("HH:mm MMM dd, yyyy").format(date);
				%>
				<div class="review">
					<div class="header row">
						<div class="avatar">
							<img src='${pageContext.request.contextPath}/img/avatar.png' />
						</div>
						<div class="info">
							<div class="name"><%=comment.reviewer%></div>
							<div class="ratings">
								<%
								  for (int j = 0; j < 5; j++) {
								        if (j < comment.ratings)
								          out.println("<span class=\"star\">&#x2605</span>");
								        else
								          out.println("<span class=\"star\">&#x2606</span>");
								  }
								%>
							</div>
							<div class="time"><%=time%></div>
						</div>
					</div>
					<div class="comment"><%=comment.comment%></div>
				</div>
				<% } } %>
			</div>
		</div>
	</body>
</html>