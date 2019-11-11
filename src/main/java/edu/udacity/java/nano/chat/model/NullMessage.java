package edu.udacity.java.nano.chat.model;

import com.fasterxml.jackson.databind.node.NullNode;

/**
 * This singleton value class is used to contain explicit Message null
 * value.
 * 
 * @see NullNode Similar concept can be found here.
 * @author traal-devel
 */
public final class NullMessage extends Message {

  
  /* constants */
  // Just need a fly-weight singleton
  /** Null representation of the message classe. */
  public final static NullMessage instance = new NullMessage();
  
  
  /* constructors */
  public NullMessage() {
    super();
  }
  
  
  /* methods */
  @Override
  public void setUsername(String username) {
  }
  
  @Override
  public void setMsg(String msg) {
  }
  
  public static NullMessage getInstance() { 
    return instance; 
  }
  
}
