package aite.servlet;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aite.model.ServiceModel;
import aite.model.TaskModel;
import aite.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/gigworkers", "/gigworkers/*"})
public class GigWorkersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
       
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
      if(accessToken == null) {
        response.sendRedirect(request.getContextPath());
      } else {
        String serviceId = (String) request.getParameter("sid");
        int sid = -1;
        try {
          sid = Integer.parseInt(serviceId);
        }
        catch (Exception e)
        {
          
        }
        if(sid == -1) {
          getServiceList(request, response);
        } else {
          getService(sid, request, response);
        }
      }
		
	}
	protected void getService(int sid, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ServiceModel service = userService.getService(sid);
      if(service == null) {
        request.getRequestDispatcher("WEB-INF/view/page404.jsp").forward(request, response);
      } else {
        request.setAttribute("service", service);
        request.getRequestDispatcher("WEB-INF/view/worker_detail.jsp").forward(request, response);
      }
    }
	protected void getServiceList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  ArrayList<ServiceModel> sList = userService.getServiceList();
	  request.setAttribute("serviceList", sList);
      request.getRequestDispatcher("WEB-INF/view/gigworkers.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
