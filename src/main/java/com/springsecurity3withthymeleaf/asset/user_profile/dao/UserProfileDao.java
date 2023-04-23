package com.springsecurity3withthymeleaf.asset.user_profile.dao;


import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileDao extends JpaRepository<UserProfile, Long> {
    UserProfile findFirstByOrderByIdDesc();

    UserProfile findByNic(String nic);
}
