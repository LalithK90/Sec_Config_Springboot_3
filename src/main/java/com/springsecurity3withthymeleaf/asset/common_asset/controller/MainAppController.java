package com.springsecurity3withthymeleaf.asset.common_asset.controller;


import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Gender;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Title;
import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import com.springsecurity3withthymeleaf.asset.role.service.RoleService;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileFilesService;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileService;
import com.springsecurity3withthymeleaf.util.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log
public class MainAppController {
  private final UserService userService;
  private final UserProfileFilesService userProfileFilesService;
  private final RoleService roleService;
  private final UserProfileService userProfileService;
  private final CommonService commonService;
  private final ResourceLoader resourceLoader;

  @GetMapping( value = {"/", "/index"} )
  public String index() {
    return "index";
  }

  @GetMapping( value = {"/login"} )
  public String getLogin(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.setAttribute("previousPage", request.getRequestURI());
    return "login/login";
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

  @GetMapping( value = "/profile" )
  public String userProfile(Model model, Principal principal) {
    UserProfile userProfile = userService.findByUserName(principal.getName()).getUserProfile();
    model.addAttribute("userDetail", userProfile);
    model.addAttribute("file", userProfileFilesService.userDetailsFileDownloadLinks(userProfile));
    return "userDetails/userDetails-detail";
  }



  @GetMapping( "/select/user" )
  @ResponseBody
  public String saveUser() {
    //roles list start
    String[] roles = {"ADMIN", "SAMPLE", "SAMPLE1", "SAMPLE2"};
    for ( String s : roles ) {
      Role role = new Role();
      role.setRoleName(s);
      roleService.persist(role);
    }

//Employee
    UserProfile userProfile = new UserProfile();
    userProfile.setNumber("LKCC" + commonService.numberAutoGen(null).toString());
    userProfile.setName("Admin User");
    userProfile.setCallingName("Admin");
    userProfile.setName("908670000V");
    userProfile.setMobileOne("0750000000");
    userProfile.setTitle(Title.DR);
    userProfile.setGender(Gender.MALE);
    userProfile.setDateOfBirth(LocalDate.now().minusYears(18));
    UserProfile userProfileDb = userProfileService.persist(userProfile);


    //admin user one
    User user = new User();
    user.setUserProfile(userProfileDb);
    user.setUsername("admin@gmail.com");
    user.setPassword("admin");
    String message = "Username:- " + user.getUsername() + "\n Password:- " + user.getPassword();
    user.setEnabled(true);
    user.setRoles(roleService.findAll()
                      .stream()
                      .filter(role -> role.getRoleName().equals("ADMIN"))
                      .collect(Collectors.toList()));
    userService.persist(user);

    return message;
  }

}