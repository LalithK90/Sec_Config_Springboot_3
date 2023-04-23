package com.springsecurity3withthymeleaf.asset.user_profile.service;


import com.springsecurity3withthymeleaf.asset.common_asset.model.FileInfo;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfileFiles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileFilesService {

    UserProfileFiles findByName(String filename);

    void persist(UserProfileFiles storedFile);

    List<UserProfileFiles> search(UserProfileFiles userProfileFiles);

    UserProfileFiles findById(Long id);

    UserProfileFiles findByNewID(String filename);

    UserProfileFiles findByUserDetails(UserProfile userProfile);

    FileInfo userDetailsFileDownloadLinks(UserProfile userProfile);
}
