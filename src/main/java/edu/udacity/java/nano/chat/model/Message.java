package edu.udacity.java.nano.chat.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * WebSocket message model
 */
public class Message extends AbstractMessage {
  
  /* constants */
  public static final String MSG_SPEAK_TYPE = "SPEAK";
  public static final String MSG_LEAVE_TYPE = "LEAVE";

  public static final List<String> MSG_TYPE_LIST  = 
                                        Collections.unmodifiableList(
                                            Arrays.asList(
                                                MSG_SPEAK_TYPE,
                                                MSG_LEAVE_TYPE
                                        ));
  
  /* member variables */
  private String username = null;
  private String msg      = null;
  
  /* constructors */
  public Message() {
    super(Message.MSG_SPEAK_TYPE);
  }
  
  protected Message(String type) {
    super(type);
  }

  // DONE: add message model.
  
  /* methods */
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getMsg() {
    return msg;
  }
  
  public void setMsg(String msg) {
    this.msg = msg;
  }
  

}
