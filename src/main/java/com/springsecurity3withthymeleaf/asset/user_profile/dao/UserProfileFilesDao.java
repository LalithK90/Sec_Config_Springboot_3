package com.springsecurity3withthymeleaf.asset.user_profile.dao;


import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfileFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileFilesDao extends JpaRepository<UserProfileFiles, Long > {
  UserProfileFiles findByUserProfile(UserProfile userProfile);

    UserProfileFiles findByName(String filename);

    UserProfileFiles findByNewName(String filename);

    UserProfileFiles findByNewId(String filename);
}
