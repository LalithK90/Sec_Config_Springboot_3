package com.springsecurity3withthymeleaf.configuration.custom_handlers;

import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HandlerCommonService {


  public FailureAttempt failureAttempt(HttpServletRequest request) {
    FailureAttempt failureAttempt = new FailureAttempt();
    failureAttempt.commonDataFromHTTPRequest(request);

    failureAttempt.setTriedDateTime(LocalDateTime.now());

    return failureAttempt;
  }

  public LogInOutHistory logInOutHistory(HttpServletRequest request) {
    LogInOutHistory logInOutHistory = new LogInOutHistory();
    logInOutHistory.commonDataFromHTTPRequest(request);

    logInOutHistory.setLogoutTime(null);
    logInOutHistory.setLoginTime(LocalDateTime.now());

    return logInOutHistory;

  }


}