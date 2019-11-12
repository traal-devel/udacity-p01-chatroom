package edu.udacity.java.nano.chat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;

import edu.udacity.java.nano.chat.ex.AppCodeException;
import edu.udacity.java.nano.chat.util.RequestUtil;

/**
 *  Dummy user info service.
 * 
 * @author traal-devel
 */
@Service
public class UserInfoService {

  
  /* member variables */
  private BidiMap<String, String>  userAccessTokens = new DualHashBidiMap<>() ;
  
  
  /* constructors */
  /**
   * Default constructor.
   */
  public UserInfoService() {
    super();
  }
  
  /* methods */
  /**
   * Checks if the given username already was taken by an other person.
   * 
   * @param username
   * @return
   */
  public boolean isUsernameAssigned(String username) {
    return this.userAccessTokens.containsValue(username);
  }

  /**
   * Generates an access token, which is an UUID.
   * 
   * @param username String
   * @return String - Unique uuid.
   */
  public String generateAccessToken(String username) {
    String accessToken = UUID.randomUUID().toString();
    
    this.userAccessTokens.putIfAbsent(accessToken, username);
    String accessTokenInMap = this.userAccessTokens.getKey(username);
    
    if (!accessToken.equals(accessTokenInMap)) {
      throw AppCodeException.USER_NAME_ALREADY_ASSIGNED;
    }
    
    return accessTokenInMap;
  }
  
  /**
   * Checks if access token is valid / exists.
   * 
   * @param accessToken String
   * @return boolean
   */
  public boolean isAccessTokenValid(String accessToken) {
    return this.userAccessTokens.containsKey(accessToken);
  }
  
  /**
   * Gets the username by using given parameter.
   * 
   * @param accessToken String
   * @return String
   */
  public String getUsernameBy(String accessToken) {
    return this.userAccessTokens.get(accessToken);
  }
  
  /**
   * Invalidates the given access token, which is equal to an logout.
   * 
   * @param strAccessToken String
   * @return String - Removed access token.
   */
  public String invalidateAccessToken(String strAccessToken) {
    RequestUtil.invalidateCurrentHttpSession();
    return this.userAccessTokens.remove(strAccessToken);
  }
  
  /**
   * Removes all access token. User with a valid session will be auto. 
   * logged in.
   */
  public void removeAllAccessTokens() {
    this.userAccessTokens.clear();
  }
  
  public List<String> getAllUsernames() {
    List<String> userList = new ArrayList<String>(this.userAccessTokens.values());
    Collections.sort(userList);
    return userList;
  }
}
