package com.springsecurity3withthymeleaf.asset.attempt.dao;


import com.springsecurity3withthymeleaf.asset.attempt.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttemptDao extends JpaRepository< Attempt, Integer> {

    List<Attempt> findByIdentifiedDateIsBetween(LocalDate from, LocalDate to);
}
