package aite.service;
import aite.model.WorkerModel;
import aite.model.RequestModel;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import aite.service.ERRORCODE;
public class GigWorkerService extends Service{

  public int acceptRequest(String accessToken, String requestId) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int rid = 0;
    if( uid > 0 ) {
      try {
        rid = Integer.parseInt(requestId);
      } catch(Exception e) {
        return ERRORCODE.ACCEPTREQUEST_ID_INVALID;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        connect.setAutoCommit(false);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT s.title, r.description, r.request_uid, r.location, r.price FROM request r LEFT JOIN service s ON r.sid = s.sid WHERE s.uid = ? AND r.rid = ? AND r.status = 'o' AND s.enabled = 1");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, rid);
        resultSet = preparedStatement.executeQuery();
        boolean requestExist = resultSet.next();
        if(requestExist) {
          String title = resultSet.getString("title");
          String location = resultSet.getString("location");
          String description = resultSet.getString("description");
          int requester_uid = resultSet.getInt("request_uid");
          float price = resultSet.getFloat("price");
          preparedStatement = connect.prepareStatement("INSERT INTO orders(worker_uid, requester_uid, price, title, location, description) VALUES(?, ?, ?, ?, ?, ?)");
          preparedStatement.setInt(1, uid);
          preparedStatement.setInt(2, requester_uid);
          preparedStatement.setFloat(3, price);
          preparedStatement.setString(4, title);
          preparedStatement.setString(5, location);
          preparedStatement.setString(6, description);
          int result = preparedStatement.executeUpdate();
          if(result > 0) {
            // Query Success
            preparedStatement = connect.prepareStatement("DELETE FROM request WHERE rid = ?");
            preparedStatement.setInt(1, rid);
            result = preparedStatement.executeUpdate();
            if(result > 0) {
              connect.commit();
            } else {
              errorCode = ERRORCODE.ACCEPTREQUEST_DELETE_ERROR;
            }
          } else {
            // Query failed
            errorCode = ERRORCODE.ACCEPTREQUEST_INSERT_ERROR;
          }
        }
      } catch (SQLException e) {
        System.out.println(e);
        try {
          connect.rollback();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        errorCode = ERRORCODE.ACCEPTREQUEST_EXCEPTION;
      } catch (Exception e) {
        System.out.println(e);
        try {
          connect.rollback();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
        errorCode = ERRORCODE.ACCEPTREQUEST_EXCEPTION;
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.ACCEPTREQUEST_UNAUTHORIED;
    }
    return errorCode;
  }
  
  public int rejectRequest(String accessToken, String requestId) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int rid = 0;
    if( uid > 0 ) {
      try {
        rid = Integer.parseInt(requestId);
      } catch(Exception e) {
        return ERRORCODE.REJECTREQUEST_ID_INVALID;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT rid FROM request r LEFT JOIN service s ON r.sid = s.sid WHERE s.uid = ? AND rid = ?");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, rid);
        resultSet = preparedStatement.executeQuery();
        boolean requestExist = resultSet.next();
        if(requestExist) {
          preparedStatement = connect.prepareStatement("UPDATE request SET status = 'r' WHERE rid = ?");
          preparedStatement.setInt(1, rid);
          int result = preparedStatement.executeUpdate();
          if(result > 0) {
            
          } else {
            errorCode = ERRORCODE.REJECTREQUEST_REJECT_ERROR;
          }
        }
      } catch (Exception e) {
        System.out.println(e);
        errorCode = ERRORCODE.REJECTREQUEST_EXCEPTION;
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.REJECTREQUEST_UNAUTHORIED;
    }
    return errorCode;
  }
  
  
  public ArrayList<RequestModel> getRequestList(String accessToken, int tid){
    ArrayList<RequestModel> result = new ArrayList<RequestModel>();
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT rid, fname, lname, r.price, r.create_time FROM service s LEFT JOIN request r ON s.sid = r.sid LEFT JOIN user u ON r.request_uid = u.uid WHERE s.sid = ? AND s.uid = ? AND r.status ='o'");
        preparedStatement.setInt(1, tid);
        preparedStatement.setInt(2, uid);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
          RequestModel request = new RequestModel();
          request.rid = resultSet.getInt("rid");
          request.name = resultSet.getString("fname") + resultSet.getString("lname");
          request.price = resultSet.getFloat("price");
          request.createTime = resultSet.getString("create_time"); 
          result.add(request);
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return result;
  }
  public int requestWorker(String accessToken, String serviceId, String location, String description) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int sid = 0;
    if( uid > 0 ) {
      if( serviceId == null || serviceId.length() == 0 )
        return ERRORCODE.WORKREQUEST_ID_EMPTY;

      else if( description == null || description.length() == 0)
        return ERRORCODE.WORKREQUEST_DESCRIPTION_EMPTY;
      else if( location == null|| location.length() == 0) {
        return ERRORCODE.WORKREQUEST_LOCATION_EMPTY;
      }
      try {
        sid = Integer.parseInt(serviceId);
      } catch(Exception e) {
        return ERRORCODE.WORKREQUEST_ID_INVALID;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT price from service WHERE sid = ? AND enabled = 1 LIMIT 1");
        preparedStatement.setInt(1, sid);
        resultSet = preparedStatement.executeQuery();
        boolean serviceExist = resultSet.next();
        if (!serviceExist) {
          errorCode = ERRORCODE.WORKREQUEST_SERVICE_NOT_EXIST;
        } else {
          float price = resultSet.getFloat("price");
          preparedStatement = connect.prepareStatement("SELECT rid from request WHERE sid = ? AND request_uid = ? LIMIT 1");
          preparedStatement.setInt(1, sid);
          preparedStatement.setInt(2, uid);
          resultSet = preparedStatement.executeQuery();
          boolean requestExist = resultSet.next();
          if (requestExist) {
            int rid = resultSet.getInt("rid");
            // record exist, then tell user he/she has applied
            preparedStatement = connect.prepareStatement("UPDATE request SET location = ?, description = ?, price = ?, status = 'o' WHERE rid = ?");
            preparedStatement.setString(1, location);
            preparedStatement.setString(2, description);
            preparedStatement.setFloat(3, price);
            preparedStatement.setInt(4, rid);
            int result = preparedStatement.executeUpdate();
            if(result > 0) {
              // Query Success
            } else {
              // Query failed
              errorCode = ERRORCODE.WORKREQUEST_EDIT_ERROR;
            }
          } else {
            preparedStatement = connect.prepareStatement("INSERT INTO request (sid, request_uid, location, description, price) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, sid);
            preparedStatement.setInt(2, uid);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, description);
            preparedStatement.setFloat(5, price);
            int result = preparedStatement.executeUpdate();
            if(result > 0) {
              // Query Success
            } else {
              // Query failed
              errorCode = ERRORCODE.WORKREQUEST_INSERT_ERROR;
            }
          }
        }
      } catch (Exception e) {
        errorCode = ERRORCODE.WORKREQUEST_EXCEPTION;
        System.out.println(e);
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.WORKREQUEST_UNAUTHORIED;
    }
    
    return errorCode;
  }

  public int cancelRequest(String accessToken, String serviceId) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int sid = 0;
    if( uid > 0 ) {
      if( serviceId == null || serviceId.length() == 0 )
        return ERRORCODE.REQUESTCANCEL_ID_EMPTY;
      try {
        sid = Integer.parseInt(serviceId);
      } catch(Exception e) {
        return ERRORCODE.REQUESTCANCEL_ID_INVALID;
      }
      
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT rid from request WHERE sid = ? AND request_uid = ? LIMIT 1");
        preparedStatement.setInt(1, sid);
        preparedStatement.setInt(2, uid);
        resultSet = preparedStatement.executeQuery();
        boolean recordExist = resultSet.next();
        if (recordExist) {
          int rid = resultSet.getInt("rid");
          preparedStatement = connect.prepareStatement("UPDATE request SET status = 'c' WHERE rid = ?");
          preparedStatement.setInt(1, rid);
          int result = preparedStatement.executeUpdate();
          if(result > 0) {
            // Query Success
          } else {
            // Query failed
            errorCode = ERRORCODE.REQUESTCANCEL_CANCEL_ERROR;
          }
        } else {
          errorCode = ERRORCODE.REQUESTCANCEL_REQUEST_NOT_EXIST;
        }
        
      } catch (Exception e) {
        errorCode = ERRORCODE.REQUESTCANCEL_EXCEPTION;
        System.out.println(e);
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.REQUESTCANCEL_UNAUTHORIED;
    }
    
    return errorCode;
  }
  
  
  
  public WorkerModel getWorker(String accessToken, int sid) {
    WorkerModel worker = null;
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT s.uid, r.status as requestStatus, r.location as requestLocation, r.description as requestDescription, rid, fname, lname, title, s.price, s.description, enabled from service s " + 
            "LEFT JOIN user u " + 
            "ON s.uid = u.uid " + 
            "LEFT JOIN request r " + 
            "ON r.request_uid = ? " + 
            "WHERE s.sid = ? LIMIT 1");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, sid);
        resultSet = preparedStatement.executeQuery();
        boolean result = resultSet.next();
        if(result) {
          worker = new WorkerModel();
          worker.sid =  sid;
          worker.rid =  resultSet.getInt("rid");
          worker.uid =  resultSet.getInt("uid");
          worker.title = resultSet.getString("title");
          worker.name = resultSet.getString("fname") + resultSet.getString("lname");
          worker.price = resultSet.getFloat("price");
          worker.description = resultSet.getString("description");
          worker.enabled = resultSet.getBoolean("enabled");
          worker.requestStatus = resultSet.getString("requestStatus");
          worker.requestLocation = resultSet.getString("requestLocation");
          worker.requestDescription = resultSet.getString("requestDescription");
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return worker;
  }
  public ArrayList<WorkerModel> getWorkerList(){
    ArrayList<WorkerModel> result = new ArrayList<WorkerModel>();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT sid, lname, fname, title, price, description, enabled from service s, user u WHERE u.uid = s.uid AND enabled = 1");
      resultSet = preparedStatement.executeQuery();
      while(resultSet.next()) {
        WorkerModel worker = new WorkerModel();
        worker.sid = resultSet.getInt("sid");
        worker.name = resultSet.getString("fname") + resultSet.getString("lname");
        worker.title = resultSet.getString("title");
        worker.price = resultSet.getFloat("price");
        worker.description = resultSet.getString("description");
        worker.enabled = resultSet.getBoolean("enabled");
        result.add(worker);
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
