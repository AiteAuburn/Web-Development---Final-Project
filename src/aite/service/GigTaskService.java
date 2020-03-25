package aite.service;
import aite.model.ApplyModel;
import aite.model.TaskModel;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
public class GigTaskService extends Service{

  public int acceptOffer(String accessToken, String applyId) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int aid = 0;
    if( uid > 0 ) {
      try {
        aid = Integer.parseInt(applyId);
      } catch(Exception e) {
        return ERRORCODE.ACCEPTOFFER_ID_INVALID;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        connect.setAutoCommit(false);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT a.apply_uid, t.title, t.description, t.location, a.quote FROM apply a LEFT JOIN task t ON a.tid = t.tid WHERE t.uid = ? AND a.aid = ? AND a.status = 'o' AND t.status = 'o'");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, aid);
        resultSet = preparedStatement.executeQuery();
        boolean requestExist = resultSet.next();
        if(requestExist) {
          String title = resultSet.getString("title");
          String location = resultSet.getString("location");
          String description = resultSet.getString("description");
          int worker_uid = resultSet.getInt("apply_uid");
          float price = resultSet.getFloat("quote");
          preparedStatement = connect.prepareStatement("INSERT INTO orders(worker_uid, requester_uid, price, title, location, description) VALUES(?, ?, ?, ?, ?, ?)");
          preparedStatement.setInt(1, worker_uid);
          preparedStatement.setInt(2, uid);
          preparedStatement.setFloat(3, price);
          preparedStatement.setString(4, title);
          preparedStatement.setString(5, location);
          preparedStatement.setString(6, description);
          int result = preparedStatement.executeUpdate();
          if(result > 0) {
            // Query Success
            preparedStatement = connect.prepareStatement("DELETE FROM apply WHERE aid = ?");
            preparedStatement.setInt(1, aid);
            result = preparedStatement.executeUpdate();
            if(result > 0) {
              connect.commit();
            } else {
              errorCode = ERRORCODE.ACCEPTOFFER_DELETE_ERROR;
            }
          } else {
            // Query failed
            errorCode = ERRORCODE.ACCEPTOFFER_INSERT_ERROR;
          }
        }
      } catch (Exception e) {
        System.out.println(e);
        errorCode = ERRORCODE.ACCEPTOFFER_EXCEPTION;
      } finally {
        if( errorCode != 0) {
          try {
            connect.rollback();
          } catch (SQLException e1) {
            System.out.println(e1);
          }
        }
        close();
      }
    } else {
      errorCode = ERRORCODE.ACCEPTOFFER_UNAUTHORIED;
    }
    return errorCode;
  }
  
  public int rejectOffer(String accessToken, String applyId) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int aid = 0;
    if( uid > 0 ) {
      try {
        aid = Integer.parseInt(applyId);
      } catch(Exception e) {
        return ERRORCODE.REJECTOFFER_ID_INVALID;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT aid FROM apply a LEFT JOIN task t ON a.tid = t.tid WHERE t.uid = ? AND aid = ?");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, aid);
        resultSet = preparedStatement.executeQuery();
        boolean requestExist = resultSet.next();
        if(requestExist) {
          preparedStatement = connect.prepareStatement("UPDATE apply SET status = 'r' WHERE aid = ?");
          preparedStatement.setInt(1, aid);
          int result = preparedStatement.executeUpdate();
          if(result > 0) {
          } else {
            errorCode = ERRORCODE.REJECTOFFER_REJECT_ERROR;
          }
        }
      } catch (Exception e) {
        System.out.println(e);
        errorCode = ERRORCODE.REJECTOFFER_EXCEPTION;
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.REJECTOFFER_UNAUTHORIED;
    }
    return errorCode;
  }
  
  public ArrayList<ApplyModel> getApplyList(String accessToken, int tid){
    ArrayList<ApplyModel> result = new ArrayList<ApplyModel>();
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT aid, fname, lname, quote, a.create_time FROM task t LEFT JOIN apply a ON t.tid = a.tid LEFT JOIN user u ON a.apply_uid = u.uid WHERE t.tid = ? AND t.uid = ? AND a.status ='o'");
        preparedStatement.setInt(1, tid);
        preparedStatement.setInt(2, uid);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
          ApplyModel apply = new ApplyModel();
          apply.aid = resultSet.getInt("aid");
          apply.name = resultSet.getString("fname") + resultSet.getString("lname");
          apply.quote = resultSet.getFloat("quote");
          apply.createTime = resultSet.getString("create_time"); 
          result.add(apply);
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return result;
  }

  
  public int applyTask(String accessToken, String tid, String price) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int intTaskId = 0;
    float floatPrice = 0;
    
    if( uid > 0 ) {
      if( tid == null || tid.length() == 0 )
        return ERRORCODE.TASKAPPLY_TASKID_EMPTY;
      else if( price == null || price.length() == 0)
        return ERRORCODE.TASKAPPLY_PRICE_EMPTY;

      try {
        intTaskId = Integer.parseInt(tid);
      } catch(Exception e) {
        return ERRORCODE.TASKAPPLY_TASKID_INVALID;
      }
      
      try {
        floatPrice = Float.parseFloat(price);
        if(floatPrice == 0 ) {
          return ERRORCODE.TASKAPPLY_PRICE_INVALID;
        }
      } catch(Exception e) {
        return ERRORCODE.TASKAPPLY_PRICE_INVALID;
      }
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT status from task WHERE tid = ? LIMIT 1");
        preparedStatement.setInt(1, intTaskId);
        resultSet = preparedStatement.executeQuery();
        boolean taskExist = resultSet.next();
        if (!taskExist) {
          errorCode = ERRORCODE.TASKAPPLY_TASK_NOT_EXIST;
        } else {
          String taskStatus = resultSet.getString("status");
          boolean taskOpen = taskStatus.equalsIgnoreCase("o");
          if(taskOpen) {
            preparedStatement = connect.prepareStatement("SELECT aid, status from apply WHERE tid = ? AND apply_uid = ? LIMIT 1");
            preparedStatement.setInt(1, intTaskId);
            preparedStatement.setInt(2, uid);
            resultSet = preparedStatement.executeQuery();
            boolean recordExist = resultSet.next();
            if (recordExist) {
              // record exist, then tell user he/she has applied.
              int aid = resultSet.getInt("aid");
              String applyStatus = resultSet.getString("status");
              if(applyStatus.equalsIgnoreCase("o")) {
                errorCode = ERRORCODE.TASKAPPLY_APPLY_EXIST;
              } else {
                preparedStatement = connect.prepareStatement("UPDATE apply SET quote = ?, status = 'o' WHERE aid = ?");
                preparedStatement.setFloat(1, floatPrice);
                preparedStatement.setInt(2, aid);
                int result = preparedStatement.executeUpdate();
                if(result > 0) {
                  // Query Success
                } else {
                  // Query failed
                  errorCode = ERRORCODE.TASKAPPLY_UPDATE_ERROR;
                }
              }

            } else {
              preparedStatement = connect.prepareStatement("INSERT INTO apply (tid, apply_uid, quote) VALUES (?, ?, ?)");
              preparedStatement.setInt(1, intTaskId);
              preparedStatement.setInt(2, uid);
              preparedStatement.setFloat(3, floatPrice);
              int result = preparedStatement.executeUpdate();
              if(result > 0) {
                // Query Success
              } else {
                // Query failed
                errorCode = ERRORCODE.TASKAPPLY_INSERT_ERROR;
              }
            }
          } else {
            errorCode = ERRORCODE.TASKAPPLY_TASK_CLOSED;
          }
        }
      } catch (Exception e) {
        errorCode = ERRORCODE.NEWTASK_EXCEPTION;
        System.out.println(e);
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.TASKAPPLY_UNAUTHORIED;
    }
    
    return errorCode;
  }

  public int cancelOffer(String accessToken, String tid) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int intTaskId = 0;
    if( uid > 0 ) {
      if( tid == null || tid.length() == 0 )
        return ERRORCODE.TASKCANCEL_TASKID_EMPTY;
      try {
        intTaskId = Integer.parseInt(tid);
      } catch(Exception e) {
        return ERRORCODE.TASKCANCEL_TASKID_INVALID;
      }
      
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT 1 from apply WHERE tid = ? AND apply_uid = ? LIMIT 1");
        preparedStatement.setInt(1, intTaskId);
        preparedStatement.setInt(2, uid);
        resultSet = preparedStatement.executeQuery();
        boolean recordExist = resultSet.next();
        if (recordExist) {
          preparedStatement = connect.prepareStatement("UPDATE apply SET status = 'c' WHERE tid = ? AND apply_uid = ?");
          preparedStatement.setInt(1, intTaskId);
          preparedStatement.setInt(2, uid);
          int result = preparedStatement.executeUpdate();
          if(result > 0) {
            // Query Success
          } else {
            // Query failed
            errorCode = ERRORCODE.TASKCANCEL_CANCEL_ERROR;
          }
        } else {
          errorCode = ERRORCODE.TASKCANCEL_OFFER_NOT_EXIST;
        }
        
      } catch (Exception e) {
        errorCode = ERRORCODE.TASKCANCEL_EXCEPTION;
        System.out.println(e);
      } finally {
        close();
      }
    } else {
      errorCode = ERRORCODE.TASKCANCEL_UNAUTHORIED;
    }
    
    return errorCode;
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
    if(uid > 0) {
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
    } else {
      errorCode = ERRORCODE.NEWTASK_UNAUTHORIZED;
    }
    
    return errorCode;
  }
  public TaskModel getTask(String accessToken, int tid) {
    TaskModel task = null;
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement(
            "SELECT t.tid, t.uid, fname, lname, title, location, description, t.create_time, a.status as applyStatus, a.quote " + 
            "FROM task t " + 
            "LEFT JOIN user u " + 
            "ON t.uid = u.uid " + 
            "LEFT JOIN apply a " + 
            "ON a.tid = t.tid AND a.apply_uid = ? " + 
            "WHERE t.tid = ? AND t.status = 'o'");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, tid);
        resultSet = preparedStatement.executeQuery();
        boolean result = resultSet.next();
        if(result) {
          task = new TaskModel();
          task.tid = resultSet.getInt("tid");
          task.uid = resultSet.getInt("uid");
          task.name = resultSet.getString("fname") + resultSet.getString("lname");
          task.title = resultSet.getString("title");
          task.location = resultSet.getString("location");
          task.quote = resultSet.getFloat("quote");
          task.description = resultSet.getString("description");
          task.createTime = resultSet.getString("create_time");
          task.applyStatus = resultSet.getString("applyStatus");
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return task;
  }
  
  public ArrayList<TaskModel> getTaskList() {
    ArrayList<TaskModel> result = new ArrayList<TaskModel>();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(connectionStr);
      statement = connect.createStatement();
      preparedStatement = connect.prepareStatement("SELECT tid, t.uid, lname, fname, title, create_time from task t, user u WHERE t.uid = u.uid AND status = 'o' ORDER BY create_time DESC");
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
}
