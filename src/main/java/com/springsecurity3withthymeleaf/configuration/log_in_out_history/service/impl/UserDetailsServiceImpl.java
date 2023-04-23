package com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.impl;


import com.springsecurity3withthymeleaf.asset.user.dao.UserDao;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.CustomerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserDao userDao;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userDao.findByUsername(username.toLowerCase());
    CustomerUserDetails userDetails;
    if ( user != null ) {
      userDetails = new CustomerUserDetails();
      userDetails.setUser(user);
    } else {
      throw new UsernameNotFoundException("User not exist with name : " + username);
    }
    return userDetails;
  }
}

