package aite.servlet;

import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class AiteServlet (Base)
 */
public class AiteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  protected static final String JSP_GIG_TASKS = "/WEB-INF/view/gigtasks.jsp";
  protected static final String JSP_TASK_DETAIL = "/WEB-INF/view/task_detail.jsp";
  protected static final String JSP_LOGIN = "/WEB-INF/view/login.jsp";
  protected static final String JSP_ANNOUNCEMENT = "/WEB-INF/view/announcement.jsp";
  protected static final String JSP_COMMENTS = "/WEB-INF/view/comments.jsp";
  protected static final String JSP_REGISTER = "/WEB-INF/view/register.jsp";
  protected static final String JSP_SETTINGS = "/WEB-INF/view/settings.jsp";
  protected static final String JSP_CHPWD = "/WEB-INF/view/chpwd.jsp";
  protected static final String JSP_CHPWD_SUCCESS = "/WEB-INF/view/chpwd_success.jsp";
  protected static final String JSP_ORDER = "/WEB-INF/view/orders.jsp";
  protected static final String JSP_ORDER_DETAIL = "/WEB-INF/view/order_detail.jsp";
  protected static final String JSP_SERVICE = "/WEB-INF/view/service.jsp";
  protected static final String JSP_SERVICE_SUCCESS = "/WEB-INF/view/service_success.jsp";
  protected static final String JSP_USER_TASKS = "/WEB-INF/view/user_tasks.jsp";
  protected static final String JSP_NEW_TASK = "/WEB-INF/view/new_task.jsp";
  protected static final String JSP_NEW_TASK_SUCCESS = "/WEB-INF/view/new_task_success.jsp";
  protected static final String JSP_ERROR = "/WEB-INF/view/page404.jsp";
  protected static final String JSP_GIG_WORKERS = "/WEB-INF/view/gigworkers.jsp";
  protected static final String JSP_WORKER_DETAIL = "/WEB-INF/view/worker_detail.jsp";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AiteServlet() {
    super();
  }

}
