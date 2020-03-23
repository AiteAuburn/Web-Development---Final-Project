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
public class UserService {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	  
	public boolean login(String username, String pwd) {
	  boolean success = false;
		try {
	      Class.forName("com.mysql.jdbc.Driver");
	      String connectionStr = "jdbc:mysql://" + DBCONFIG.HOST + "/aite?"
	              + "user=" + DBCONFIG.USER + "&password=" + DBCONFIG.PWD;
	      
	      connect = DriverManager.getConnection(connectionStr);
	      statement = connect.createStatement();
	      preparedStatement = connect.prepareStatement("SELECT 1 from user WHERE username = ? and pwd = ? LIMIT 1");
	      // Parameters start with 1
	      preparedStatement.setString(1, username);
	      preparedStatement.setString(2, pwd);
	      resultSet = preparedStatement.executeQuery();
	      success = resultSet.next();
	    } catch (Exception e) {
	      success = false;
          System.out.println(e);
	    } finally {
	      close();
	    }
        return success;
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
