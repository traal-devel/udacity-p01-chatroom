package edu.udacity.java.nano.chat.ex;

import java.util.HashMap;
import java.util.Map;

/**
 * Application Code Exception implementation.
 * <p>
 * Test / Proof of concept.
 * </p>
 * 
 * @author traal-devel
 */
public final class AppCodeException extends AppException {

  /* constants */
  /** Added by eclipse */
  private static final long serialVersionUID = 1741928639340846393L;

  private static final Map<String, AppCodeException> ERROR_MAP = new HashMap<>();
  
  
  
  public static final AppCodeException UNAUTHORIZED_USER = 
      new AppCodeException("0001", "/login.html", "Unauthorized: Invalid access token.");
  
  public static final AppCodeException USER_NAME_EMPTY = 
      new AppCodeException("0010", "/login.html", "User name is empty. Please enter a user name.");
 
  public static final AppCodeException USER_NAME_ALREADY_ASSIGNED = 
      new AppCodeException("0011", "/login.html", "User name is already taken. Please choose an other name.");
  
  public static final AppCodeException USER_ACCESS_TOKEN_INVALID = 
      new AppCodeException("0012", "/login.html", "User access token is invalid.");
  
  public static final AppCodeException USER_MAX_MIN_CHAR = 
      new AppCodeException("0013", "/login.html", "User name has to be between 6 and 20 characters.");
  
  public static final AppCodeException UNDEFINED = 
      new AppCodeException("9999", "Undefined error");
  
  
  /* member variables */
  public final String errorCode; 
  
  public final String errorPage;
  
  
  /* constructors */
  private AppCodeException(String errorCode, String message) {
    this(errorCode, "/error.html", message);
  }
  
  private AppCodeException(String errorCode, String errorPage, String message) {
    super(message);
    
    this.errorCode = errorCode;
    this.errorPage = errorPage;
    AppCodeException.ERROR_MAP.put(this.errorCode, this);
  }
  

  /* class variables */
  public static AppCodeException getInstanceBy(String strErrorCode) {
    return AppCodeException
            .ERROR_MAP
            .getOrDefault(strErrorCode, AppCodeException.UNDEFINED);
  }
}
