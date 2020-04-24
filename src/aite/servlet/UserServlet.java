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
import aite.service.GigWorkerService;
import aite.service.GigOrderService;

import aite.model.WorkerModel;
import aite.model.TaskModel;
import aite.model.OrderModel;
import aite.model.CommentModel;
import aite.model.LoginModel;

import aite.service.ERRORCODE;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet({ "/announcement", "/login", "/register", "/settings", "/user/chpwd", "/user/service", "/user/tasks",
    "/user/newTask", "/user/orders", "/user/orders/review", "/user/comments", "/logout" })
public class UserServlet extends AiteServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public UserServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserService userService = new UserService();
    String accessToken = (String) request.getSession().getAttribute("accessToken");
    int uid = userService.getUIDbyToken(accessToken);
    request.setAttribute("uid", uid);
    if (request.getRequestURI().endsWith("/login")) {
      if (uid > 0) {
        response.sendRedirect(request.getContextPath() + "/gigworkers");
      } else {
        getLogin(request, response);
      }
    } else if (request.getRequestURI().endsWith("/register")) {
      getRegister(request, response);
    } else if (request.getRequestURI().endsWith("/settings") && uid > 0) {
      getSettings(request, response);
    } else if (request.getRequestURI().endsWith("/user/tasks") && uid > 0) {
      getMyTask(request, response, uid);
    } else if (request.getRequestURI().endsWith("/user/newTask") && uid > 0) {
      getNewTask(request, response);
    } else if (request.getRequestURI().endsWith("/user/service") && uid > 0) {
      getService(request, response, uid);
    } else if (request.getRequestURI().endsWith("/user/chpwd") && uid > 0) {
      getChpwd(request, response);
    } else if (request.getRequestURI().endsWith("/user/orders") && uid > 0) {
      getOrders(request, response, uid);
    } else if (request.getRequestURI().endsWith("/user/comments") && uid > 0) {
      getComments(request, response, uid);
    } else if (request.getRequestURI().endsWith("/announcement") && uid > 0) {
      getAnnouncements(request, response);
    } else if (request.getRequestURI().endsWith("/logout")) {
      doLogout(request, response);
    } else {
      response.sendRedirect(request.getContextPath());
    }
  }

  protected void getLogin(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
    System.out.println("GET: /login");
  }

  protected void getAnnouncements(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher(JSP_ANNOUNCEMENT).forward(request, response);
    System.out.println("GET: /announcement");
  }

  protected void getRegister(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher(JSP_REGISTER).forward(request, response);
    System.out.println("GET: /register");
  }

  protected void getSettings(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher(JSP_SETTINGS).forward(request, response);
    System.out.println("GET: /settings");
  }

  protected void getChpwd(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher(JSP_CHPWD).forward(request, response);
    System.out.println("GET: /chpwd");
  }

  protected void doLogout(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getSession().invalidate();
    response.sendRedirect(request.getContextPath());
    System.out.println("GET: /logout");
  }

  protected void getNewTask(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher(JSP_NEW_TASK).forward(request, response);
    System.out.println("GET: /user/newTask");
  }

  protected void getService(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    UserService userService = new UserService();
    WorkerModel n = userService.getMyService(uid);
    String path = JSP_SERVICE;
    String defaultTitle = "";
    String defaultPrice = "0";
    String defaultDescription = "";
    String defaultEnabled = String.valueOf("true");
    if (n != null) {
      defaultTitle = n.title;
      defaultPrice = String.valueOf(n.price);
      defaultDescription = n.description;
      defaultEnabled = String.valueOf(n.enabled);
    }
    request.setAttribute("defaultTitle", defaultTitle);
    request.setAttribute("defaultPrice", defaultPrice);
    request.setAttribute("defaultDescription", defaultDescription);
    request.setAttribute("defaultEnabled", defaultEnabled);
    request.getRequestDispatcher(path).forward(request, response);
  }

  protected void getOrders(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigOrderService orderService = new GigOrderService();
    String path = JSP_ERROR;
    int oid = -1;
    try {
      oid = Integer.parseInt(request.getParameter("oid"));
    } catch (Exception e) {
    }
    if (oid == -1) {
      path = JSP_ORDER;
      ArrayList<OrderModel> oList = orderService.getOrderList(uid);
      request.setAttribute("orderList", oList);
    } else {
      OrderModel order = orderService.getOrder(uid, oid);
      if (order != null) {
        path = JSP_ORDER_DETAIL;
        request.setAttribute("order", order);
      }
    }
    request.getRequestDispatcher(path).forward(request, response);
    System.out.println("GET: /user/orders");
  }

  protected void getComments(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    UserService userService = new UserService();
    String path = JSP_COMMENTS;
    ArrayList<CommentModel> cList = userService.getComments(uid);
    request.setAttribute("commentList", cList);
    request.getRequestDispatcher(path).forward(request, response);
    System.out.println("GET: /user/comments?uid=" + uid);
  }

  /**
   * @see HttpServlet#doPost(HttpServlRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserService userService = new UserService();
    String accessToken = (String) request.getSession().getAttribute("accessToken");
    int uid = userService.getUIDbyToken(accessToken);
    request.setAttribute("uid", uid);
    if (request.getRequestURI().endsWith("/login")) {
      doLogin(request, response);
    } else if (request.getRequestURI().endsWith("/register")) {
      doRegister(request, response);
    } else if (request.getRequestURI().endsWith("/user/service") && uid > 0) {
      editService(request, response, uid);
    } else if (request.getRequestURI().endsWith("/user/newTask") && uid > 0) {
      newTask(request, response, uid);
    } else if (request.getRequestURI().endsWith("/user/chpwd") && uid > 0) {
      changePwd(request, response, uid);
    } else if (request.getRequestURI().endsWith("/user/orders/review") && uid > 0) {
      submitReview(request, response, uid);
    } else {
      response.sendRedirect(request.getContextPath());
    }
  }

  private void getMyTask(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    UserService userService = new UserService();
    String path = JSP_USER_TASKS;
    ArrayList<TaskModel> tList = userService.getMyTaskList(uid);
    request.setAttribute("taskList", tList);
    request.getRequestDispatcher(path).forward(request, response);
    System.out.println("GET: /user/tasks");
  }

  private void newTask(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigTaskService taskService = new GigTaskService();
    String title = request.getParameter("title");
    String location = request.getParameter("location");
    String description = request.getParameter("description");
    int errorCode = taskService.newTask(uid, title, location, description);
    String output = String.format("POST: /user/newTask [%d] | uid: %d", errorCode, uid);
    String path = JSP_NEW_TASK_SUCCESS;
    if (errorCode != 0) {
      path = JSP_NEW_TASK;
      request.setAttribute("defaultTitle", title);
      request.setAttribute("defaultLocation", location);
      request.setAttribute("defaultDescription", description);
    }
    request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
    request.getRequestDispatcher(path).forward(request, response);
    System.out.println(output);
  }

  private void editService(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigWorkerService workerService = new GigWorkerService();
    String title = request.getParameter("title");
    String price = request.getParameter("price");
    String description = request.getParameter("description");
    String enabled = request.getParameter("enabled");
    int errorCode = workerService.editService(uid, title, price, description, enabled);
    String output = String.format("POST: /user/service [%d] | uid: %d", errorCode, uid);
    String path = JSP_SERVICE_SUCCESS;
    if (errorCode != 0) {
      path = JSP_SERVICE;
      request.setAttribute("defaultTitle", title);
      request.setAttribute("defaultPrice", price);
      request.setAttribute("defaultDescription", description);
      request.setAttribute("defaultEnabled", enabled);
    }
    request.getRequestDispatcher(path).forward(request, response);
    request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
    System.out.println(output);
  }

  private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserService userService = new UserService();
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    LoginModel login = userService.login(username, pwd);
    int errorCode = login.errorCode;
    String output = String.format("POST: /login [%d] | username: %s accessToken: %s", errorCode, username,
        login.accessToken);
    if (errorCode == 0) {
      request.getSession().setAttribute("accessToken", login.accessToken);
      response.sendRedirect(request.getContextPath() + "/gigworkers");
    } else {
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
    }
    System.out.println(output);
  }

  private void changePwd(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    UserService userService = new UserService();
    String oldpwd = request.getParameter("oldpwd");
    String pwd = request.getParameter("pwd");
    String confirmpwd = request.getParameter("confirmpwd");
    int errorCode = userService.changePassword(uid, oldpwd, pwd, confirmpwd);
    String output = String.format("POST: /user/chpwd [%d]", errorCode);
    request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
    String path = (errorCode == 0) ? JSP_CHPWD_SUCCESS : JSP_CHPWD;
    request.getRequestDispatcher(path).forward(request, response);
    System.out.println(output);
  }

  private void submitReview(HttpServletRequest request, HttpServletResponse response, int uid)
      throws ServletException, IOException {
    GigOrderService orderService = new GigOrderService();
    String oid = request.getParameter("oid");
    String ratings = request.getParameter("ratings");
    String comment = request.getParameter("comment");
    int errorCode = orderService.submitReview(uid, oid, ratings, comment);
    String output = String.format("POST: /user/orders/review [%d]", errorCode);
    String path = String.format("%s/user/orders?oid=%s", request.getContextPath(), oid);
    path += (errorCode == 0) ? "" : String.format("&errorCode=%d", errorCode);
    response.sendRedirect(path);
    System.out.println(output);
  }

  private void doRegister(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserService userService = new UserService();
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    int errorCode = userService.register(username, pwd, fname, lname);
    String output = String.format("POST: /register [%d] | username: %s, password: %s", errorCode, username, pwd);
    if (errorCode == 0) {
      doLogin(request, response);
    } else {
      request.setAttribute("errorMsg", ERRORCODE.getMsg(errorCode));
      request.getRequestDispatcher(JSP_REGISTER).forward(request, response);
    }
    System.out.println(output);
  }

}
