package com.springsecurity3withthymeleaf.asset.user_profile.service.impl;


import com.springsecurity3withthymeleaf.asset.common_asset.model.FileInfo;
import com.springsecurity3withthymeleaf.asset.user_profile.controller.UserProfileController;
import com.springsecurity3withthymeleaf.asset.user_profile.dao.UserProfileFilesDao;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfileFiles;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileFilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Service
@CacheConfig(cacheNames = "employeeFiles")
@RequiredArgsConstructor
public class UserProfileFilesServiceImpl implements UserProfileFilesService {
    private final UserProfileFilesDao userProfileFilesDao;


    public UserProfileFiles findByName(String filename) {
        return userProfileFilesDao.findByName(filename);
    }

    public void persist(UserProfileFiles userProfileFiles) {
        userProfileFilesDao.save(userProfileFiles);
    }

    public List<UserProfileFiles> search(UserProfileFiles userProfileFiles) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<UserProfileFiles> employeeFilesExample = Example.of(userProfileFiles, matcher);
        return userProfileFilesDao.findAll(employeeFilesExample);
    }

    public UserProfileFiles findById(Long id) {
        return userProfileFilesDao.getReferenceById(id);
    }

    public UserProfileFiles findByNewID(String filename) {
        return userProfileFilesDao.findByNewId(filename);
    }

    public UserProfileFiles findByUserDetails(UserProfile userProfile) {
        return userProfileFilesDao.findByUserProfile(userProfile);
    }

    @Cacheable
    public FileInfo userDetailsFileDownloadLinks(UserProfile userProfile) {
        UserProfileFiles userProfileFiles = userProfileFilesDao.findByUserProfile(userProfile);
        if (userProfileFiles != null) {
            String filename = userProfileFiles.getName();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(UserProfileController.class, "downloadFile", userProfileFiles.getNewId())
                    .build()
                    .toString();
            return new FileInfo(filename, userProfileFiles.getCreatedAt(), url);
        }
        return null;
    }
}

