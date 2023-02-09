package com.springsecurity3withthymeleaf.configuration.sec_handler;


import com.springsecurity3withthymeleaf.configuration.session_log.entity.UserSessionLog;
import com.springsecurity3withthymeleaf.configuration.session_log.service.UserSessionLogService;
import jakarta.servlet.ServletException;
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
                              Authentication authentication) throws IOException, ServletException {

    UserSessionLog userSessionLog = userSessionLogService.findByUserName(request.getSession().getId());
    userSessionLog.setLogoutTime(LocalDateTime.now());
    userSessionLogService.persist(userSessionLog);

    response.setStatus(HttpServletResponse.SC_OK);
    response.sendRedirect("/");
  }
}
