package edu.udacity.java.nano.chat;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.udacity.java.nano.chat.model.AbstractMessage;
import edu.udacity.java.nano.chat.model.Message;
import edu.udacity.java.nano.chat.model.UpdateMessage;
import edu.udacity.java.nano.chat.service.UserInfoService;
import edu.udacity.java.nano.chat.util.MessageDecoder;
import edu.udacity.java.nano.chat.util.MessageEncoder;

/**
 * WebSocket Server Implementation without Sock / STOMP.
 * <p>
 * <h2>Information</h2>
 * Apparently we should use for this task not the STOMP implementation.
 * We have to implement the subscribe / notify mechanism with the method
 * sendMessageToAll. 
 * </p>
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session WebSocket Session
 */

@Component
@ServerEndpoint(
    value = "/chat",                          // Endpoint Registry -> :TODO: Refactor with application.properties value
    encoders = {
        MessageEncoder.class
    },
    decoders = {
        MessageDecoder.class
    },
    configurator = WebSocketHttpSessionConfigurator.class
)
public class WebSocketChatServer {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger(WebSocketChatServer.class);
  
  
  /* class variables */
  /** All chat sessions. */
  private static Map<String, Session>  s_onlineSessions = new ConcurrentHashMap<>();
  
  private static UserInfoService       s_userInfoService;

  /* member variables */
  
  
  /* constructors */
  public WebSocketChatServer() {
    super();
  }
  
  @Autowired
  public WebSocketChatServer(UserInfoService userInfoService){
    // :TODO: Refactor this. Workaround.
    // td, 10.11.2019: The autowired stuff is not working with 
    // the websocket api. Apparently WebSocketChatServer class is not managed
    // by the spring di container and the class is instantiated over and over.
    // It exists the class SpringConfigurator but it does not work with 
    // @Component annotation ...
    // Use class variables to reference the bean.
    WebSocketChatServer.s_userInfoService = userInfoService;
  }
  
  /* methods */
  /**
   * Open connection, 
   * 1) add session, 
   * 2) add user.
   */
  @OnOpen
  public void onOpen(
      Session session
  ) throws IOException, EncodeException {

    logger.debug("onOpen invoked - " 
        + " Session: " + session.getId()
        + " Params: " + session.getRequestParameterMap());

    String accessToken = this.checkAccessTokenInEx(session);
    WebSocketChatServer.s_onlineSessions.put(session.getId(), session);
    this.sendWelcomeMessage(accessToken, session);
    this.sendUpdateMessageToAll();
  }
  
  /**
   * Checks the access token in the given session.
   * 
   * @param session Session.
   * @throws IOException - Thrown if access token not valid.
   * @return String - current access token 
   */
  private String checkAccessTokenInEx(
      Session session
  ) throws IOException {

    String accessToken = (String)session.getUserProperties()
                                  .get(WebSocketConfig.PROP_KEY_ACCESS_TOKEN);
                                
    if (!s_userInfoService.isAccessTokenValid(accessToken)) {
      // If access token not valid then we close the connection and notify the client.
      session.close(
          new CloseReason(
              CloseReason.CloseCodes.CANNOT_ACCEPT, 
              "ERROR: Access-token not valid. You will be redirectd to the login page"));
    }
    
    return accessToken;
  }
  
  /**
   * Send message, 
   * 1) get username and session, 
   * 2) send message to all.
   * <p>
   * Apparently OnMessage annotation is only allowed once per handler...  
   * </p>
   */
  @OnMessage
  public void onChatMessage(
      Session session, 
      Message message
  ) throws IOException {
    
    logger.debug("onMessage invoked - jsonStr: " + message + ", Session: " + session.getId());
    
    // DONE: add send message.
    if ("LEAVE".equalsIgnoreCase(message.type)) {
      this.logoutUser(session);
    } else {
      this.sendMessageToAll(message);
    }
    
  }
  
  /**
   * Close connection.
   * <p>
   * 1) remove session, <br />
   * 2) update user.
   * </p>
   * <p>
   * On close is also called by a browser-refresh. In that case the websocket 
   * session would get lost. For this reason we use the http session to 
   * reconnect the user again via a filter.
   * </p>
   */
  @OnClose
  public void onClose(
      Session session
  ) {
    // DONE: add close connection. --> Done by LeaveMessage
    WebSocketChatServer.s_onlineSessions.remove(session.getId());
    
    logger.debug("onClose invoked - Session: {}", session.getId());
  }

  /**
   * Print exception.
   */
  @OnError
  public void onError(Session session, Throwable error) {
    
    logger.error("onError invoked - Error: " + error.getMessage() 
                             + ", Session: " + session.getId());
    
  }

  /**
   * @param session
   * @throws IOException
   */
  public void logoutUser(
      Session session
  ) throws IOException {
    String accessToken = (String)session.getUserProperties()
                                .get(WebSocketConfig.PROP_KEY_ACCESS_TOKEN);
    
    s_userInfoService.invalidateAccessToken(accessToken);
    session.close();
    this.sendUpdateMessageToAll();
  }
  
  /**
   * Sends a welcome message to all user with a current user-list.
   * 
   * @param accessToken String
   * @param session Session
   * @throws IOException
   * @throws EncodeException
   */
  private void sendWelcomeMessage(
      String accessToken, 
      Session session
  ) throws IOException, EncodeException {
  
    String userName = s_userInfoService.getUsernameBy(accessToken);
    
    Message msg = new Message();
    msg.setUsername(userName);
    msg.setMsg("++++ Welcome to chat ++++");
    
    session.getBasicRemote().sendObject(msg); 
    
  }
  
  private void sendUpdateMessageToAll() {
    UpdateMessage updateMessage = new UpdateMessage();
    List<String> userList = s_userInfoService.getAllUsernames();
    updateMessage.setUserList(userList);
    
    this.sendMessageToAll(updateMessage);
  }
  
  private void sendMessageToAll(
      AbstractMessage msg
  ) {
    
    logger.debug("sendMessageToAll invoked: " + msg);
    
    for (Map.Entry<String,Session> entSession : 
            WebSocketChatServer.s_onlineSessions.entrySet()) {
      Session session = entSession.getValue();
      
      if (session.isOpen()) {
        try {
          session.getBasicRemote().sendObject(msg);
        } catch(Exception ex) {
          logger.error(ex);
        }
      }
    }
  }


}

