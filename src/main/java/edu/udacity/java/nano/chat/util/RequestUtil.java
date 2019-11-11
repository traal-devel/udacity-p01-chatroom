package edu.udacity.java.nano.chat.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.udacity.java.nano.chat.WebSocketConfig;

/**
 * (Experiment) Request Util.
 * 
 * @author traal-devel
 */
public final class RequestUtil {

  /* constants */
  private static final Logger logger = LogManager.getLogger(RequestUtil.class);
  
  /* member variables */
  
  /* constructors */
  private RequestUtil() {
    super();
  }
  
  /* methods */
  public static String getSessionValue(String strKey) {
    String retValue = null;

    HttpSession session = RequestUtil.getHttpSession();
    if (session != null) {
      retValue = (String)session.getAttribute(WebSocketConfig.PROP_KEY_ACCESS_TOKEN);
    }
    return retValue;
  }
  
  public static HttpSession getHttpSession() {
    HttpSession session = null;
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        session = request.getSession(false); 
    }
    return session;
  }
  
  public static void invalidateCurrentHttpSession() {
    HttpSession httpSession = RequestUtil.getHttpSession();
    if (httpSession != null 
        && httpSession.getAttribute(WebSocketConfig.PROP_KEY_ACCESS_TOKEN) != null) {
      httpSession.invalidate();
    }
  }
  
  public static void removeSessionValue(String...keys) {
    if(keys != null) {
      HttpSession session = RequestUtil.getHttpSession();
      if (session != null) {
        for(String key : keys) {
          try {
            session.removeAttribute(key);
          } catch (Exception ex) {
            logger.error(ex);
          }
        }
      }
    }
  }
}
