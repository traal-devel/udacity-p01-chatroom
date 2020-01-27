package edu.udacity.java.nano;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

// td, 10.11.2019: Use this or else ServerEndpointExporter bean can not be 
// intialiazed by spring di container.
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT                    
)
public class WebDriverTest {

  /* constants */
  private static final Logger logger = LogManager.getLogger(WebDriverTest.class);
  
  /* class variables */
  private static WebDriver browser = null;
  
  /* member variables */
  @LocalServerPort
  private Long localPort;
  
  
  /* constructors */
  public WebDriverTest() {
    super();
  }
  
  /* methods */
  @Before
  public void initBrowser() {
    System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    browser = new ChromeDriver();
  }
  
  @Test
  public void testBasicLocalPort() {
    assertNotNull(this.localPort);
    logger.info("Server port is " + this.localPort);
  }
  
  @Test
  public void testWebDriverBasic() {
    browser.get("http://localhost:" + this.localPort);

    assertTrue(true); // Browser reference could be used.
    
  }
  
  @After
  public void destroyBrowser() {
    browser.close();
  }

}
