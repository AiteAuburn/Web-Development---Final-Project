package aite.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aite.service.UserService;
import aite.service.GigTaskService;

import aite.model.ApplyModel;
import aite.model.TaskModel;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/gigtasks", "/gigtasks/apply", "/gigtasks/apply_cancel", "/gigtasks/accept", "/gigtasks/reject" })
public class GigTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    private GigTaskService taskService = new GigTaskService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GigTasksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
	  int uid = userService.getUIDbyToken(accessToken);
	  request.setAttribute("uid", uid);
	  if(accessToken == null) {
	    response.sendRedirect(request.getContextPath());
	  } else {
	    if (request.getRequestURI().endsWith("/gigtasks/accept")) {
          acceptReqeust(request, response);
        } else if (request.getRequestURI().endsWith("/gigtasks/reject")) {
          rejectReqeust(request, response);
        } else {
    	  String tid = (String) request.getParameter("tid");
    	  if(tid == null || tid.length() == 0) {
    	    getTaskList(request, response);
    	  } else {
    	    getTask(tid, request, response);
    	  }
        }
	  }
	}
	protected void getTask(String taskId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      int tid = -1;
      try {
        tid = Integer.parseInt(taskId);
      }
      catch (Exception e)
      {
        
      }
      TaskModel task = taskService.getTask(accessToken, tid);
      if(task == null) {
        request.getRequestDispatcher("/WEB-INF/view/page404.jsp").forward(request, response);
      } else {
        request.setAttribute("task", task);
        int uid = (int) request.getAttribute("uid");
        if( uid == task.uid) {
          ArrayList<ApplyModel> aList = taskService.getApplyList(accessToken, tid);
          request.setAttribute("applyList", aList);
        }
        request.getRequestDispatcher("/WEB-INF/view/task_detail.jsp").forward(request, response);
      }
    }
	protected void getTaskList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ArrayList<TaskModel> tList = taskService.getTaskList();
      request.setAttribute("taskList", tList);
      request.getRequestDispatcher("/WEB-INF/view/gigtasks.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  response.setHeader("Cache-Control","no-cache"); 
      response.setHeader("Pragma","no-cache"); 
      response.setDateHeader ("Expires", -1); 
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      int uid = userService.getUIDbyToken(accessToken);
      request.setAttribute("uid", uid);
	  if (request.getRequestURI().endsWith("/apply")) {
        doApply(request, response);
      } else if (request.getRequestURI().endsWith("/apply_cancel")) {
        doCancel(request, response);
      } else {
        doGet(request, response);
      }
	}
    private void doApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String tid = request.getParameter("tid");
      String price = request.getParameter("price");
      int errorCode = taskService.applyTask(accessToken, tid, price);
      output = String.format("POST: /gigtasks/apply [%d]", errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid));
      } else {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s&errorCode=%d", request.getContextPath(), tid, errorCode));
      }
    }
	private void doCancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String tid = request.getParameter("tid");
      int errorCode = taskService.cancelOffer(accessToken,tid);
      output = String.format("POST: /gigtasks/apply_cancel [%d]", errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid));
      } else {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s&errorCode=%d", request.getContextPath(), tid, errorCode));
      }
    }
    private void acceptReqeust(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String tid = request.getParameter("tid");
      String aid = request.getParameter("aid");
      int errorCode = taskService.acceptOffer(accessToken, aid);
      output = String.format("GET: /gigtasks/accept?tid=%s&aid=%s [%d]", tid, aid, errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid));
      } else {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s&errorCode=%d", request.getContextPath(), tid, errorCode));
      }
    }
    
    private void rejectReqeust(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String tid = request.getParameter("tid");
      String aid = request.getParameter("aid");
      int errorCode = taskService.rejectOffer(accessToken, aid);
      output = String.format("GET: /gigtasks/accept?tid=%s&aid=%s [%d]", tid, aid, errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid));
      } else {
        response.sendRedirect(String.format("%s/gigtasks?tid=%s&errorCode=%d", request.getContextPath(), tid, errorCode));
      }
    }
}
