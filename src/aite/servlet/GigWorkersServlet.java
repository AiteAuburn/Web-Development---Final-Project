package aite.servlet;

import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aite.model.RequestModel;
import aite.model.WorkerModel;
import aite.service.GigWorkerService;
import aite.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/gigworkers", "/gigworkers/request", "/gigworkers/request_cancel", "/gigworkers/accept",
    "/gigworkers/reject" })
public class GigWorkersServlet extends AiteServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public GigWorkersServlet() {
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
      if (request.getRequestURI().endsWith("/gigworkers/accept")) {
        acceptReqeust(request, response, uid);
      } else if (request.getRequestURI().endsWith("/gigworkers/reject")) {
        rejectReqeust(request, response, uid);
      } else {
        String sid = (String) request.getParameter("sid");
        if (sid == null || sid.length() == 0) {
          getServiceList(request, response);
        } else {
          getService(sid, request, response, uid);
        }
      }
    } else {
      response.sendRedirect(request.getContextPath());
    }

  }

  protected void getService(String serviceId, HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    int sid = -1;
    try {
      sid = Integer.parseInt(serviceId);
    } catch (Exception e) {
    }
    WorkerModel service = workerService.getWorker(uid, sid);
    String path = JSP_ERROR;
    if (service != null) {
      request.setAttribute("service", service);
      if (uid == service.uid) {
        ArrayList<RequestModel> rList = workerService.getRequestList(uid, sid);
        request.setAttribute("requestList", rList);
      }
      path = JSP_WORKER_DETAIL;
    }
    request.getRequestDispatcher(path).forward(request, response);

  }

  protected void getServiceList(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    ArrayList<WorkerModel> sList = workerService.getWorkerList();
    request.setAttribute("serviceList", sList);
    request.getRequestDispatcher(JSP_GIG_WORKERS).forward(request, response);
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
    if (uid > 0) {
      request.setAttribute("uid", uid);
      if (request.getRequestURI().endsWith("/request")) {
        doApply(request, response, uid);
      } else if (request.getRequestURI().endsWith("/request_cancel")) {
        doCancel(request, response, uid);
      }
    } else {
      doGet(request, response);
    }
  }

  private void doApply(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    String sid = request.getParameter("sid");
    String location = request.getParameter("location");
    String description = request.getParameter("description");
    int errorCode = workerService.requestWorker(uid, sid, location, description);
    String output = String.format("POST: /gigworkers/request [%d]", errorCode);
    String path = String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void doCancel(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    String sid = request.getParameter("sid");
    int errorCode = workerService.cancelRequest(uid, sid);
    String output = String.format("POST: /gigworkers/request_cancel [%d]", errorCode);
    String path = String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void acceptReqeust(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    String sid = request.getParameter("sid");
    String rid = request.getParameter("rid");
    int errorCode = workerService.acceptRequest(uid, rid);
    String output = String.format("GET: /gigworkers/accept?sid=%s&rid=%s [%d]", sid, rid, errorCode);
    String path = String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void rejectReqeust(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    String sid = request.getParameter("sid");
    String rid = request.getParameter("rid");
    int errorCode = workerService.rejectRequest(uid, rid);
    String output = String.format("GET: /gigworkers/reject?sid=%s&rid=%s [%d]", sid, rid, errorCode);
    String path = String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }
}
