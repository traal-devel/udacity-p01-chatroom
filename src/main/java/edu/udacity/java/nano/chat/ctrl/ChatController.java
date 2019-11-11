package edu.udacity.java.nano.chat.ctrl;

import java.net.UnknownHostException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.udacity.java.nano.chat.AccessToken;
import edu.udacity.java.nano.chat.WebSocketConfig;
import edu.udacity.java.nano.chat.ex.AppCodeException;
import edu.udacity.java.nano.chat.service.UserInfoService;

@RestController
public class ChatController {

  
  /* member variables */
  @Autowired
  private UserInfoService   userInfoService;
  
  @Autowired
  private WebSocketConfig   webSocketConfig;
  
  
  /* constructors */
  public ChatController() {
    super();
  }
  
  
  /* methods */
  /**
   * Login Page
   */
  @GetMapping("/")
  public ModelAndView login() {
    return new ModelAndView("/login.html");
  }

  /**
   * Chatroom Page
   */
  /**
   * Login GET Request
   * 
   * @param accessToken
   * @return
   * @throws UnknownHostException
   */
  @GetMapping("/index")
  public ModelAndView indexGet(
      @AccessToken String accessToken,
      HttpSession httpSession
  ) throws UnknownHostException {
    ModelAndView modelAndView = new ModelAndView("/chat.html");
    
    // DONE: add code for login to chatroom -> See POST-method below.
    String webSocketUrl = this.webSocketConfig.getWebsocketURI();
    String strUsername = this.userInfoService.getUsernameBy(accessToken);
    modelAndView.addObject("username", strUsername);
    modelAndView.addObject("webSocketUrl", webSocketUrl);
    
    return modelAndView;
  }
  
  /**
   * Login POST request
   * 
   * @param strUsername String
   * @return ModelAndview
   * @throws UnknownHostException
   */
  @PostMapping("/login")
  public ModelAndView indexPost(
      @RequestParam(value = "username") String strUsername,
      HttpSession httpSession
  ) throws UnknownHostException {
    
    // Validation: Throws exception, if not valid
    this.checkUsernameValidEx(strUsername); 
    
    this.registerAccessToken(httpSession, strUsername);
    
    return new ModelAndView("redirect:/index");
  }
  
  @GetMapping(value = "/error/{errorCode}/")
  public ModelAndView errorCode(
      @PathVariable(value="errorCode", required = true) String strErrorCode
  ) {
    AppCodeException appCodeEx = AppCodeException.getInstanceBy(strErrorCode);
    throw appCodeEx;
  }
  
  /**
   * (Experiment)
   * Registers an accesstoken for a given user.
   * 
   * @param httpSession HttpSession
   * @param strUsername String
   */
  private void registerAccessToken(HttpSession httpSession, String strUsername) {
    String accessToken = userInfoService.generateAccessToken(strUsername);
    httpSession.setAttribute(WebSocketConfig.PROP_KEY_ACCESS_TOKEN, accessToken);
    httpSession.setAttribute(WebSocketConfig.PROP_KEY_USERNAME, strUsername);
  }
  
  /**
   * Checks if the username is valid.
   * <p>
   * <ul>
   *   <li>Check if username is not empty.</li>
   *   <li>Check if username is not already taken by an other person</li>
   * </ul>
   * </p>
   * 
   * @param strUsername String
   */
  private void checkUsernameValidEx(String strUsername) {
    if (!StringUtils.hasText(strUsername)) {
      throw AppCodeException.USER_NAME_EMPTY;
    } else if(strUsername.length() < 6 || strUsername.length() > 20 ) {
      throw AppCodeException.USER_MAX_MIN_CHAR;
    } else if(this.userInfoService.isUsernameAssigned(strUsername)) {
      throw AppCodeException.USER_NAME_ALREADY_ASSIGNED;
    } else {
    } // ok, nothing to do
  }
}
