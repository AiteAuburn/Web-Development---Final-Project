package aite.service;
import aite.model.OrderModel;
import java.sql.DriverManager;
import java.util.ArrayList;
public class GigOrderService extends Service{
  public int submitReview(String accessToken, String oid, String ratings, String comment) {
    int errorCode = 0;
    int uid = getUIDbyToken(accessToken);
    if( uid > 0 ) {
    
    } else {
      errorCode = 1;
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
            "WHERE (o.requester_uid = ? OR o.worker_uid = ?)");
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
