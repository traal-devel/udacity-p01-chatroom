package edu.udacity.java.nano.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * (Experiment) WebSocketHttpSessionConfigurator used to check if an valid
 * access_token / http-session exists. 
 * Try to implement a simple security layer for the websocket.
 * 
 * @author traal-devel
 */
public class WebSocketHttpSessionConfigurator 
                                extends ServerEndpointConfig.Configurator  {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger(WebSocketHttpSessionConfigurator.class);
  
  /* member variables */
  
  
  /* constructors */
  public WebSocketHttpSessionConfigurator() {
    super();
  }
  
  
  /* methods */
  @Override
  public void modifyHandshake(
      ServerEndpointConfig sec, 
      HandshakeRequest request, 
      HandshakeResponse response
  ) {
    logger.debug("modifyHandshake() invoked.");
    
    HttpSession session = (HttpSession)request.getHttpSession();
    if (session != null) {
      // :TODO: If access token is not exist then return an exception
      sec.getUserProperties().put(
          WebSocketConfig.PROP_KEY_ACCESS_TOKEN, 
          session.getAttribute(WebSocketConfig.PROP_KEY_ACCESS_TOKEN));
    }
    
  }
}