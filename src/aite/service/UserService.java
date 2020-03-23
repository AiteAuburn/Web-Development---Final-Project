package aite.service;
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
import aite.service.DBCONFIG;
import aite.service.ERRORCODE;
public class UserService {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  private String connectionStr = "jdbc:mysql://" + DBCONFIG.HOST + "/aite?"
      + "user=" + DBCONFIG.USER + "&password=" + DBCONFIG.PWD;
  
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
