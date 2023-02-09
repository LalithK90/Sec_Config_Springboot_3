package com.springsecurity3withthymeleaf.asset.user.service;


import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;

import java.util.List;

public interface UserService {

    List< User > findAll();

    User findById(Integer id);

    User persist(User user);

    boolean delete(Integer id);

    List<User> search(User user);

    Integer findByUserIdByUserName(String userName);

    User findByUserName(String name);

    User findUserByEmployee(UserDetails userDetails);

    boolean findByEmployee(UserDetails userDetails);
}
