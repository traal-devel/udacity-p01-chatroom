package edu.udacity.java.nano.chat.util;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import edu.udacity.java.nano.chat.model.Message;

/**
 * Implementation of a websocket message decoder.
 * 
 * @author traal-devel
 */
public class MessageDecoder implements Decoder.Text<Message> {
  
  
  /* member variables /

  /* methods */
  @Override 
  public Message decode (String src) throws DecodeException {
    return DTOUtil.castToObject(src, Message.class);
  }

  /**
   * Check if we want to try to decode the given parameter.
   * 
   * @param src String
   */
  @Override 
  public boolean willDecode (String src) {
     // return true if we want to decode this String into a Message instance
    return DTOUtil.hasType(src, Message.MSG_TYPE_LIST);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    // not used
  }

  @Override
  public void destroy() {
    // not used.
  }
  
}
