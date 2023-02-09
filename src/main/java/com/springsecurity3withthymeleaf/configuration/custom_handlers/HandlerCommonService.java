package com.springsecurity3withthymeleaf.configuration.custom_handlers;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class HandlerCommonService {
  public  String extractDevice(String userAgent) {
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

  public  String extractCookies(Cookie[] cookies) {
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
  public String extractBrowser(String userAgent) {
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

  public String extractOperatingSystem(String userAgent) {
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
  }}

//    userSessionLog.setSessionId(request.getSession().getId());
//    userSessionLog.setReferrer(request.getHeader("Referer"));
//    userSessionLog.setRequestURI(request.getRequestURI());
//    userSessionLog.setQueryString(request.getQueryString());
//    userSessionLog.setCookies(extractCookies(request.getCookies()));


