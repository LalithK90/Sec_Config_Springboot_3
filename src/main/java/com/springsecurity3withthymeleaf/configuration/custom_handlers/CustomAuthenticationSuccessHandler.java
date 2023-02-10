package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.FailureAttemptService;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.LogInOutHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component( "customAuthenticationSuccessHandler" )
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  protected final Log logger = LogFactory.getLog(this.getClass());
  @Autowired
  private LogInOutHistoryService logInOutHistoryService;
  @Autowired
  private HandlerCommonService handlerCommonService;
  @Autowired
  private FailureAttemptService failureAttemptService;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    LogInOutHistory logInOutHistory = new LogInOutHistory();
    // Extract information from the HttpServletRequest object
    logInOutHistory.setUsername(authentication.getName());
    logInOutHistory.setIpAddress(request.getRemoteAddr());
    logInOutHistory.setLanguage(request.getHeader("Accept-Language"));
    logInOutHistory.setRequestMethod(request.getMethod());
    logInOutHistory.setLoginTime(LocalDateTime.now());
    logInOutHistory.setLogoutTime(null);
    logInOutHistory.setBrowser(handlerCommonService.extractBrowser(request.getHeader("User-Agent")));
    logInOutHistory.setOperatingSystem(handlerCommonService.extractOperatingSystem(request.getHeader("User-Agent")));
    logInOutHistory.setDevice(handlerCommonService.extractDevice(request.getHeader("User-Agent")));

    logInOutHistoryService.persist(logInOutHistory);


    clearAuthenticationAttributes(request);
//    after successfully login all failure attempts are removed
    failureAttemptService.deleteByUsername(authentication.getName());
    logger.info("successfully login");
    HttpSession session = request.getSession();
    String previousPage = (String) session.getAttribute("previousPage");
    System.out.println(previousPage + " in csah");
    if ( previousPage != null && ! previousPage.equals("/login") ) {
      session.removeAttribute("previousPage");
      System.out.println("insside " + previousPage);
      response.sendRedirect( previousPage);
    } else {
      response.sendRedirect("/mainWindow");
    }
  }

  /**
   * Removes temporary authentication-related data which may have been stored in the session
   * during the authentication process.
   */
  protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);

    if ( session == null ) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }


}
