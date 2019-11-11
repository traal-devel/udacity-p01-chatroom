package edu.udacity.java.nano;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.websocket.Session;

import org.apache.tomcat.websocket.WsSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.udacity.java.nano.chat.WebSocketChatServer;
import edu.udacity.java.nano.chat.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT                    
)
public class UserInfoServiceTest {

  
  /* member variables */
  @Autowired
  private UserInfoService userInfoService;
  
  @Autowired
  private WebSocketChatServer chatServer;
  
  /* methods */
  @Test
  public void testBasicNotNull() {
    assertNotNull(this.userInfoService);
    assertNotNull(this.chatServer);
  }
  
  @Test
  public void testAcessTokenInvalid() {
    assertFalse(this.userInfoService.isAccessTokenValid("not-existing"));
  }
  
  
  
}
