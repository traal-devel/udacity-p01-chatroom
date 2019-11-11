package edu.udacity.java.nano.chat.ctrl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import edu.udacity.java.nano.chat.WebSocketConfig;
import edu.udacity.java.nano.chat.ex.AppCodeException;
import edu.udacity.java.nano.chat.service.UserInfoService;

/**
 * Implementation of a simple Authenticatior for path / index.
 * <p>
 * Seems that &#64;ControlerAdvice / ExceptionHandler is not use, when an  
 * exception is thrown in a filter. For this reason, we use simply the
 * path /error/{errorCode}
 * </p>
 * 
 * Tried, but not worked:
 * - https://stackoverflow.com/questions/43999877/springboot-filters-exception-handler
 * - https://stackoverflow.com/questions/50542300/throw-an-exception-in-a-webfilter-class-and-handle-it-in-a-controlleradvice-clas
 * 
 * @author traal-devel
 */
@WebFilter(
    filterName="chatFilter", 
    urlPatterns="/index"
)
public class AccessTokenFilter implements Filter {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger(AccessTokenFilter.class);
  
  /* member variables */
  @Autowired
  private UserInfoService  userInfoService;
  
  /* constructors */
  public AccessTokenFilter() {
    super();
  }
  
  /* methods */
  @Override
  public void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse, 
      FilterChain filterChain
  ) throws IOException, ServletException {
    
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    
    String token = this.getAccessToken(request);
    if (!StringUtils.hasText(token)) {
//      throw new ApplicationException("An access token is required to connect");
      logger.error("User not authorized. No access-token found.");
      response.sendRedirect("/error/" + AppCodeException.UNAUTHORIZED_USER.errorCode + "/");
      return;
    } else if (!this.userInfoService.isAccessTokenValid(token)) {
      logger.error("Access token invalid: " + token);
      response.sendRedirect("/error/" + AppCodeException.USER_ACCESS_TOKEN_INVALID.errorCode + "/");
      return;
    } else {
    } // nothing to do
    
    filterChain.doFilter(servletRequest, servletResponse);
    
  }
  
  private String getAccessToken(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    return session != null ? 
              (String)session.getAttribute(WebSocketConfig.PROP_KEY_ACCESS_TOKEN) :
              null;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // not used --> embedded tomcat 8. In 9 not used anymore.
    
  }

  @Override
  public void destroy() {
    // not used --> embedded tomcat 8. In 9 not used anymore.
  }  
}



