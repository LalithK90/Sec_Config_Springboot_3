package com.springsecurity3withthymeleaf.configuration.session_log.service;


import com.springsecurity3withthymeleaf.configuration.session_log.entity.UserSessionLog;

import java.util.List;

public interface UserSessionLogService {
    List< UserSessionLog > findAll();

    UserSessionLog findById(Integer id);
    UserSessionLog findBySessionId(String sessionId);
    UserSessionLog findByUserName(String username);

    UserSessionLog persist(UserSessionLog userSessionLog);



}
