<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, java.util.Date, java.text.DateFormat, java.text.SimpleDateFormat, aite.model.TaskModel" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="../meta.jsp" %>  
    <title>My Tasks</title>
  </head>
  <body>
    <div class="container">
      <a href="${pageContext.request.contextPath}/settings"><h1>&larr;</h1></a>
      <h1 class="page-title">My Tasks</h1>
      <div class="taskList">
      <%
      ArrayList<TaskModel> taskList = (ArrayList<TaskModel>) request.getAttribute("taskList");
      if(taskList != null) {
        for(int i = 0; i < taskList.size(); i++) {
          TaskModel task = taskList.get(i);
          DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
          Date date = formatter.parse(task.createTime);
          String time = new SimpleDateFormat("MMM dd, yyyy").format(date);
          String output = "";
          output += "<div class='task'>";
          output += "<div class='title'><a href='"+ request.getContextPath() +"/gigtasks?tid="+ task.tid +"'>" + task.title + "</a></div>";
          output += "<div class='name'>" + task.name + "</div>";
          output += "<div class='time'>" + time + "</div>";
          output += "</div>";
          out.print(output);
        }
      }
      %>
      </div>
    </div>
  </body>
</html>