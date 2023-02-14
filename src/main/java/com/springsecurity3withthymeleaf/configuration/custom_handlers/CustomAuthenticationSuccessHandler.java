package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.FailureAttemptService;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.LogInOutHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component( "customAuthenticationSuccessHandler" )
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  @Autowired
  private LogInOutHistoryService logInOutHistoryService;
  @Autowired
  private HandlerCommonService handlerCommonService;
  @Autowired
  private FailureAttemptService failureAttemptService;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
String username = authentication.getName();
    logInOutHistoryService.persist(handlerCommonService.logInOutHistory(request,username));


    clearAuthenticationAttributes(request);
//    after successfully login all failure attempts are removed
    failureAttemptService.deleteByUsername(username);


    HttpSession session = request.getSession();
    String previousPage = (String) session.getAttribute("previousPage");

    if ( previousPage != null && ! previousPage.equals("/login") ) {
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
