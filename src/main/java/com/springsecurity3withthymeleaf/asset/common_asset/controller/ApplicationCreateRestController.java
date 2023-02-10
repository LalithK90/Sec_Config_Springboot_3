package com.springsecurity3withthymeleaf.asset.common_asset.controller;


import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Gender;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Title;
import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import com.springsecurity3withthymeleaf.asset.role.service.RoleService;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import com.springsecurity3withthymeleaf.asset.user_details.service.UsersDetailsService;
import com.springsecurity3withthymeleaf.util.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApplicationCreateRestController {
  private final RoleService roleService;
  private final UserService userService;
  private final UsersDetailsService usersDetailsService;
  private final CommonService commonService;
  private final ResourceLoader resourceLoader;


  @GetMapping( "/select/user" )
  public String saveUser() {
    //roles list start
    String[] roles = {"ADMIN", "PH", "POLICE", "ARMY", "NAVY", "PRESIDENT", "COVID_CENTER"};
    for ( String s : roles ) {
      Role role = new Role();
      role.setRoleName(s);
      roleService.persist(role);
    }

//Employee
    UserDetails userDetails = new UserDetails();
    userDetails.setNumber("LKCC" + commonService.numberAutoGen(null).toString());
    userDetails.setName("Admin User");
    userDetails.setCallingName("Admin");
    userDetails.setName("908670000V");
    userDetails.setMobileOne("0750000000");
    userDetails.setTitle(Title.DR);
    userDetails.setGender(Gender.MALE);
    userDetails.setDateOfBirth(LocalDate.now().minusYears(18));
    UserDetails userDetailsDb = usersDetailsService.persist(userDetails);


    //admin user one
    User user = new User();
    user.setUserDetails(userDetailsDb);
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
