package edu.udacity.java.nano.chat.model;

import java.util.List;

public class UpdateMessage extends Message {

  
  /* constants */
  public static final String MSG_TYPE = "UPDATE";
  
  
  /* member variables */
  public List<String> userList = null;
  
  
  /* constructors */
  public UpdateMessage() {
    super(UpdateMessage.MSG_TYPE);
  }
  
  
  /* methods */
  public void setUserList(List<String> userList) {
    this.userList = userList;
  }
  
  public List<String> getUserList() {
    return this.userList;
  }
  
}
