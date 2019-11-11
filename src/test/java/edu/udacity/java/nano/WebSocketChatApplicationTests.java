package edu.udacity.java.nano;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import edu.udacity.java.nano.chat.WebSocketConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(
  // td, 10.11.2019: Use this or else ServerEndpointExporter bean can not be 
  // intialiazed by spring di container.
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT                    
)
public class WebSocketChatApplicationTests {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger(WebSocketChatApplicationTests.class);
  
  /* member variables */
  @Autowired
  private ApplicationContext  context;
  
  @Autowired
  private WebSocketConfig     webSocketConfig;
  
  
  /* constructors */
  
  /* methods */
  
  @Test
  public void contextLoads() {
    assertNotNull("Application context could not be loaded", this.context);
  }
  
  @Test
  public void testBasic() {
    assertNotNull(this.webSocketConfig);
  }
  
  @Test
  public void testConfigProps() {
    assertNotNull(this.webSocketConfig.getWebsocketHost());
    assertNotNull(this.webSocketConfig.getWebsocketPort());
    assertNotNull(this.webSocketConfig.getWebsocketURI());
    
    logger.info("Host: " + this.webSocketConfig.getWebsocketURI());
  }
  
  


}
