package com.springsecurity3withthymeleaf.configuration.session_log.dao;


import com.springsecurity3withthymeleaf.configuration.session_log.entity.UserSessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository< UserSessionLog, Integer > {
  UserSessionLog findBySessionId(String sessionId);
  UserSessionLog findByUsername(String username);

}
