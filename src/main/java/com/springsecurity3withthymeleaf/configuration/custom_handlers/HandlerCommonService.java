package com.springsecurity3withthymeleaf.configuration.custom_handlers;

import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HandlerCommonService {


  public FailureAttempt failureAttempt(HttpServletRequest request) {
    FailureAttempt failureAttempt = new FailureAttempt(request);

//    failureAttempt.setUsername(request.getParameter("username"));
//    failureAttempt.setBrowser(extractBrowser(request.getHeader("User-Agent")));
//    failureAttempt.setOperatingSystem(extractOperatingSystem(request.getHeader("User-Agent")));
//    failureAttempt.setDevice(extractDevice(request.getHeader("User-Agent")));
//    failureAttempt.setIpAddress(request.getRemoteAddr());
//    failureAttempt.setLanguage(request.getHeader("Accept-Language"));
//    failureAttempt.setRequestMethod(request.getMethod());
//    failureAttempt.setSessionId(request.getSession().getId());
//    failureAttempt.setCookies(extractCookies(request.getCookies()));
//    failureAttempt.setQueryString(request.getQueryString());
//    failureAttempt.setReferrer(request.getHeader("Referer"));
//    failureAttempt.setRequestURI(request.getRequestURI());

    failureAttempt.setTriedDateTime(LocalDateTime.now());

    return failureAttempt;
  }

  public LogInOutHistory logInOutHistory(HttpServletRequest request) {
    LogInOutHistory logInOutHistory = new LogInOutHistory(request);

//    logInOutHistory.setUsername(request.getParameter("username"));
//    logInOutHistory.setBrowser(extractBrowser(request.getHeader("User-Agent")));
//    logInOutHistory.setOperatingSystem(extractOperatingSystem(request.getHeader("User-Agent")));
//    logInOutHistory.setDevice(extractDevice(request.getHeader("User-Agent")));
//    logInOutHistory.setIpAddress(request.getRemoteAddr());
//    logInOutHistory.setLanguage(request.getHeader("Accept-Language"));
//    logInOutHistory.setRequestMethod(request.getMethod());
//    logInOutHistory.setSessionId(request.getSession().getId());
//    logInOutHistory.setCookies(extractCookies(request.getCookies()));
//    logInOutHistory.setQueryString(request.getQueryString());
//    logInOutHistory.setReferrer(request.getHeader("Referer"));
//    logInOutHistory.setRequestURI(request.getRequestURI());

    logInOutHistory.setLogoutTime(null);
    logInOutHistory.setLoginTime(LocalDateTime.now());

    return logInOutHistory;

  }

//  public void commonDataFromHTTPRequest(HttpServletRequest request) {
//    setUsername(request.getParameter("username"));
//    setBrowser(extractBrowser(request.getHeader("User-Agent")));
//    setOperatingSystem(extractOperatingSystem(request.getHeader("User-Agent")));
//    setDevice(extractDevice(request.getHeader("User-Agent")));
//    setIpAddress(request.getRemoteAddr());
//    setLanguage(request.getHeader("Accept-Language"));
//    setRequestMethod(request.getMethod());
//    setSessionId(request.getSession().getId());
//    setCookies(extractCookies(request.getCookies()));
//    setQueryString(request.getQueryString());
//    setReferrer(request.getHeader("Referer"));
//    setRequestURI(request.getRequestURI());
//  }




}