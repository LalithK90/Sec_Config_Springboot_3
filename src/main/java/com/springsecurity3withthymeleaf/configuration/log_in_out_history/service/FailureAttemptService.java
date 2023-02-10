package com.springsecurity3withthymeleaf.configuration.log_in_out_history.service;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.FailureAttempt;

import java.util.List;

public interface FailureAttemptService {

  FailureAttempt persist(FailureAttempt failureAttempt);

  void deleteByUsername(String username);

  List< FailureAttempt > findByUsername(String username);
}
