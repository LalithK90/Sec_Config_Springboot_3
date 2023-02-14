package com.springsecurity3withthymeleaf.util.aspect;

import com.springsecurity3withthymeleaf.configuration.custom_handlers.HandlerCommonService;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.FailureAttemptService;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.LogInOutHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Log
public class SuccessLogInAspect {

  private final LogInOutHistoryService logInOutHistoryService;

  private final HandlerCommonService handlerCommonService;

  private final FailureAttemptService failureAttemptService;

  @AfterReturning( value = "execution( * com.springsecurity3withthymeleaf.configuration.custom_handlers" +
      ".CustomAuthenticationSuccessHandler.onAuthenticationSuccess(..))" )
  public void onAuthenticationSuccess(JoinPoint joinPoint) {
LogInOutHistory logInOutHistory = logInOutHistoryService.persist(handlerCommonService.logInOutHistory((HttpServletRequest) joinPoint.getArgs()[0]));

//    after successfully login all failure attempts are removed
    failureAttemptService.deleteByUsername(logInOutHistory.getUsername());

  }


}
