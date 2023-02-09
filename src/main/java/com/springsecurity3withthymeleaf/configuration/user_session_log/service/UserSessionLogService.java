package com.springsecurity3withthymeleaf.configuration.user_session_log.service;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.UserSessionLog;

import java.util.List;

public interface UserSessionLogService {
    List< UserSessionLog > findAll();

    UserSessionLog persist(UserSessionLog userSessionLog);


    UserSessionLog findByUserNameAndBrowser(String name, String browserName);
}
