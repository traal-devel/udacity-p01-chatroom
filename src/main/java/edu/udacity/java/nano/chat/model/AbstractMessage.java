package edu.udacity.java.nano.chat.model;


public class AbstractMessage {

  
  /* member variables */
  private String username       = null;
  public final String type;
  
  
  
  /* constructors */
  public AbstractMessage(String type) {
    super();
    this.type = type;
  }
  
  /* methods */
  /* methods */
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getType() {
    return type;
  }
  
}
