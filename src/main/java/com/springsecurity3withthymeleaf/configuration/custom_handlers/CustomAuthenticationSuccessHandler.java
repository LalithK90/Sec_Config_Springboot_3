package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.UserSessionLog;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.UserSessionLogService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component( "customAuthenticationSuccessHandler" )
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  protected final Log logger = LogFactory.getLog(this.getClass());
  @Autowired
  private UserSessionLogService userSessionLogService;

  private static String extractDevice(String userAgent) {
    if (userAgent.contains("Mobile")) {
      return "Mobile Device";
    } else if (userAgent.contains("Tablet")) {
      return "Tablet Device";
    } else if (userAgent.contains("Windows NT") || userAgent.contains("Mac OS X") || userAgent.contains("Linux")) {
      return "Desktop Computer";
    } else {
      return "Unknown Device";
    }
  }

  private static String extractCookies(Cookie[] cookies) {
    if (cookies == null) {
      return "";
    }
    StringBuilder cookieString = new StringBuilder();
    for (Cookie cookie : cookies) {
      cookieString.append(cookie.getName());
      cookieString.append("=");
      cookieString.append(cookie.getValue());
      cookieString.append(";");
    }
    return cookieString.toString();
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    UserSessionLog userSessionLog = new UserSessionLog();

    // Extract information from the HttpServletRequest object
    userSessionLog.setUsername(authentication.getName());
    userSessionLog.setIpAddress(request.getRemoteAddr());
    userSessionLog.setLanguage(request.getHeader("Accept-Language"));
    userSessionLog.setRequestMethod(request.getMethod());
    userSessionLog.setLoginTime(LocalDateTime.now());
    userSessionLog.setLogoutTime(null);
    userSessionLog.setBrowser(extractBrowser(request.getHeader("User-Agent")));
    userSessionLog.setOperatingSystem(extractOperatingSystem(request.getHeader("User-Agent")));
    userSessionLog.setDevice(extractDevice(request.getHeader("User-Agent")));
//    userSessionLog.setSessionId(request.getSession().getId());
//    userSessionLog.setReferrer(request.getHeader("Referer"));
//    userSessionLog.setRequestURI(request.getRequestURI());
//    userSessionLog.setQueryString(request.getQueryString());
//    userSessionLog.setCookies(extractCookies(request.getCookies()));

    userSessionLogService.persist(userSessionLog);


    clearAuthenticationAttributes(request);
    logger.info("successfully login");
    response.sendRedirect("/mainWindow");
  }

  /**
   * Removes temporary authentication-related data which may have been stored in the session
   * during the authentication process.
   */
  protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);

    if ( session == null ) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
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

  private String extractOperatingSystem(String userAgent) {
    // Code to extract operating system information from user agent string
    if ( userAgent.contains("Windows") ) {
      return "Windows";
    } else if ( userAgent.contains("Windows NT 10.0") ) {
      return "Windows 10";
    } else if ( userAgent.contains("Windows NT 6.2") ) {
      return "Windows 8";
    } else if ( userAgent.contains("Windows NT 6.1") ) {
      return "Windows 7";
    } else if ( userAgent.contains("Mac OS X") ) {
      return "Mac OS";
    } else if ( userAgent.contains("Linux") ) {
      return "Linux";
    } else if ( userAgent.contains("Mac") ) {
      return "Mac";
    } else if ( userAgent.contains("X11") ) {
      return "Unix";
    } else if ( userAgent.contains("Android") ) {
      return "Android";
    } else if ( userAgent.contains("iPhone") || userAgent.contains("iPad") ) {
      return "iOS";
    }
    return "Unknown Operating System";
  }
}
