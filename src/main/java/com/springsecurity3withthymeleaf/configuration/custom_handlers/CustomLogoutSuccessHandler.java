package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.LogInOutHistoryService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component( "customLogoutSuccessHandler" )
public class CustomLogoutSuccessHandler extends
                                        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
  @Autowired
  private LogInOutHistoryService logInOutHistoryService;


  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException {

    LogInOutHistory logInOutHistory = logInOutHistoryService.findByUserNameAndBrowser(authentication.getName(),
                                                                                      extractBrowser(request.getHeader(
                                                                                          "User-Agent")));

    System.out.println("Befoer  "+logInOutHistory);

    logInOutHistory.setLogoutTime(LocalDateTime.now());
    logInOutHistory = logInOutHistoryService.persist(logInOutHistory);

    System.out.println(" after "+ logInOutHistory);

    String c =
        extractCookies(request.getCookies());
    System.out.println("kooko "+ c);

    response.setStatus(HttpServletResponse.SC_OK);
    response.sendRedirect("/");
  }

  private String extractBrowser(String userAgent) {
    // Code to extract browser information from user agent string
    if ( userAgent.contains("Firefox") ) {
      return "Firefox";
    } else if ( userAgent.contains("Edge") ) {
      return "Microsoft Edge";
    } else if ( userAgent.contains("Chrome") ) {
      return "Chrome";
    } else if ( userAgent.contains("Safari") ) {
      return "Safari";
    } else if ( userAgent.contains("Opera") ) {
      return "Opera";
    } else if ( userAgent.contains("MSIE") || userAgent.contains("Trident") ) {
      return "Internet Explorer";
    }
    return "Unknown Browser";
  }

  private String extractCookies(Cookie[] cookies) {
    if ( cookies == null ) {
      return "";
    }
    StringBuilder cookieString = new StringBuilder();
    for ( Cookie cookie : cookies ) {
      cookieString.append(cookie.getName());
      cookieString.append("=");
      cookieString.append(cookie.getValue());
      cookieString.append(";");
    }
    return cookieString.toString();
  }
}
