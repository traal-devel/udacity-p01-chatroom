package edu.udacity.java.nano;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
  // td, 10.11.2019: Use this or else ServerEndpointExporter bean can not be 
  // intialiazed by spring di container.
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT                    
)
public class WebDriverTest {

  /* constants */
  private static final Logger logger = LogManager.getLogger(WebDriverTest.class);
  
  /* class variables */
  private static WebDriver s_browser = null;
  
  /* member variables */
  @LocalServerPort
  private Long localPort;
  
  
  /* constructors */
  public WebDriverTest() {
    super();
  }
  
  /* methods */
  @BeforeClass
  public static void initBrowser() {
  // s_browser = new ChromeDriver();
  }
  
  @Test
  public void testBasic() {
    assertNotNull(this.localPort);
    logger.info("Server port is " + this.localPort);
  }
  
  @Test
  public void site_header_is_on_home_page() {
    System.setProperty("webdriver.chrome.driver", "data/webdriver/chromedriver");
    
    s_browser.get("http://localhost:8080");
    s_browser.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
    WebElement href = s_browser.findElement(By.xpath("//a[@href='/beta/login']"));
    assertTrue((href.isDisplayed()));
    
  }
  
  @AfterClass
  public void destroyBrowser() {
    s_browser.close();
  }

}
