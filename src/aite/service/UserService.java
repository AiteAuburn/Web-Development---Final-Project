package aite.service;
import aite.model.WorkerModel;
import aite.model.OrderModel;
import aite.model.TaskModel;
import aite.model.CommentModel;
import java.sql.DriverManager;
import java.util.ArrayList;
import aite.service.ERRORCODE;
public class UserService extends Service{
  public ArrayList<CommentModel> getComments(int uid) {
    ArrayList<CommentModel> comments = new ArrayList<CommentModel>();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();

      preparedStatement = connect.prepareStatement("SELECT cid, ratings, comment, create_time, CONCAT(u.fname, u.lname) as reviewer FROM comment c LEFT JOIN user u ON c.from_uid = u.uid WHERE to_uid = ? ORDER BY create_time DESC");
      preparedStatement.setInt(1, uid);
      resultSet = preparedStatement.executeQuery();
      while(resultSet.next()) {
        CommentModel comment = new CommentModel();
        comment.cid = resultSet.getInt("cid");
        comment.reviewer = resultSet.getString("reviewer");
        comment.ratings = resultSet.getInt("ratings");
        comment.comment = resultSet.getString("comment");
        comment.createTime = resultSet.getString("create_time");
        comments.add(comment);
      }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      close();
    }
    return comments;
  }
  public ArrayList<TaskModel> getMyTaskList(String accessToken) {
    ArrayList<TaskModel> result = new ArrayList<TaskModel>();
    int uid = getUIDbyToken(accessToken);
    if( uid > 0) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT tid, t.uid, lname, fname, title, create_time from task t, user u WHERE t.uid = ? AND t.uid = u.uid AND status = 'o'");
        preparedStatement.setInt(1, uid);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
          TaskModel task = new TaskModel();
          task.tid = resultSet.getInt("tid");
          task.uid = resultSet.getInt("uid");
          task.name = resultSet.getString("fname") + resultSet.getString("lname");
          task.title = resultSet.getString("title");
          task.createTime = resultSet.getString("create_time");
          result.add(task);
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return result;
  }

  public WorkerModel getMyService(String accessToken) {
    WorkerModel service = null;
    int uid = getUIDbyToken(accessToken);
    if( uid > 0) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT title, price, description, enabled from service WHERE uid = ? LIMIT 1");
        preparedStatement.setInt(1, uid);
        resultSet = preparedStatement.executeQuery();
        boolean result = resultSet.next();
        if(result) {
          service = new WorkerModel();
          service.title = resultSet.getString("title");
          service.price = resultSet.getFloat("price");
          service.description = resultSet.getString("description");
          service.enabled = resultSet.getBoolean("enabled");
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return service;
  }

  
  public int changePassword(String accessToken, String oldpwd, String pwd, String confirmpwd) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    if( uid > 0) {
      if(oldpwd.equals(pwd)){
        return ERRORCODE.CHPWD_SAME_PASSWORD;
      } else if(!pwd.equals(confirmpwd)) {
        return ERRORCODE.CHPWD_CONFIRMPWD_ERROR;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("UPDATE user SET pwd = ? WHERE uid = ? AND pwd = ?");
        preparedStatement.setString(1, pwd);
        preparedStatement.setInt(2, uid);
        preparedStatement.setString(3, oldpwd);
        int result = preparedStatement.executeUpdate();
        if(result > 0) {
          // Query Success
        } else {
          // Query failed
          errorCode = ERRORCODE.CHPWD_PASSWORD_MISMATCH;
        }
      } catch (Exception e) {
        errorCode = ERRORCODE.CHPWD_EXCEPTION;
        System.out.println(e);
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.CHPWD_UNAUTHORIZED;
    }
    return errorCode;
  }
  public int login(String username, String pwd) {
    int errorCode = 0;
	try {
      Class.forName("com.mysql.jdbc.Driver");
	  connect = DriverManager.getConnection(connectionStr);
	  statement = connect.createStatement();
	  preparedStatement = connect.prepareStatement("SELECT 1 from user WHERE username = ? and pwd = ? LIMIT 1");
	  preparedStatement.setString(1, username);
	  preparedStatement.setString(2, pwd);
	  resultSet = preparedStatement.executeQuery();
	  boolean result = resultSet.next();
	  if(!result) {
	    errorCode = ERRORCODE.LOGIN_FAILED;
	  }
	} catch (Exception e) {
      errorCode = ERRORCODE.LOGIN_EXCEPTION;
	  System.out.println(e);
	} finally {
	  close();
	}
	return errorCode;
  }
  public int register(String username, String pwd, String fname, String lname) {
    int errorCode = 0;
    if( username.length() == 0 )
      return ERRORCODE.REGISTER_USERNAME_EMPTY;
    else if( pwd.length() == 0)
      return ERRORCODE.REGISTER_PWD_EMPTY;
    else if( fname.length() == 0)
      return ERRORCODE.REGISTER_FNAME_EMPTY;
    else if( lname.length() == 0)
      return ERRORCODE.REGISTER_LNAME_EMPTY;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT 1 from user WHERE username = ? LIMIT 1");
      preparedStatement.setString(1, username);
      resultSet = preparedStatement.executeQuery();
      if(resultSet.next()) {
        // Username duplicate
        errorCode = ERRORCODE.REGISTER_DUPLICATE_USERNAME;
      } else {
        preparedStatement = connect.prepareStatement("INSERT INTO user (username, pwd, fname, lname) VALUES(?, ?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, pwd);
        preparedStatement.setString(3, fname);
        preparedStatement.setString(4, lname);
        int result = preparedStatement.executeUpdate();
        if(result > 0) {
          // Query Success
        } else {
          // Query failed
          errorCode = ERRORCODE.REGISTER_INSERT_ERROR;
        }
      }
    } catch (Exception e) {
      errorCode = ERRORCODE.REGISTER_EXCEPTION;
      System.out.println(e);
    } finally {
      close();
    }
    return errorCode;
  }
}
