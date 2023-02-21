package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.enums.Browser;
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
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
  @Autowired
  private LogInOutHistoryService logInOutHistoryService;



  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException {

    Browser bs = extractBrowser(request.getHeader(
        "User-Agent"));
    System.out.println(bs);
    String b = bs.getBrowser();
    System.out.println(b);

    LogInOutHistory logInOutHistory = logInOutHistoryService.findByUserNameAndBrowser(authentication.getName(), String.valueOf(bs));

    System.out.println("Before  " + logInOutHistory);

    logInOutHistory.setLogoutTime(LocalDateTime.now());
    logInOutHistory = logInOutHistoryService.persist(logInOutHistory);

    System.out.println(" after " + logInOutHistory);

    String c =
        extractCookies(request.getCookies());
    System.out.println("kooko " + c);

    response.setStatus(HttpServletResponse.SC_OK);
    response.sendRedirect("/");
  }

  public String extractCookies(Cookie[] cookies) {
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

  public Browser extractBrowser(String userAgent) {
    System.out.println(userAgent);
    if ( userAgent.contains(Browser.FIREFOX.getBrowser()) ) {
      return Browser.FIREFOX;
    } else if ( userAgent.contains(Browser.EDGE.getBrowser()) ) {
      return Browser.EDGE;
    } else if ( userAgent.contains(Browser.CHROME.getBrowser()) ) {
      return Browser.CHROME;
    } else if ( userAgent.contains(Browser.SAFARI.getBrowser()) ) {
      return Browser.SAFARI;
    } else if ( userAgent.contains(Browser.OPERA.getBrowser()) ) {
      return Browser.OPERA;
    } else if ( userAgent.contains(Browser.MSIE.getBrowser()) || userAgent.contains(Browser.TRIDENT.getBrowser()) ) {
      return Browser.INTERNETEXPLORER;
    }
    return Browser.UNKNOWNBROWSER;
  }
}
