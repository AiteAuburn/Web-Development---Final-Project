package aite.servlet;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aite.model.WorkerModel;
import aite.service.ERRORCODE;
import aite.service.GigWorkerService;
import aite.service.UserService;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/gigworkers", "/gigworkers/request", "/gigworkers/request_cancel"})
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
      if(accessToken == null) {
        response.sendRedirect(request.getContextPath());
      } else {
        String sid = (String) request.getParameter("sid");
        if(sid == null || sid.length() == 0) {
          getServiceList(request, response);
        } else {
          getService(sid, request, response);
        }
      }
		
	}
	protected void getService(String serviceId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int sid = -1;
      try {
        sid = Integer.parseInt(serviceId);
      }
      catch (Exception e)
      {
        
      }
      WorkerModel service = workerService.getWorker(sid);
      if(service == null) {
        request.getRequestDispatcher("/WEB-INF/view/page404.jsp").forward(request, response);
      } else {
        request.setAttribute("service", service);
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
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      int uid = userService.getUIDbyToken(accessToken);
      request.setAttribute("uid", uid);
      if (request.getRequestURI().endsWith("/request")) {
        doApply(request, response);
      } else if (request.getRequestURI().endsWith("/request_cancel")) {
        doCancel(request, response);
      } else {
        doGet(request, response);
      }
	}
	private void doApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String sid = request.getParameter("sid");
      int errorCode = workerService.requestWorker(accessToken, sid);
      output = String.format("POST: /gigworkers/request [%d]", errorCode);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid));
      } else {
        getService(sid, request, response);
      }
    }
    private void doCancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String sid = request.getParameter("sid");
      int errorCode = workerService.cancelRequest(accessToken, sid);
      output = String.format("POST: /gigworkers/request_cancel [%d]", errorCode);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        response.sendRedirect(String.format("%s/gigworkers?sid=%s", request.getContextPath(), sid));
      } else {
        getService(sid, request, response);
      }
    }

}
