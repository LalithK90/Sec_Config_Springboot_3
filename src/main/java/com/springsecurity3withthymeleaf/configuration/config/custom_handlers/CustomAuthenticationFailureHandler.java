package com.springsecurity3withthymeleaf.configuration.config.custom_handlers;


import com.springsecurity3withthymeleaf.asset.user.service.UserService;
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
  private FailureAttemptService failureAttemptService;

  @Autowired
  private UserService userService;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException {
    String username = request.getParameter("username");
//if user exit or not according to provide username
    boolean userExist = userService.existsUsername(username);
    List<FailureAttempt> failureAttempts = failureAttemptService.findByUsername(username);
    System.out.println(userExist);
    if ( !userExist ) {
      response.sendRedirect("/app/register");
    } else

      //    check how many failure attempts record on the system if count is more than 5
      if ( failureAttempts.size() > 4 ) {
        //    user redirect to password reset page
        response.sendRedirect("/app/forgottenPassword");
      } else {
        //    if not save new failure attempt
        failureAttemptService.persist(new FailureAttempt(request));
        // Log the authentication failure
        response.sendRedirect("/login?error=true");
      }
  }

}
