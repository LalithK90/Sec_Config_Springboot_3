package com.springsecurity3withthymeleaf.configuration.custom_handlers;

import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.FailureAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Autowired
  private HandlerCommonService handlerCommonService;
  @Autowired
  private FailureAttemptService failureAttemptService;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException {
    List< FailureAttempt > failureAttempts = failureAttemptService.findByUsername(request.getParameter("username"));
//    check how many failure attempts record on the system if count is more than 5
    if ( failureAttempts.size() > 5 ) {
      //    user redirect to password reset page
      response.sendRedirect("/forgottenPassword");
    } else {//    if not save new failure attempt
      FailureAttempt userSessionLog = new FailureAttempt();
      userSessionLog.setUsername(request.getParameter("username"));
      userSessionLog.setIpAddress(request.getRemoteAddr());
      userSessionLog.setLanguage(request.getHeader("Accept-Language"));
      userSessionLog.setRequestMethod(request.getMethod());
      userSessionLog.setTriedDateTime(LocalDateTime.now());
      userSessionLog.setBrowser(handlerCommonService.extractBrowser(request.getHeader("User-Agent")));
      userSessionLog.setOperatingSystem(handlerCommonService.extractOperatingSystem(request.getHeader("User-Agent")));
      userSessionLog.setDevice(handlerCommonService.extractDevice(request.getHeader("User-Agent")));

      failureAttemptService.persist(userSessionLog);
      // Log the authentication failure

      response.sendRedirect("/login?error=true");
    }

  }

}
