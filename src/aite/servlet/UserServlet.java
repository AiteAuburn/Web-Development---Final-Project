package aite.servlet;
import aite.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet({"/user/login", "/user/register", "/user/logout"})
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
		if (request.getRequestURI().endsWith("/login")) {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		} else if (request.getRequestURI().endsWith("/register")) {
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServlRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().endsWith("/login")) {
		  String output;
		  String username = request.getParameter("username");
		  String pwd = request.getParameter("pwd");
          Boolean result = userService.login(username, pwd);
		  output = String.format("/login [%b] | username: %s, password: %s", result, username, pwd);
		  System.out.println(output);
		  if(result)
		    ;
		  request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		} else if (request.getRequestURI().endsWith("/register")) {
			;//request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

}
