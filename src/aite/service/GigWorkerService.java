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
public class GigWorkerService extends Service{

  public ServiceModel getWorker(int sid) {
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
  public ArrayList<ServiceModel> getWorkerList(){
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
        preparedStatement = connect.prepareStatement("UPDATE service SET title = ?, price = ?, description = ?, enabled = ? WHERE uid = ?");
        preparedStatement.setString(1, title);
        preparedStatement.setFloat(2, floatPrice);
        preparedStatement.setString(3, description);
        preparedStatement.setBoolean(4, booleanEnbaled);
        preparedStatement.setInt(5, uid);
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
}
