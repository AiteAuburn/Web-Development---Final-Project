package aite.service;
import aite.model.OrderModel;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
public class GigOrderService extends Service{
  public int submitReview(String accessToken, String orderId, String rt, String comment) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    int oid = 0;
    int ratings = 0;
    if( uid > 0 ) {
      try {
        oid = Integer.parseInt(orderId);
      } catch(Exception e) {
        return ERRORCODE.SUBMITREVIEW_ORDERID_INVALID;
      }
      try {
        ratings = Integer.parseInt(rt);
        if(ratings < 0 || ratings > 5 ) {
          return ERRORCODE.SUBMITREVIEW_RATINGS_INVALID;
        }
      } catch(Exception e) {
        return ERRORCODE.SUBMITREVIEW_RATINGS_INVALID;
      }
      
      if(comment == null) {
        comment = "";
      }

      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        connect.setAutoCommit(false);
        preparedStatement = connect.prepareStatement("SELECT o.oid, o.worker_uid,  o.requester_uid, o.status FROM orders o WHERE o.oid = ? AND (o.worker_uid = ? OR o.requester_uid = ?)");
        preparedStatement.setInt(1, oid);
        preparedStatement.setInt(2, uid);
        preparedStatement.setInt(3, uid);
        resultSet = preparedStatement.executeQuery();
        boolean orderExist = resultSet.next();
        if(orderExist) {
          int requesterID =  resultSet.getInt("requester_uid");
          int workerUID =  resultSet.getInt("worker_uid");
          int senderID = uid;
          int receiverID = 0;
          receiverID = (senderID == requesterID )? workerUID:requesterID;
          String status = resultSet.getString("status");
          if(!status.equalsIgnoreCase("e") ) {
            boolean canReview = false;
            String nextStatus = "";
            if( status.equalsIgnoreCase("o")) {
              canReview = true;
              if(senderID == workerUID) {
                nextStatus = "wr";
              } else {
                nextStatus = "ww";
              }
            } else {
              canReview = ( status.equalsIgnoreCase("ww") && senderID == workerUID) ||
              ( status.equalsIgnoreCase("wr") && senderID == requesterID);
              nextStatus = "e";
            }
            if(canReview) {
              preparedStatement = connect.prepareStatement("INSERT INTO comment(oid, from_uid, to_uid, ratings, comment) VALUES(?, ?, ?, ?, ?)");
              preparedStatement.setInt(1, oid);
              preparedStatement.setInt(2, senderID);
              preparedStatement.setInt(3, receiverID);
              preparedStatement.setInt(4, ratings);
              preparedStatement.setString(5, comment);
              int result = preparedStatement.executeUpdate();
              if(result > 0) {
                preparedStatement = connect.prepareStatement("UPDATE orders SET status = ? WHERE oid = ?");
                preparedStatement.setString(1, nextStatus);
                preparedStatement.setInt(2, oid);
                result = preparedStatement.executeUpdate();
                if(result > 0) {
                  connect.commit();
                  //Success
                } else {
                  errorCode = ERRORCODE.SUBMITREVIEW_SUBMIT_ERROR;
                }
              } else {
                errorCode = ERRORCODE.SUBMITREVIEW_SUBMIT_ERROR;
              }
            } else {
              errorCode = ERRORCODE.SUBMITREVIEW_ALREADY_REVIEW;
            }
          } else {
            errorCode = ERRORCODE.SUBMITREVIEW_ORDER_CLOSED;
          }
        } else {
          errorCode = ERRORCODE.SUBMITREVIEW_ORDER_NOT_EXIST;
        }
      } catch (Exception e) {
        System.out.println(e);
        errorCode = ERRORCODE.SUBMITREVIEW_EXCEPTION;
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
      errorCode = ERRORCODE.SUBMITREVIEW_UNAUTHORIZED;
    }
    return errorCode;
  }
  
  public OrderModel getOrder(String accessToken, int oid) {
    OrderModel order = null;
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT o.oid, o.worker_uid,  CONCAT(u1.fname,' ', u1.lname) as worker_name, o.requester_uid, CONCAT(u2.fname,' ', u2.lname) as requester_name, o.title, o.location, o.description, o.price, o.status, o.create_time " + 
            "FROM orders o " + 
            "LEFT JOIN user u1 " + 
            "ON o.worker_uid = u1.uid " + 
            "LEFT JOIN user u2 " + 
            "ON o.requester_uid = u2.uid " + 
            "WHERE o.oid = ? " + 
            "AND (o.requester_uid = ? OR o.worker_uid = ?) " + 
            "LIMIT 1");
        preparedStatement.setInt(1, oid);
        preparedStatement.setInt(2, uid);
        preparedStatement.setInt(3, uid);
        resultSet = preparedStatement.executeQuery();
        boolean result = resultSet.next();
        if(result) {
          order = new OrderModel();
          order.oid =  oid;
          order.requesterID =  resultSet.getInt("requester_uid");
          order.requesterName =  resultSet.getString("requester_name");
          order.workerUID =  resultSet.getInt("worker_uid");
          order.workerName =  resultSet.getString("worker_name");
          order.title = resultSet.getString("title");
          order.price = resultSet.getFloat("price");
          order.location = resultSet.getString("location");
          order.description = resultSet.getString("description");
          order.createTime = resultSet.getString("create_time");
          order.status = resultSet.getString("status");
          preparedStatement = connect.prepareStatement("SELECT ratings, comment, create_time FROM comment WHERE oid = ? AND from_uid = ? LIMIT 1");
          preparedStatement.setInt(1, oid);
          preparedStatement.setInt(2, order.requesterID);
          resultSet = preparedStatement.executeQuery();
          result = resultSet.next();
          if(result) {
            order.requesterReviewRatings = resultSet.getInt("ratings");
            order.requesterReview = resultSet.getString("comment");
            order.requesterReviewTime = resultSet.getString("create_time");
          }
          preparedStatement = connect.prepareStatement("SELECT ratings, comment, create_time FROM comment WHERE oid = ? AND from_uid = ? LIMIT 1");
          preparedStatement.setInt(1, oid);
          preparedStatement.setInt(2, order.workerUID);
          resultSet = preparedStatement.executeQuery();
          result = resultSet.next();
          if(result) {
            order.workerReviewRatings = resultSet.getInt("ratings");
            order.workerReview = resultSet.getString("comment");
            order.workerReviewTime = resultSet.getString("create_time");
          }
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return order;
  }
  public ArrayList<OrderModel> getOrderList(String accessToken){
    ArrayList<OrderModel> result = new ArrayList<OrderModel>();
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(connectionStr);
        statement = connect.createStatement();
        preparedStatement = connect.prepareStatement("SELECT o.oid, o.worker_uid,  CONCAT(u1.fname,' ', u1.lname) as worker_name, o.requester_uid, CONCAT(u2.fname,' ', u2.lname) as requester_name, o.title, o.location, o.description, o.price, o.status, o.create_time " + 
            "FROM orders o " + 
            "LEFT JOIN user u1 " + 
            "ON o.worker_uid = u1.uid " + 
            "LEFT JOIN user u2 " + 
            "ON o.requester_uid = u2.uid " + 
            "WHERE (o.requester_uid = ? OR o.worker_uid = ?) ORDER BY o.create_time DESC");
        preparedStatement.setInt(1, uid);
        preparedStatement.setInt(2, uid);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
          OrderModel order = new OrderModel();
          order.oid = resultSet.getInt("oid");
          order.requesterID =  resultSet.getInt("requester_uid");
          order.requesterName =  resultSet.getString("requester_name");
          order.workerUID =  resultSet.getInt("worker_uid");
          order.workerName =  resultSet.getString("worker_name");
          order.title = resultSet.getString("title");
          order.price = resultSet.getFloat("price");
          order.location = resultSet.getString("location");
          order.description = resultSet.getString("description");
          order.createTime = resultSet.getString("create_time");
          order.status = resultSet.getString("status");
          result.add(order);
        }
      } catch (Exception e) {
        System.out.println(e);
      } finally {
        close();
      }
    }
    return result;
  }

}
