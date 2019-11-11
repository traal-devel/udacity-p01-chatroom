package edu.udacity.java.nano.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
//@EnableWebSocketMessageBroker // For Stomp
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /* constants */
  public static final String PROP_KEY_ACCESS_TOKEN = "ACCESS_TOKEN";
  public static final String PROP_KEY_USERNAME     = "USER_NAME";
  
  
  /* member variables */
  @Value("${udacity.websocket.host}")
  private String websocketHost;
  
  @Value("${udacity.websocket.port}")
  private String webSocketPort;
  
  @Value("${udacity.websocket.prot}")
  private String webSocketProt;
  
  @Value("${udacity.websocket.path}")
  private String webSocketPath;
  
  
  /* class variables */
  public static ObjectMapper JSON_MAPPER = new ObjectMapper() {
    private static final long serialVersionUID = -7275728383291379718L;
    {
      registerModule(new Jdk8Module());
      registerModule(new JavaTimeModule());
      configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
  };
  
  /* constructors */
  public WebSocketConfig() {
    super();
  }
  
  
  /* methods */
  
  public String getWebsocketHost() {
    return this.websocketHost;
  }
  
  public String getWebsocketPort() {
    return this.webSocketPort;
  }
  
  public String getWebsocketProt() {
    return this.webSocketProt;
  }
  
  public String getWebsocketURI() {
    return this.webSocketProt + "://" 
              + this.websocketHost 
              + ":" + this.webSocketPort
              + this.webSocketPath;
  }
  
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
  
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new WebSocketAccessTokenArgumentResolver());
  }
  
  // For Stomp -> not used at the moment.
  
//  @Override
//  public void configureMessageBroker(MessageBrokerRegistry registry) {
//    // TODO Auto-generated method stub
//    WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
//  }
//  
//  @Override
//  public void registerStompEndpoints(StompEndpointRegistry registry) {
//    // TODO Auto-generated method stub
//    WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
//  }
}
