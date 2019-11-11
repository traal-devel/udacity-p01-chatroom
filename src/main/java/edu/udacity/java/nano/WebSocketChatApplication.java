package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan       // td, 10.11.2019: Used for ChatFilter.
public class WebSocketChatApplication {

  
  /* member variables */
  
  /* constructors */
  
  /* methods */
  public static void main(String[] args) {
    SpringApplication.run(WebSocketChatApplication.class, args);
  }
  
  

}
