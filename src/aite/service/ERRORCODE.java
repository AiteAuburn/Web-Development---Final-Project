package aite.service;

import java.util.HashMap;

public class ERRORCODE {
  public final static int LOGIN_FAILED = 100;
  public final static int LOGIN_EXCEPTION = 101;
  
  public final static int REGISTER_DUPLICATE_USERNAME = 200;
  public final static int REGISTER_INSERT_ERROR = 201;
  public final static int REGISTER_EXCEPTION = 202;
  public final static int REGISTER_USERNAME_EMPTY = 203;
  public final static int REGISTER_PWD_EMPTY = 204;
  public final static int REGISTER_FNAME_EMPTY = 205;
  public final static int REGISTER_LNAME_EMPTY = 206;
  
  public final static int SERVICE_TITLE_EMPTY = 301;
  public final static int SERVICE_PRICE_EMPTY = 302;
  public final static int SERVICE_PRICE_INVALID = 302;
  public final static int SERVICE_DESCRIPTION_EMPTY = 303;
  public final static int SERVICE_ENABLED_EMPTY = 304;
  public final static int SERVICE_ENABLED_OUTRANGE = 305;
  public final static int SERVICE_INSERT_ERROR = 306;
  public final static int SERVICE_EDIT_ERROR = 307;
  public final static int SERVICE_EXCEPTION = 308;
  
  public final static int NEWTASK_TITLE_EMPTY = 401;
  public final static int NEWTASK_DESCRIPTION_EMPTY = 402;
  public final static int NEWTASK_INSERT_ERROR = 403;
  public final static int NEWTASK_EXCEPTION = 404;
  public final static int NEWTASK_UNAUTHORIZED = 405;
  
  public final static int CHPWD_SAME_PASSWORD = 501;
  public final static int CHPWD_CONFIRMPWD_ERROR = 502;
  public final static int CHPWD_UNAUTHORIZED = 503;
  public final static int CHPWD_EXCEPTION = 504;
  public final static int CHPWD_PASSWORD_MISMATCH = 505;
  
  public final static int TASKAPPLY_TASKID_EMPTY = 601;
  public final static int TASKAPPLY_PRICE_EMPTY = 602;
  public final static int TASKAPPLY_TASKID_INVALID = 603;
  public final static int TASKAPPLY_PRICE_INVALID = 604;
  public final static int TASKAPPLY_INSERT_ERROR = 605;
  public final static int TASKAPPLY_UPDATE_ERROR = 606;
  public final static int TASKAPPLY_EXCEPTION = 607;
  public final static int TASKAPPLY_UNAUTHORIED = 608;
  public final static int TASKAPPLY_APPLY_EXIST = 609;
  public final static int TASKAPPLY_TASK_NOT_EXIST = 610;
  public final static int TASKAPPLY_TASK_CLOSED = 611;

  public final static int TASKCANCEL_TASKID_EMPTY = 701;
  public final static int TASKCANCEL_TASKID_INVALID = 702;
  public final static int TASKCANCEL_CANCEL_ERROR = 703;
  public final static int TASKCANCEL_OFFER_NOT_EXIST = 704;
  public final static int TASKCANCEL_EXCEPTION = 705;
  public final static int TASKCANCEL_UNAUTHORIED = 706;
  
  public final static int WORKREQUEST_ID_EMPTY = 801;
  public final static int WORKREQUEST_ID_INVALID = 802;
  public final static int WORKREQUEST_SERVICE_NOT_EXIST = 803;
  public final static int WORKREQUEST_ALREADY_REQUEST = 804;
  public final static int WORKREQUEST_INSERT_ERROR = 805;
  public final static int WORKREQUEST_EDIT_ERROR = 806;
  public final static int WORKREQUEST_EXCEPTION = 807;
  public final static int WORKREQUEST_UNAUTHORIED = 808;
  
  public final static int REQUESTCANCEL_ID_EMPTY = 901;
  public final static int REQUESTCANCEL_ID_INVALID = 902;
  public final static int REQUESTCANCEL_CANCEL_ERROR = 903;
  public final static int REQUESTCANCEL_REQUEST_NOT_EXIST = 904;
  public final static int REQUESTCANCEL_EXCEPTION = 905;
  public final static int REQUESTCANCEL_UNAUTHORIED = 906;
  public final static HashMap<Integer, String> errMsg = new HashMap<Integer, String>() {
    {
      put(0, "");
      put(LOGIN_FAILED, "Invalid username or password!");
      put(LOGIN_EXCEPTION, "Please try again later!");
      
      put(REGISTER_DUPLICATE_USERNAME, "The username is already taken.");
      put(REGISTER_INSERT_ERROR, "Please try again later!");
      put(REGISTER_EXCEPTION, "Please try again later!");
      put(REGISTER_USERNAME_EMPTY, "Username cannot be empty!");
      put(REGISTER_PWD_EMPTY, "Password cannot be empty!");
      put(REGISTER_FNAME_EMPTY, "First Name cannot be empty.");
      put(REGISTER_LNAME_EMPTY, "Last Name cannot be empty!");
      
      put(SERVICE_TITLE_EMPTY, "Title cannot be empty!");
      put(SERVICE_PRICE_EMPTY, "Price cannot be empty!");
      put(SERVICE_PRICE_INVALID, "Price must be numeric");
      put(SERVICE_DESCRIPTION_EMPTY, "Description cannot be empty!");
      put(SERVICE_ENABLED_EMPTY, "Enabled cannot be empty!");
      put(SERVICE_ENABLED_OUTRANGE, "Enabled Invalid!");
      put(SERVICE_INSERT_ERROR, "Please try again later!");
      put(SERVICE_EDIT_ERROR, "Please try again later!");
      put(SERVICE_EXCEPTION, "Please try again later!");
      
      put(NEWTASK_TITLE_EMPTY, "Title cannot be empty!");
      put(NEWTASK_DESCRIPTION_EMPTY, "Description cannot be empty!");
      put(NEWTASK_INSERT_ERROR, "Please try again later!");
      put(NEWTASK_EXCEPTION, "Please try again later!");
      put(NEWTASK_UNAUTHORIZED, "UNAUTHORIZED!");
      
      put(CHPWD_SAME_PASSWORD, "Your new password is the same as old password.");
      put(CHPWD_CONFIRMPWD_ERROR, "Confirm password does not match");
      put(CHPWD_UNAUTHORIZED, "UNAUTHORIZED!");
      put(CHPWD_EXCEPTION, "Please try again later!");
      put(CHPWD_PASSWORD_MISMATCH, "Old password does not match");
      
      put(TASKAPPLY_TASKID_EMPTY, "Task does not exist");
      put(TASKAPPLY_PRICE_EMPTY, "Price cannot be empty!");
      put(TASKAPPLY_TASKID_INVALID, "Task does not exist");
      put(TASKAPPLY_PRICE_INVALID, "Price must be numeric!");
      put(TASKAPPLY_INSERT_ERROR, "Try again later!");
      put(TASKAPPLY_UPDATE_ERROR, "Try again later!");
      put(TASKAPPLY_EXCEPTION, "Please try again later!");
      put(TASKAPPLY_UNAUTHORIED, "UNAUTHORIZED!");
      put(TASKAPPLY_APPLY_EXIST, "You have already applied this task!");
      put(TASKAPPLY_TASK_NOT_EXIST, "Task does not exist");
      put(TASKAPPLY_TASK_CLOSED, "Task is closed.");
      

      put(TASKCANCEL_TASKID_EMPTY, "Task does not exist");
      put(TASKCANCEL_TASKID_INVALID, "Task does not exist");
      put(TASKCANCEL_CANCEL_ERROR, "Please try again later!");
      put(TASKCANCEL_OFFER_NOT_EXIST, "Offer does not exist");
      put(TASKCANCEL_EXCEPTION, "Please try again later!");
      put(TASKCANCEL_UNAUTHORIED, "UNAUTHORIZED!");
      
      put(WORKREQUEST_ID_EMPTY, "ID cannot be empty!");
      put(WORKREQUEST_ID_INVALID, "Invalid ID!");
      put(WORKREQUEST_SERVICE_NOT_EXIST, "Worker does not exist");
      put(WORKREQUEST_ALREADY_REQUEST, "You've requested!");
      put(WORKREQUEST_INSERT_ERROR, "Please try again later!");
      put(WORKREQUEST_EDIT_ERROR, "Please try again later!");
      put(WORKREQUEST_EXCEPTION, "Please try again later!");
      put(WORKREQUEST_UNAUTHORIED, "UNAUTHORIZED!");
      

      put(REQUESTCANCEL_ID_EMPTY, "ID cannot be empty!");
      put(REQUESTCANCEL_ID_INVALID, "Invalid ID!");
      put(REQUESTCANCEL_CANCEL_ERROR, "Please try again later!");
      put(REQUESTCANCEL_REQUEST_NOT_EXIST, "Request does not exist");
      put(REQUESTCANCEL_EXCEPTION, "Please try again later!");
      put(REQUESTCANCEL_UNAUTHORIED, "UNAUTHORIZED!");
    }};

    //put(, "");

    public static String getMsg(int errorCode) {
      String output = "";
      if(errorCode == 0)
        return output;
      else if(errMsg.containsKey(errorCode))
        output = "Error: " + errMsg.get(errorCode);
      else
        output = "Unknown Error";
      return output;
    }

}
