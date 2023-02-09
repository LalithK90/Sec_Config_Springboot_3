package com.springsecurity3withthymeleaf.asset.user_details.dao;


import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetailsFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsFilesDao extends JpaRepository< UserDetailsFiles, Integer > {
  UserDetailsFiles findByUserDetails(UserDetails userDetails);

    UserDetailsFiles findByName(String filename);

    UserDetailsFiles findByNewName(String filename);

    UserDetailsFiles findByNewId(String filename);
}
