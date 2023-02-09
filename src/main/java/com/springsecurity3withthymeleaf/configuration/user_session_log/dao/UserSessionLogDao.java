package com.springsecurity3withthymeleaf.configuration.user_session_log.dao;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.UserSessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository< UserSessionLog, Integer > {
  @Query(value = "select * from user_session_log where username = ?1 and browser = ?2 order by id desc limit 1", nativeQuery = true)
  UserSessionLog findByUsernameAndBrowserLastRecord(@Param("user_name") String username, @Param("browser_name") String browser);

}
