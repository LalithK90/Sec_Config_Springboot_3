package com.springsecurity3withthymeleaf.asset.user_details.service;


import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;

import java.util.List;

public interface UsersDetailsService {
    List< UserDetails > findAll();

    UserDetails findById(Integer id);

    UserDetails persist(UserDetails userDetails);

    boolean delete(Integer id);

    List<UserDetails> search(UserDetails userDetails);

    UserDetails findByNic(String nic);

    UserDetails findLastUserDetails();

}
