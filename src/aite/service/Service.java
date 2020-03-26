package aite.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class Service{
  protected Connection connect = null;
  protected Statement statement = null;
  protected PreparedStatement preparedStatement = null;
  protected ResultSet resultSet = null;
  protected String connectionStr = "jdbc:mysql://" + DBCONFIG.HOST + "/aite?"
      + "user=" + DBCONFIG.USER + "&password=" + DBCONFIG.PWD;

  public int getUIDbyToken(String accessToken) {
    int uid = -1;
    System.out.println("accessToken:" + accessToken);
    if(accessToken != null && accessToken.length() != 0) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT uid from token WHERE access_token = ? LIMIT 1");
        preparedStatement.setString(1, accessToken);
        resultSet = preparedStatement.executeQuery();
        boolean success = resultSet.next();
        if(success) {
          uid = resultSet.getInt("uid");
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    System.out.println("uid: " + uid);
    return uid;
   
  }
  // You need to close the resultSet
  protected void close() {
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
