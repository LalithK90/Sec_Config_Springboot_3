package com.springsecurity3withthymeleaf.asset.user_details.service;


import com.springsecurity3withthymeleaf.asset.common_asset.model.FileInfo;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetailsFiles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsFilesService {

    UserDetailsFiles findByName(String filename);

    void persist(UserDetailsFiles storedFile);

    List<UserDetailsFiles> search(UserDetailsFiles userDetailsFiles);

    UserDetailsFiles findById(Integer id);

    UserDetailsFiles findByNewID(String filename);

    UserDetailsFiles findByUserDetails(UserDetails userDetails);

    FileInfo userDetailsFileDownloadLinks(UserDetails userDetails);
}
