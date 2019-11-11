package edu.udacity.java.nano.chat.ctrl;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import edu.udacity.java.nano.chat.ex.AppCodeException;

@ControllerAdvice
public class GlobalExceptionHandler {

  
  /* member variables */
  
  /* constructors */
  
  /* methods */
  @ExceptionHandler(
      value = AppCodeException.class
  )
  public ModelAndView handlerApplicationException(AppCodeException appExCode) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("errorCode", appExCode.errorCode);
    modelAndView.addObject("message", appExCode.getMessage());
    modelAndView.setViewName(appExCode.errorPage);
    
    return modelAndView;
  }
  
  @ExceptionHandler(
      value = Exception.class
  )
  public ModelAndView handlerException(AppCodeException appExCode) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("errorCode", appExCode.errorCode);
    modelAndView.addObject("message", appExCode.getMessage());
    modelAndView.setViewName(appExCode.errorPage);
    
    return modelAndView;
  }

}