package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.UserSessionLog;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.UserSessionLogService;
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
  private UserSessionLogService userSessionLogService;


  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException {
    UserSessionLog userSessionLog = userSessionLogService.findByUserNameAndBrowser(authentication.getName(),extractBrowser(request.getHeader("User-Agent")));
       userSessionLog.setLogoutTime(LocalDateTime.now());
    userSessionLogService.persist(userSessionLog);

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
}
