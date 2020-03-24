package aite.servlet;
import aite.service.ERRORCODE;
import aite.model.ServiceModel;
import aite.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet({"/login", "/register", "/settings", "/user/service", "/logout"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
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
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
	protected void getRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
    }
	protected void getSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.getRequestDispatcher("/WEB-INF/view/settings.jsp").forward(request, response);
    }
	protected void getChpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.getRequestDispatcher("/WEB-INF/view/chpwd.jsp").forward(request, response);
	}
	protected void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.getSession().invalidate();
      response.sendRedirect(request.getContextPath());
    }
	protected void getService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
	  ServiceModel n = userService.getService(accessToken);
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
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}
	private void editService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String accessToken = (String) request.getSession().getAttribute("accessToken"); 
	  String output;
      String title = request.getParameter("title");
      String price = request.getParameter("price");
      String description = request.getParameter("description");
      String enabled = request.getParameter("enabled");
      int errorCode = userService.editService(accessToken, title, price, description, enabled);
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
