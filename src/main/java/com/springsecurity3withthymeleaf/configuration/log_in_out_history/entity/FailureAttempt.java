package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class FailureAttempt {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer id;

  private LocalDateTime triedDateTime;

  private String username;

  private String browser;

  private String operatingSystem;

  private String device;

  private String ipAddress;

  private String language;

  private String requestMethod;

  private String sessionId;

  private String cookies;

  private String queryString;

  private String referrer;

  private String requestURI;



  public  FailureAttempt(HttpServletRequest request) {
    setUsername(request.getParameter("username"));
    setBrowser(extractBrowser(request.getHeader("User-Agent")));
    setOperatingSystem(extractOperatingSystem(request.getHeader("User-Agent")));
    setDevice(extractDevice(request.getHeader("User-Agent")));
    setIpAddress(request.getRemoteAddr());
    setLanguage(request.getHeader("Accept-Language"));
    setRequestMethod(request.getMethod());
    setSessionId(request.getSession().getId());
    setCookies(extractCookies(request.getCookies()));
    setQueryString(request.getQueryString());
    setReferrer(request.getHeader("Referer"));
    setRequestURI(request.getRequestURI());
    setTriedDateTime(LocalDateTime.now());
  }

  private String extractDevice(String userAgent) {
    if ( userAgent.contains("Mobile") ) {
      return "Mobile Device";
    } else if ( userAgent.contains("Tablet") ) {
      return "Tablet Device";
    } else if ( userAgent.contains("Windows NT") || userAgent.contains("Mac OS X") || userAgent.contains("Linux") ) {
      return "Desktop Computer";
    } else {
      return "Unknown Device";
    }
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
  }}
