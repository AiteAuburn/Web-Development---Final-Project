<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="aite.model.OrderModel"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../meta.jsp"%>
		<title>Announcement</title>
	</head>
	<body>
		<div class="container">
			<h1>
				<a href="${pageContext.request.contextPath}/settings">&larr;</a>
			</h1>
			<h1 class="page-title">Announcement</h1>
			<c:import var="announcementInfo" url="../announcement.xml" />
			<c:import url="../announcement.xsl" var="xslt" />
			<x:transform xml="${announcementInfo}" xslt="${xslt}" />
		</div>
	</body>
</html>