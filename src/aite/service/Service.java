package aite.service;
import java.sql.Connection;
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
    if(accessToken == null || accessToken.length() == 0)
      return -1;
    else 
      return 1;
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
