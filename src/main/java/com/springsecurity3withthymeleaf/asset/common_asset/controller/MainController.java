package com.springsecurity3withthymeleaf.asset.common_asset.controller;


import com.springsecurity3withthymeleaf.asset.common_asset.model.PasswordChange;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import com.springsecurity3withthymeleaf.asset.user_details.service.UserDetailsFilesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsFilesService userDetailsFilesService;

  @GetMapping
  public String favicon() {
    return "/img/favicon.ico";
  }

  @GetMapping( value = "/profile" )
  public String userProfile(Model model, Principal principal) {
    UserDetails userDetails = userService.findByUserName(principal.getName()).getUserDetails();
    model.addAttribute("userDetail", userDetails);
    model.addAttribute("file", userDetailsFilesService.userDetailsFileDownloadLinks(userDetails));
    return "userDetails/userDetails-detail";
  }

  @GetMapping( value = "/passwordChange" )
  public String passwordChangeForm(Model model) {
    model.addAttribute("pswChange", new PasswordChange());
    return "login/passwordChange";
  }

  @PostMapping( value = "/passwordChange" )
  public String passwordChange(@Valid @ModelAttribute PasswordChange passwordChange,
                               BindingResult result, RedirectAttributes redirectAttributes) {
    User user =
        userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));

    if ( passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword()) && !result.hasErrors() && passwordChange.getNewPassword().equals(passwordChange.getNewPasswordConform()) ) {

      user.setPassword(passwordChange.getNewPassword());
      userService.persist(user);

      redirectAttributes.addFlashAttribute("message", "Congratulations .!! Success password is changed");
      redirectAttributes.addFlashAttribute("alertClass", "alert-success");
      return "redirect:/home";

    }
    redirectAttributes.addFlashAttribute("message", "Failed");
    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
    return "redirect:/passwordChange";

  }

  @GetMapping( value = {"/", "/index"} )
  public String index() {
    return "index";
  }

  @GetMapping( value = {"/home", "/mainWindow"} )
  public String getHome(Model model) {
    //do some logic here if you want something to be done whenever
        /*User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Petition> petitionSet = new HashSet<>();
        minutePetitionService
                .findByEmployeeAndCreatedAtBetween(authUser.getEmployee(),
                        dateTimeAgeService
                                .dateTimeToLocalDateStartInDay(LocalDate.now()),
                        dateTimeAgeService
                                .dateTimeToLocalDateEndInDay(LocalDate.now())).forEach(
                minutePetition -> {
                    petitionSet.add(petitionService.findById(minutePetition.getPetition().getId()));
                });
        model.addAttribute("petitions", petitionSet.toArray());*/
    return "mainWindow";
  }

  @GetMapping( value = {"/login"} )
  public String getLogin(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.setAttribute("previousPage", request.getRequestURI());
    return "login/login";
  }
}