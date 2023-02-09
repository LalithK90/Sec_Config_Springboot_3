package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.UserSessionLog;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.FailureAttemptService;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.UserSessionLogService;
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
@Autowired
private HandlerCommonService handlerCommonService;
@Autowired
private FailureAttemptService failureAttemptService;


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
    userSessionLog.setBrowser(handlerCommonService.extractBrowser(request.getHeader("User-Agent")));
    userSessionLog.setOperatingSystem(handlerCommonService.extractOperatingSystem(request.getHeader("User-Agent")));
    userSessionLog.setDevice(handlerCommonService.extractDevice(request.getHeader("User-Agent")));

    userSessionLogService.persist(userSessionLog);


    clearAuthenticationAttributes(request);
//    after successfully login all failure attempts are removed
    failureAttemptService.deleteByUsername(authentication.getName());
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


}
