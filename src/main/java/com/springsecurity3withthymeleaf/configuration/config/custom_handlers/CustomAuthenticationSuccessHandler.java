package com.springsecurity3withthymeleaf.configuration.config.custom_handlers;


import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Title;
import com.springsecurity3withthymeleaf.asset.role.service.RoleService;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.entity.enums.AProvider;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileService;
import com.springsecurity3withthymeleaf.util.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Component( "customAuthenticationSuccessHandler" )
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserProfileService userProfileService;
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {


//
//    clearAuthenticationAttributes(request);
//
//    HttpSession session = request.getSession();
//    String previousPage = (String) session.getAttribute("previousPage");
//
//    if ( previousPage != null && !previousPage.equals("/login") ) {
//      response.sendRedirect(previousPage);
//    } else {
//      response.sendRedirect("/app/mainWindow");
//    }

      String redirectUrl = null;
      if (authentication.getPrincipal() instanceof DefaultOAuth2User userDetails) {
          OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
          String providerName = oauthToken.getAuthorizedClientRegistrationId();
          System.out.println("Provider  :  " + providerName);

          // Do something with the provider name
          System.out.println(authentication.getPrincipal());

          String username = userDetails.getAttribute("email") != null ?
                  userDetails.getAttribute("email") : userDetails.getAttribute("login") + "@gmail.com";
          if (userService.findByUserName(username) == null) {

              UserProfile userDetailToDb = new UserProfile();
              userDetailToDb.setNumber("LITI" + commonService.numberAutoGen(null).toString());
              userDetailToDb.setName("Admin User");
              userDetailToDb.setCallingName("Admin");
              userDetailToDb.setName("908670000V");
              userDetailToDb.setMobileOne("0750000000");
              userDetailToDb.setEmail(userDetails.getAttribute("email"));
              userDetailToDb.setTitle(Title.DR);
              userDetailToDb.setDateOfBirth(LocalDate.now().minusYears(18));
              userDetailToDb = userProfileService.persist(userDetailToDb);

              User user = new User();
              user.setUsername(username);
              user.setName(userDetails.getAttribute("name") != null ? userDetails.getAttribute("email") : userDetails.getAttribute("login"));
              user.setPassword(commonService.generateAlias(6));
              user.setEnabled(true);
              user.setAProvider(providerName==null? AProvider.DATABASE : AProvider.valueOf(providerName.toUpperCase()));
              user.setUserProfile(userDetailToDb);
              user.setRoles(roleService.findAll()
                      .stream()
                      .filter(role -> role.getRoleName().equals("ADMIN"))
                      .collect(Collectors.toList()));
              userService.persist(user);
          }
      }
      redirectUrl = "/";
      new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);

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
