package com.springsecurity3withthymeleaf.configuration.custom_handlers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component( "customAuthenticationSuccessHandler" )
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {

    clearAuthenticationAttributes(request);

    HttpSession session = request.getSession();
    String previousPage = (String) session.getAttribute("previousPage");

    if ( previousPage != null && !previousPage.equals("/login") ) {
      response.sendRedirect(previousPage);
    } else {
      response.sendRedirect("/mainWindow");
    }
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
