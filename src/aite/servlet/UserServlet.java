package aite.servlet;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aite.service.UserService;
import aite.service.GigTaskService;
import aite.service.GigWorkerService;

import aite.model.ServiceModel;
import aite.model.TaskModel;

import aite.service.ERRORCODE;
/**
 * Servlet implementation class UserServlet
 */
@WebServlet({"/login", "/register", "/settings", "/user/chpwd", "/user/service", "/user/tasks", "/user/newTask", "/logout"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    private GigWorkerService workerService = new GigWorkerService();
    private GigTaskService taskService = new GigTaskService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      int uid = userService.getUIDbyToken(accessToken);
      if (request.getRequestURI().endsWith("/login")) {
        getLogin(request, response);
      } else if (request.getRequestURI().endsWith("/register")) {
        getRegister(request, response);
      } else if (request.getRequestURI().endsWith("/settings") && uid > 0) {
        getSettings(request, response);
      } else if (request.getRequestURI().endsWith("/user/tasks") && uid > 0) {
        getMyTask(request, response);
      } else if (request.getRequestURI().endsWith("/user/newTask") && uid > 0) {
        getNewTask(request, response);
      } else if (request.getRequestURI().endsWith("/user/service") && uid > 0) {
        getService(request, response);
      } else if (request.getRequestURI().endsWith("/user/chpwd") && uid > 0) {
        getChpwd(request, response);
      } else if (request.getRequestURI().endsWith("/logout")) {
        doLogout(request, response);
      } else {
        response.sendRedirect(request.getContextPath());
      }
	}
	protected void getLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  System.out.println("GET: /login");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
	protected void getRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GET: /register");
	  request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
    }
	protected void getSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GET: /settings");
      request.getRequestDispatcher("/WEB-INF/view/settings.jsp").forward(request, response);
    }
	protected void getChpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GET: /chpwd");
      request.getRequestDispatcher("/WEB-INF/view/chpwd.jsp").forward(request, response);
	}
	protected void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GET: /logout");
      request.getSession().invalidate();
      response.sendRedirect(request.getContextPath());
    }
	protected void getNewTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GET: /user/newTask");
      request.getRequestDispatcher("/WEB-INF/view/new_task.jsp").forward(request, response);
    }
	protected void getService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
	  ServiceModel n = userService.getMyService(accessToken);
      request.setAttribute("defaultTitle", n.title);
      request.setAttribute("defaultPrice", String.valueOf(n.price));
      request.setAttribute("defaultDescription", n.description);
      request.setAttribute("defaultEnabled", String.valueOf(n.enabled));
      request.getRequestDispatcher("/WEB-INF/view/service.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServlRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().endsWith("/login")) {
		  doLogin(request, response);
		} else if (request.getRequestURI().endsWith("/register")) {
          doRegister(request, response);
        } else if (request.getRequestURI().endsWith("/user/service")) {
          editService(request, response);
        } else if (request.getRequestURI().endsWith("/user/newTask")) {
          newTask(request, response);
        } else if (request.getRequestURI().endsWith("/user/chpwd")) {
          changePwd(request, response);
		} else {
		  response.sendRedirect(request.getContextPath());
		}
	}

    private void getMyTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GET: /user/tasks");
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      ArrayList<TaskModel> tList = userService.getMyTaskList(accessToken);
      request.setAttribute("taskList", tList);
      request.getRequestDispatcher("/WEB-INF/view/user_tasks.jsp").forward(request, response);
    }
	private void newTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String title = request.getParameter("title");
      String location = request.getParameter("location");
      String description = request.getParameter("description");
      int errorCode = taskService.newTask(accessToken, title, location, description);
      output = String.format("POST: /user/newTask [%d] | accessToken: %s", errorCode, accessToken);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        request.getRequestDispatcher("/WEB-INF/view/new_task_success.jsp").forward(request, response);
      } else {
        request.setAttribute("defaultTitle", title);
        request.setAttribute("defaultLocation", location);
        request.setAttribute("defaultDescription", description);
        request.getRequestDispatcher("/WEB-INF/view/new_task.jsp").forward(request, response);
      }
    }
	private void editService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
	  String output;
      String title = request.getParameter("title");
      String price = request.getParameter("price");
      String description = request.getParameter("description");
      String enabled = request.getParameter("enabled");
      int errorCode = workerService.editService(accessToken, title, price, description, enabled);
      output = String.format("POST: /user/service [%d] | accessToken: %s", errorCode, accessToken);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        request.getRequestDispatcher("/WEB-INF/view/service_success.jsp").forward(request, response);
      } else {
        request.setAttribute("defaultTitle", title);
        request.setAttribute("defaultPrice", price);
        request.setAttribute("defaultDescription", description);
        request.setAttribute("defaultEnabled", enabled);
        request.getRequestDispatcher("/WEB-INF/view/service.jsp").forward(request, response);
      }
    }
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String output;
      String username = request.getParameter("username");
      String pwd = request.getParameter("pwd");
      int errorCode = userService.login(username, pwd);
      output = String.format("POST: /login [%d] | username: %s, password: %s", errorCode, username, pwd);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        request.getSession().setAttribute("accessToken","123");
        response.sendRedirect(request.getContextPath() + "/gigworkers");
      } else {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      }
	}
	private void changePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accessToken = (String) request.getSession().getAttribute("accessToken"); 
      String output;
      String oldpwd = request.getParameter("oldpwd");
      String pwd = request.getParameter("pwd");
      String confirmpwd = request.getParameter("confirmpwd");
      int errorCode = userService.changePassword(accessToken, oldpwd, pwd, confirmpwd);
      output = String.format("POST: /user/chpwd [%d]", errorCode);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        request.getRequestDispatcher("/WEB-INF/view/chpwd_success.jsp").forward(request, response);
      } else {
        request.getRequestDispatcher("/WEB-INF/view/chpwd.jsp").forward(request, response);
      }
    }
	private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String output;
      String username = request.getParameter("username");
      String pwd = request.getParameter("pwd");
      String fname = request.getParameter("fname");
      String lname = request.getParameter("lname");
      int errorCode = userService.register(username, pwd, fname, lname);
      output = String.format("POST: /register [%d] | username: %s, password: %s", errorCode, username, pwd);
      System.out.println(output);
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      if(errorCode == 0) {
        doLogin(request, response);
      } else {
        request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      }
    }

}
