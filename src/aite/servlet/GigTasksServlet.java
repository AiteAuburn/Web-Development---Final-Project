package aite.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aite.model.TaskModel;
import aite.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/gigtasks"})
public class GigTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
       
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
		// TODO Auto-generated method stub
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
	  if(accessToken == null) {
	    response.sendRedirect(request.getContextPath());
	  } else {
	    String taskId = (String) request.getParameter("tid");
	    int tid = -1;
	    try {
	      tid = Integer.parseInt(taskId);
	    }
	    catch (Exception e)
	    {
	      
	    }
	    if(tid == -1) {
	      getTaskList(request, response);
	    } else {
	      getTask(tid, request, response);
	    }
	  }
	}
	protected void getTask(int tid, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      TaskModel task = userService.getTask(tid);
      if(task == null) {
        request.getRequestDispatcher("WEB-INF/view/page404.jsp").forward(request, response);
      } else {
        request.setAttribute("task", task);
        request.getRequestDispatcher("WEB-INF/view/task_detail.jsp").forward(request, response);
      }
    }
	protected void getTaskList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ArrayList<TaskModel> tList = userService.getTaskList();
      request.setAttribute("taskList", tList);
      request.getRequestDispatcher("WEB-INF/view/gigtasks.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
