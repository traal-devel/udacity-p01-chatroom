package edu.udacity.java.nano.chat;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import edu.udacity.java.nano.chat.util.RequestUtil;

/**
 * (Experiment)
 * Implementation of an argument resolver to retrieve the current accesstoken
 * from the session.
 * 
 * @author traal-devel
 */
public class WebSocketAccessTokenArgumentResolver implements HandlerMethodArgumentResolver{

  
  /* member variables */
  
  /* constructors */
  /**
   * Constructor for {@link WebSocketAccessTokenArgumentResolver}. 
   * 
   */
  public WebSocketAccessTokenArgumentResolver() {
    super();
  }
  

  /* methods */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(AccessToken.class);
  }
  

  @Override
  public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
    return RequestUtil.getSessionValue(WebSocketConfig.PROP_KEY_ACCESS_TOKEN);
  }
}