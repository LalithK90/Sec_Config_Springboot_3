package com.springsecurity3withthymeleaf.asset.user_reg_pd_change.controller;


import com.springsecurity3withthymeleaf.asset.common_asset.model.PasswordChange;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.StopActive;
import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import com.springsecurity3withthymeleaf.asset.role.service.RoleService;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileService;
import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.entity.UserRegAndPwChangeForPw;
import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.entity.enums.TokenStatus;
import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.service.UserRegAndPwChangeForPwService;
import com.springsecurity3withthymeleaf.util.service.CommonService;
import com.springsecurity3withthymeleaf.util.service.DateTimeAgeService;
import com.springsecurity3withthymeleaf.util.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Log
public class UserRegAndPwChangeForPwController {
  private final UserRegAndPwChangeForPwService userRegAndPwChangeForPwService;
  private final EmailService emailService;
  private final UserService userService;
  private final RoleService roleService;
  private final DateTimeAgeService dateTimeAgeService;
  private final CommonService commonService;
  private final UserProfileService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  private boolean confirmationTokenIsExpired(UserRegAndPwChangeForPw userRegAndPwChangeForPw) {
    return userRegAndPwChangeForPw != null && dateTimeAgeService.getDateTimeDurationInHours(LocalDateTime.now(),
                                                                                            userRegAndPwChangeForPw.getEndDate()) < 25L;
  }

  private void accordingToTokenStatus(Model model, TokenStatus status) {
    model.addAttribute("newOrOld", status);
    if ( status == TokenStatus.NEW ) {
      model.addAttribute("formHeader", "Welcome to the system");
      model.addAttribute("formHeaderMessage", "Hey :/) This is the time to enter the new world");
    } else {
      model.addAttribute("formHeader", "Here is able to rest your password");
      model.addAttribute("formHeaderMessage", "Ha Ha !! Time is ok to change your old password. \n Because you may forgotten your password or your tried more than five (5) to come to home with wrong credential combination (-);");
    }
  }

  @GetMapping( value = "/register" )
  private String sendNewUserRegistrationForm(Model model) {
    accordingToTokenStatus(model, TokenStatus.NEW);
    return "user/register";
  }

  @PostMapping( value = {"/register"} )
  private String sendTokenToEmail(@RequestParam( "email" ) String email,
                                  @RequestParam( "newOrOld" ) TokenStatus newOrOld,
                                  Model model, HttpServletRequest request) {
    //check email validity
    if ( !commonService.isValidEmail(email) ) {
      TokenStatus tokenStatus = TokenStatus.OLD;
      model.addAttribute("message", "Hey ypu enter email is not a valid one then please enter valid email to continue" +
          " this process. (-)");
      if ( newOrOld.equals(TokenStatus.NEW) ) {
        tokenStatus = TokenStatus.NEW;
      }
      accordingToTokenStatus(model, tokenStatus);
      return "user/register";
    }

    //check if there is user on the system
    User user = userService.findByUserName(email);
    if ( user != null ) {
      if ( Objects.equals(newOrOld, TokenStatus.OLD) ) {
        accordingToTokenStatus(model, TokenStatus.OLD);
        String message = "Hey Hey !! \n You are on our system please use forgotten password reset option to reset " +
            "your password.";
        model.addAttribute("message", message);
        return "user/register";
      }
    }

    //create token
    UserRegAndPwChangeForPw userRegAndPwChangeForPw = userRegAndPwChangeForPwService.findByEmail(email);
    if ( userRegAndPwChangeForPw != null ) {
      userRegAndPwChangeForPw.setEndDate(LocalDateTime.from(LocalDateTime.now().plusDays(1)));
    } else {
      userRegAndPwChangeForPw = new UserRegAndPwChangeForPw(email, newOrOld);
    }

    String token = userRegAndPwChangeForPwService.createToken(userRegAndPwChangeForPw).getToken();
    String url = request.getRequestURL().toString() + "/token/" + token;
    String subject = "Covid19 Prevention Center";
    String message = ("""
        Knock knock !!
         Here is your account activation link
         please click below link to active your account \n""").concat(url).concat("\n  this link is valid only 24 hours. ");
    System.out.println(message);
    System.out.println(url);
    emailService.sendEmail(email, subject, message);
    model.addAttribute("successMessage",
                       "Please check your email \n Here is the your entered email is \t ".concat(email));
    model.addAttribute("newOrOld", newOrOld);
    return "user/successMessage";
  }

  @GetMapping( value = {"/register/token/{token}"} )
  public String passwordEnterPage(@PathVariable( "token" ) String token, Model model) {

    UserRegAndPwChangeForPw userRegAndPwChangeForPw = userRegAndPwChangeForPwService.findByToken(token);
    if ( confirmationTokenIsExpired(userRegAndPwChangeForPw) ) {
      model.addAttribute("token", userRegAndPwChangeForPw.getToken());
      return "user/password";
    } else {
      String message = " Hey \n There is difficulty to going forward at the moment. could you be kind enough to go " +
          "same process " +
          "because your current activation link is expired";
      model.addAttribute("message", message);
      return "user/register";
    }
  }

  @PostMapping( "/register/user" )
  public String newUser(@RequestParam( "token" ) String token, @RequestParam( "password" ) String password,
                        @RequestParam( "reEnterPassword" ) String reEnterPassword, Model model) {
//     token
    UserRegAndPwChangeForPw userRegAndPwChangeForPw = userRegAndPwChangeForPwService.findByToken(token);
//    check token
    if ( !confirmationTokenIsExpired(userRegAndPwChangeForPw) ) {
      String message = " Hey \n There is no valid token at the moment. could you be kind enough to go with same process " +
          "because your current activation link is expired";
      model.addAttribute("message", message);
      return "redirect:/register";
    }
//    check password equality
    if ( !password.equals(reEnterPassword) ) {
      model.addAttribute("token", token);
      String message = "Hey your entered password is not match with re-enter password then there is difficulty to " +
          "continue your requirement so " +
          "\n please carefully choose your password and kind enough to fill this form again re enter.";
      model.addAttribute("message", message);

    }
    String email = userRegAndPwChangeForPw.getEmail();
//    find user account according to token email
    User userDB = userService.findByUserName(email);
//    if user is on the system
    if ( userDB != null ) {
      userDB.setPassword(password);
      userService.persist(userDB);
      return "redirect:/login";
    } else {
      User user = new User();
      user.setUsername(userRegAndPwChangeForPw.getEmail());
      user.setPassword(password);
      user.setEnabled(true);
      Role role = roleService.findByRoleName("New");

//      need to set role
      List< Role > roles = new ArrayList<>();
      roles.add(role);
      user.setRoles(roles);

//      need to set user details
      UserProfile userProfile = new UserProfile();
      userProfile.setEmail(email);
      userProfile.setStopActive(StopActive.ACTIVE);

      user.setUserProfile(userDetailsService.persist(userProfile));
      User saveUser = userService.persist(user);

//    destroy confirmation token
      userRegAndPwChangeForPwService.deleteByConformationToken(userRegAndPwChangeForPw);
//todo destroy confirmation token not working
      return "redirect:/userDetails/edit/"+saveUser.getUserProfile().getId();
    }

  }


  @GetMapping( value = "/passwordChange" )
  public String passwordChangeForm(Model model) {
    model.addAttribute("pswChange", new PasswordChange());
    return "login/passwordChange";
  }

  @PostMapping( value = "/passwordChange" )
  public String passwordChange(@Valid @ModelAttribute PasswordChange passwordChange,
                               BindingResult result, RedirectAttributes redirectAttributes) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user =
        userService.findById(userService.findByUserIdByUserName(username));

    if ( passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword()) && !result.hasErrors() && passwordChange.getNewPassword().equals(passwordChange.getNewPasswordConform()) ) {

      user.setPassword(passwordChange.getNewPassword());
      userService.persist(user);

      redirectAttributes.addFlashAttribute("message", "Congratulations .!! Success password is changed");
      redirectAttributes.addFlashAttribute("alertClass", "alert-success");
      log.info("Username :  " + username + " is successfully password changed.");
      return "redirect:/home";

    }
    redirectAttributes.addFlashAttribute("message", "Failed");
    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
    return "redirect:/passwordChange";

  }

  @GetMapping( value = "/forgottenPassword" )
  private String sendForgottenPasswordForm(Model model) {
    accordingToTokenStatus(model, TokenStatus.OLD);
    return "user/register";
  }

}
