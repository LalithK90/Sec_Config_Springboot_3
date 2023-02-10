package com.springsecurity3withthymeleaf.configuration.log_in_out_history.dao;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.FailureAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailureAttemptDao extends JpaRepository< FailureAttempt, Integer > {

  List< FailureAttempt > findByUsername(String username);
}
