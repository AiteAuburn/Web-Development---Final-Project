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
  public final static int CHPWD_SAME_PASSWORD = 501;
  public final static int CHPWD_CONFIRMPWD_ERROR = 502;
  public final static int CHPWD_UNANTHORIZED = 503;
  public final static int CHPWD_EXCEPTION = 504;
  public final static int CHPWD_PASSWORD_MISMATCH = 505;
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
      put(CHPWD_SAME_PASSWORD, "Your new password is the same as old password.");
      put(CHPWD_CONFIRMPWD_ERROR, "Confirm password does not match");
      put(CHPWD_UNANTHORIZED, "Unanthorized Action!");
      put(CHPWD_EXCEPTION, "Please try again later!");
      put(CHPWD_PASSWORD_MISMATCH, "Old password does not match");
    }};

    //put(, "");

    public static String getMsg(int errorCode) {
      String output = "";
      if(errMsg.containsKey(errorCode))
        output = "Error: " + errMsg.get(errorCode);
      else
        output = "Unknown Error";
      return output;
    }

}
