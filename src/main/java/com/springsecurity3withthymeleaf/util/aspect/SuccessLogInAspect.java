package com.springsecurity3withthymeleaf.util.aspect;

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

  private final FailureAttemptService failureAttemptService;

  @AfterReturning( value = "execution( * com.springsecurity3withthymeleaf.configuration.config.custom_handlers" +
      ".CustomAuthenticationSuccessHandler.onAuthenticationSuccess(..))" )
  public void onAuthenticationSuccess(JoinPoint joinPoint) {

    LogInOutHistory logInOutHistory = new LogInOutHistory((HttpServletRequest) joinPoint.getArgs()[0]);
    logInOutHistoryService.persist(logInOutHistory);
    log.info("Successfully login : " + logInOutHistory.getUsername());


//    after successfully login all failure attempts are removed
    failureAttemptService.deleteByUsername(logInOutHistory.getUsername());
    log.info("Previous failure attempts were removed");
  }


}
