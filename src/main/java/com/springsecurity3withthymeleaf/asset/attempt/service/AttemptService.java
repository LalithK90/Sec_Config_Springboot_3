package com.springsecurity3withthymeleaf.asset.attempt.service;


import com.springsecurity3withthymeleaf.asset.attempt.entity.Attempt;

import java.time.LocalDate;
import java.util.List;

public interface AttemptService {

    List< Attempt > findAll();

    Attempt findById(Integer id);

    Attempt persist(Attempt attempt);

    boolean delete(Integer id);

    List<Attempt> search(Attempt attempt);


    List<Attempt> findByIdentifiedDateIsBetween(LocalDate startDate, LocalDate endDate);
}
