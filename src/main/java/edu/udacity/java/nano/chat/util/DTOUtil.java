package edu.udacity.java.nano.chat.util;

import java.util.List;

import javax.websocket.EncodeException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;

import edu.udacity.java.nano.chat.WebSocketConfig;

public class DTOUtil {

  /* constant */
  private static final Logger logger = LogManager.getLogger(DTOUtil.class);
  
  
  /* methods */
  public static <T> T castToObject(String src, Class<T> clazz) {
    T retValue = null;
    try {
      JsonNode node = DTOUtil.parseJson(src); 
      retValue = WebSocketConfig.JSON_MAPPER.<T>treeToValue(node, clazz);
    } catch(Exception ex) {
      logger.error("Cast to object failed.", ex);
    }
    return retValue;
  }
  
  public static String castToString(Object obj) throws EncodeException {
    String strReturnValue = null;
    try {
      strReturnValue = WebSocketConfig.JSON_MAPPER.valueToTree(obj).toString();
    } catch (Exception ex) {
      logger.error("Object could not be encoded", ex);
      throw new EncodeException(obj, "could not be encoded.", ex);
    }
    return strReturnValue;
  }
  
  public static JsonNode parseJson(String jsonContent) {
    JsonNode jsonNode = null;
    try {
      jsonNode = WebSocketConfig.JSON_MAPPER.readTree(jsonContent);
    } catch(Exception ex) {
      jsonNode = NullNode.instance;
      
    }
    
    return jsonNode;
  }
  
  public static boolean hasType(String src, String type) {
    JsonNode node = DTOUtil.parseJson(src);
    
    return node.has("type") && type.equalsIgnoreCase(node.get("type").asText());
  }
  
  public static boolean hasType(String src, List<String> typeList) {
    JsonNode node = DTOUtil.parseJson(src);
    
    return node.has("type") && typeList.indexOf(node.get("type").asText()) != -1;
  }
}
