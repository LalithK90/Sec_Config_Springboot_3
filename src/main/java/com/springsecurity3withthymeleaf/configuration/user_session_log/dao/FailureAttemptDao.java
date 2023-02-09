package com.springsecurity3withthymeleaf.configuration.user_session_log.dao;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.FailureAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailureAttemptDao extends JpaRepository< FailureAttempt, Integer> {
void deleteByUsername(String username);

  List< FailureAttempt> findByUsername(String username);
}
