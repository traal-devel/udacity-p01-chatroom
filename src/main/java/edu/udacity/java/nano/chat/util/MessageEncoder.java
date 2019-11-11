package edu.udacity.java.nano.chat.util;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import edu.udacity.java.nano.chat.model.Message;

/**
 * Implementation of a websocket message encoder.
 * 
 * @author traal-devel
 */
public class MessageEncoder implements Encoder.Text<Message> {
  
  
  /* methods */
  @Override
  public String encode(Message obj) throws EncodeException {
    return DTOUtil.castToString(obj);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    // not used
  }

  @Override
  public void destroy() {
    // not used
  }
}
