package aite.service;
import aite.model.ServiceModel;
import aite.model.TaskModel;
import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import aite.service.DBCONFIG;
import aite.service.ERRORCODE;
public class UserService {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  private String connectionStr = "jdbc:mysql://" + DBCONFIG.HOST + "/aite?"
      + "user=" + DBCONFIG.USER + "&password=" + DBCONFIG.PWD;
  
  public int getUIDbyToken(String accessToken) {
    if(accessToken == null || accessToken.length() == 0)
      return -1;
    else 
      return 1;
  }
  public ArrayList<ServiceModel> getServiceList(){
    ArrayList<ServiceModel> result = new ArrayList<ServiceModel>();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT sid, lname, fname, title, price, description, enabled from service s, user u WHERE u.uid = s.uid AND enabled = 1");
      resultSet = preparedStatement.executeQuery();
      while(resultSet.next()) {
        ServiceModel service = new ServiceModel();
        service.sid = resultSet.getInt("sid");
        service.name = resultSet.getString("fname") + resultSet.getString("lname");
        service.title = resultSet.getString("title");
        service.price = resultSet.getFloat("price");
        service.description = resultSet.getString("description");
        service.enabled = resultSet.getBoolean("enabled");
        result.add(service);
      }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      close();
    }
    return result;
  }
  public ArrayList<TaskModel> getTaskList() {
    ArrayList<TaskModel> result = new ArrayList<TaskModel>();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT tid, t.uid, lname, fname, title, create_time from task t, user u WHERE t.uid = u.uid");
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
    return result;
  }
  public TaskModel getTask(int tid) {
    TaskModel task = null;
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT tid, t.uid, fname, lname, title, location, description, create_time FROM task t, user u WHERE t.uid = u.uid AND tid = ?");
        preparedStatement.setInt(1, tid);
        resultSet = preparedStatement.executeQuery();
        boolean result = resultSet.next();
        if(result) {
          task = new TaskModel();
          task.tid = resultSet.getInt("tid");
          task.uid = resultSet.getInt("uid");
          task.name = resultSet.getString("fname") + resultSet.getString("lname");
          task.title = resultSet.getString("title");
          task.location = resultSet.getString("location");
          task.description = resultSet.getString("description");
          task.createTime = resultSet.getString("create_time");
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    return task;
  }

  public ServiceModel getService(int sid) {
    ServiceModel service = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT fname, lname, title, price, description, enabled from service s, user u WHERE sid = ? AND s.uid = u.uid LIMIT 1");
      preparedStatement.setInt(1, sid);
      resultSet = preparedStatement.executeQuery();
      boolean result = resultSet.next();
      if(result) {
        service = new ServiceModel();
        service.title = resultSet.getString("title");
        service.name = resultSet.getString("fname") + resultSet.getString("lname");
        service.price = resultSet.getFloat("price");
        service.description = resultSet.getString("description");
        service.enabled = resultSet.getBoolean("enabled");
      }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      close();
    }
    return service;
  }
  public ServiceModel getMyService(String accessToken) {
    ServiceModel service = null;
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
          service = new ServiceModel();
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

  public int newTask(String accessToken, String title, String location, String description) {

    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    if( title == null || title.length() == 0 )
      return ERRORCODE.NEWTASK_TITLE_EMPTY;
    else if( description == null || description.length() == 0)
      return ERRORCODE.NEWTASK_DESCRIPTION_EMPTY;
    else if(location == null || location.length() == 0) {
      location = "Not Known";
    }
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("INSERT INTO task (uid, title, location, description) VALUES (?, ?, ?, ?)");
      preparedStatement.setInt(1, uid);
      preparedStatement.setString(2, title);
      preparedStatement.setString(3, location);
      preparedStatement.setString(4, description);
      int result = preparedStatement.executeUpdate();
      if(result > 0) {
        // Query Success
      } else {
        // Query failed
        errorCode = ERRORCODE.NEWTASK_INSERT_ERROR;
      }
    } catch (Exception e) {
      errorCode = ERRORCODE.NEWTASK_EXCEPTION;
      System.out.println(e);
    } finally {
      close();
    }
    
    return errorCode;
  }
  
  public int editService(String accessToken, String title, String price, String description, String enabled) {

    int errorCode = 0;
    float floatPrice = 0;
    Boolean booleanEnbaled = false;
    int uid = getUIDbyToken(accessToken);
    if( title == null || title.length() == 0 )
      return ERRORCODE.SERVICE_TITLE_EMPTY;
    else if( price == null || price.length() == 0)
      return ERRORCODE.SERVICE_PRICE_EMPTY;
    else if( description == null || description.length() == 0)
      return ERRORCODE.SERVICE_DESCRIPTION_EMPTY;
    else if( enabled == null|| enabled.length() == 0)
      return ERRORCODE.SERVICE_ENABLED_EMPTY;
    else if( !enabled.equalsIgnoreCase("true") & !enabled.equalsIgnoreCase("false"))
      return ERRORCODE.SERVICE_ENABLED_OUTRANGE;
    try {
      floatPrice = Float.parseFloat(price);
    } catch(Exception e) {
      return ERRORCODE.SERVICE_PRICE_INVALID;
    }
    try {
      booleanEnbaled = Boolean.parseBoolean(enabled);
    } catch(Exception e) {
      return ERRORCODE.SERVICE_ENABLED_OUTRANGE;
    }
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT 1 from service WHERE uid = ? LIMIT 1");
      preparedStatement.setInt(1, uid);
      resultSet = preparedStatement.executeQuery();
      boolean recordExist = resultSet.next();
      if (recordExist) {
        // record exist, then edit
        preparedStatement = connect.prepareStatement("UPDATE service SET uid = ?, title = ?, price = ?, description = ?, enabled = ?");
        preparedStatement.setInt(1, uid);
        preparedStatement.setString(2, title);
        preparedStatement.setFloat(3, floatPrice);
        preparedStatement.setString(4, description);
        preparedStatement.setBoolean(5, booleanEnbaled);
        int result = preparedStatement.executeUpdate();
        if(result > 0) {
          // Query Success
        } else {
          // Query failed
          errorCode = ERRORCODE.SERVICE_EDIT_ERROR;
        }
        errorCode = ERRORCODE.LOGIN_FAILED;
      } else {
        // not exist, then insert
        preparedStatement = connect.prepareStatement("INSERT INTO service (uid, title, price, description, enabled) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, uid);
        preparedStatement.setString(2, title);
        preparedStatement.setFloat(3, floatPrice);
        preparedStatement.setString(4, description);
        preparedStatement.setBoolean(5, booleanEnbaled);
        int result = preparedStatement.executeUpdate();
        if(result > 0) {
          // Query Success
        } else {
          // Query failed
          errorCode = ERRORCODE.SERVICE_INSERT_ERROR;
        }
      }
    } catch (Exception e) {
      errorCode = ERRORCODE.SERVICE_EXCEPTION;
      System.out.println(e);
    } finally {
      close();
    }
    
    return errorCode;
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
      errorCode = ERRORCODE.CHPWD_UNANTHORIZED;
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
	// You need to close the resultSet
	  private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
