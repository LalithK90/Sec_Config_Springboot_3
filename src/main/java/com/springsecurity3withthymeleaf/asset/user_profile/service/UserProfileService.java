package com.springsecurity3withthymeleaf.asset.user_profile.service;


import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;

import java.util.List;

public interface UserProfileService {
    List<UserProfile> findAll();

    UserProfile findById(Long id);

    UserProfile persist(UserProfile userProfile);

    boolean delete(Long id);

    List<UserProfile> search(UserProfile userProfile);

    UserProfile findByNic(String nic);

    UserProfile findLastUserDetails();

}
