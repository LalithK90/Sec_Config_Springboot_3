package com.springsecurity3withthymeleaf.configuration.user_session_log.service;


import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.FailureAttempt;

import java.time.LocalDate;
import java.util.List;

public interface FailureAttemptService {

    List< FailureAttempt > findAll();

    FailureAttempt findById(Integer id);

    FailureAttempt persist(FailureAttempt failureAttempt);

    boolean delete(Integer id);

    List< FailureAttempt > search(FailureAttempt failureAttempt);


    List< FailureAttempt > findByIdentifiedDateIsBetween(LocalDate startDate, LocalDate endDate);
}
