package edu.udacity.java.nano.chat;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.udacity.java.nano.chat.util.RequestUtil;

/**
 * (Experiment)
 * Implementation of an argument resolver to retrieve the current accesstoken
 * from the session.
 * 
 * @author traal-devel
 */
public class WebMVCAccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

  
  /* member variables */
  
  /* constructors */
  /**
   * Constructor for {@link WebMVCAccessTokenArgumentResolver}. 
   * 
   */
  public WebMVCAccessTokenArgumentResolver() {
    super();
  }
  

  /* methods */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(AccessToken.class);
  }
  

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return RequestUtil.getSessionValue(WebSocketConfig.PROP_KEY_ACCESS_TOKEN);
  }
}