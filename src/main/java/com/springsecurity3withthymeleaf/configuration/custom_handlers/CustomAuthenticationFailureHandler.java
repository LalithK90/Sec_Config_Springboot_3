package com.springsecurity3withthymeleaf.configuration.custom_handlers;

import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.FailureAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

      failureAttemptService.persist(handlerCommonService.failureAttempt(request));
      // Log the authentication failure

      response.sendRedirect("/login?error=true");
    }

  }

}
