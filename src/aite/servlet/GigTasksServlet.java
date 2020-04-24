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
@WebServlet({ "/gigtasks", "/gigtasks/apply", "/gigtasks/apply_cancel", "/gigtasks/accept", "/gigtasks/reject" })
public class GigTasksServlet extends AiteServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public GigTasksServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserService userService = new UserService();
    String accessToken = (String) request.getSession().getAttribute("accessToken");
    int uid = userService.getUIDbyToken(accessToken);
    if (uid > 0) {
      request.setAttribute("uid", uid);
      if (request.getRequestURI().endsWith("/gigtasks/accept")) {
        acceptReqeust(request, response, uid);
      } else if (request.getRequestURI().endsWith("/gigtasks/reject")) {
        rejectReqeust(request, response, uid);
      } else {
        String tid = (String) request.getParameter("tid");
        if (tid == null || tid.length() == 0) {
          getTaskList(request, response);
        } else {
          getTask(tid, request, response, uid);
        }
      }
    } else {
      response.sendRedirect(request.getContextPath());
    }
  }

  protected void getTask(String taskId, HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    String path = JSP_ERROR;
    int tid = -1;
    try {
      tid = Integer.parseInt(taskId);
    } catch (Exception e) {
    }
    TaskModel task = taskService.getTask(uid, tid);
    if (task != null) {
      request.setAttribute("task", task);
      if (uid == task.uid) {
        ArrayList<ApplyModel> aList = taskService.getApplyList(uid, tid);
        request.setAttribute("applyList", aList);
      }
      path = JSP_TASK_DETAIL;
    }
    request.getRequestDispatcher(path).forward(request, response);
  }

  protected void getTaskList(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    ArrayList<TaskModel> tList = taskService.getTaskList();
    request.setAttribute("taskList", tList);
    request.getRequestDispatcher(JSP_GIG_TASKS).forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
    UserService userService = new UserService();
    String accessToken = (String) request.getSession().getAttribute("accessToken");
    int uid = userService.getUIDbyToken(accessToken);
    request.setAttribute("uid", uid);
    if (uid > 0) {
      if (request.getRequestURI().endsWith("/apply")) {
        doApply(request, response, uid);
      } else if (request.getRequestURI().endsWith("/apply_cancel")) {
        doCancel(request, response, uid);
      }
    } else {
      doGet(request, response);
    }
  }

  private void doApply(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    String tid = request.getParameter("tid");
    String price = request.getParameter("price");
    int errorCode = taskService.applyTask(uid, tid, price);
    String output = String.format("POST: /gigtasks/apply [%d]", errorCode);
    String path = String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void doCancel(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    String tid = request.getParameter("tid");
    int errorCode = taskService.cancelOffer(uid, tid);
    String output = String.format("POST: /gigtasks/apply_cancel [%d]", errorCode);
    String path = String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void acceptReqeust(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    String tid = request.getParameter("tid");
    String aid = request.getParameter("aid");
    int errorCode = taskService.acceptOffer(uid, aid);
    String output = String.format("GET: /gigtasks/accept?tid=%s&aid=%s [%d]", tid, aid, errorCode);
    String path = (errorCode == 0) ? String.format("%s/user/orders", request.getContextPath(), tid)
        : String.format("%s/gigtasks?tid=%s&errorCode=%d", request.getContextPath(), tid, errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void rejectReqeust(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    String tid = request.getParameter("tid");
    String aid = request.getParameter("aid");
    int errorCode = taskService.rejectOffer(uid, aid);
    String output = String.format("GET: /gigtasks/accept?tid=%s&aid=%s [%d]", tid, aid, errorCode);
    String path = String.format("%s/gigtasks?tid=%s", request.getContextPath(), tid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }
}
