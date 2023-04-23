package com.springsecurity3withthymeleaf.asset.user.service;


import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List< User > findAll();

    User findById(Long id);

    User persist(User user);

    boolean delete(Long id);

    List<User> search(User user);

    Long findByUserIdByUserName(String userName);

    User findByUserName(String name);

    User findUserByEmployee(UserProfile userProfile);

    boolean findByEmployee(UserProfile userProfile);

    boolean existsUsername(String username);
}
