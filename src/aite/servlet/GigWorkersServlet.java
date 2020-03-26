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
@WebServlet({"/gigworkers", "/gigworkers/request", "/gigworkers/request_cancel", "/gigworkers/accept", "/gigworkers/reject"})
public class GigWorkersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    private GigWorkerService workerService = new GigWorkerService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GigWorkersServlet() {
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
      if(uid < 1) {
        response.sendRedirect(request.getContextPath());
      } else {
        if (request.getRequestURI().endsWith("/gigworkers/accept")) {
          acceptReqeust(request, response);
        } else if (request.getRequestURI().endsWith("/gigworkers/reject")) {
          rejectReqeust(request, response);
        } else {
          String sid = (String) request.getParameter("sid");
          if(sid == null || sid.length() == 0) {
            getServiceList(request, response);
          } else {
            getService(sid, request, response);
          }
        }
      }
		
	}
	protected void getService(String serviceId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      int sid = -1;
      try {
        sid = Integer.parseInt(serviceId);
      }
      catch (Exception e)
      {
        
      }
      WorkerModel service = workerService.getWorker(accessToken, sid);
      if(service == null) {
        request.getRequestDispatcher("/WEB-INF/view/page404.jsp").forward(request, response);
      } else {
        request.setAttribute("service", service);
        int uid = (int) request.getAttribute("uid");
        if( uid == service.uid ) {
          ArrayList<RequestModel> rList = workerService.getRequestList(accessToken, sid);
          request.setAttribute("requestList", rList);
        }
        request.getRequestDispatcher("/WEB-INF/view/worker_detail.jsp").forward(request, response);
      }

      
    }
	protected void getServiceList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  ArrayList<WorkerModel> sList = workerService.getWorkerList();
	  request.setAttribute("serviceList", sList);
      request.getRequestDispatcher("/WEB-INF/view/gigworkers.jsp").forward(request, response);
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
      if (request.getRequestURI().endsWith("/request") && uid > 0) {
        doApply(request, response);
      } else if (request.getRequestURI().endsWith("/request_cancel") && uid > 0) {
        doCancel(request, response);
      } else {
        doGet(request, response);
      }
	}
	private void doApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String sid = request.getParameter("sid");
      String location = request.getParameter("location");
      String description = request.getParameter("description");
      int errorCode = workerService.requestWorker(accessToken, sid, location, description);
      output = String.format("POST: /gigworkers/request [%d]", errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid));
      } else {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s&errorCode=%d", request.getContextPath(), sid, errorCode));
      }
    }
    private void doCancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String sid = request.getParameter("sid");
      int errorCode = workerService.cancelRequest(accessToken, sid);
      output = String.format("POST: /gigworkers/request_cancel [%d]", errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid));
      } else {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s&errorCode=%d", request.getContextPath(), sid, errorCode));
      }
    }
    private void acceptReqeust(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String sid = request.getParameter("sid");
      String rid = request.getParameter("rid");
      int errorCode = workerService.acceptRequest(accessToken, rid);
      output = String.format("GET: /gigworkers/accept?sid=%s&rid=%s [%d]", sid, rid, errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid));
      } else {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s&errorCode=%d", request.getContextPath(), sid, errorCode));
      }
    }
    
    private void rejectReqeust(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String sid = request.getParameter("sid");
      String rid = request.getParameter("rid");
      int errorCode = workerService.rejectRequest(accessToken, rid);
      output = String.format("GET: /gigworkers/reject?sid=%s&rid=%s [%d]", sid, rid, errorCode);
      System.out.println(output);
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid));
      } else {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s&errorCode=%d", request.getContextPath(), sid, errorCode));
      }
    }
}
