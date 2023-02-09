package com.springsecurity3withthymeleaf.configuration.user_session_log.service.impl;


import com.springsecurity3withthymeleaf.configuration.user_session_log.dao.FailureAttemptDao;
import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.FailureAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@CacheConfig(cacheNames = "failureAttempt")
@RequiredArgsConstructor
public class FailureAttemptServiceImpl implements FailureAttemptService {
    private final FailureAttemptDao failureAttemptDao;

    @Cacheable
    public List< FailureAttempt > findAll() {
        return failureAttemptDao.findAll();
    }

    @Cacheable
    public FailureAttempt findById(Integer id) {
        return failureAttemptDao.getById(id);
    }

    @Caching(evict = {@CacheEvict(value = "failureAttempt", allEntries = true)},
            put = {@CachePut(value = "failureAttempt", key = "#failureAttempt.id")})
    public FailureAttempt persist(FailureAttempt failureAttempt) {
        return failureAttemptDao.save(failureAttempt);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        failureAttemptDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< FailureAttempt > search(FailureAttempt failureAttempt) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< FailureAttempt > attemptExample = Example.of(failureAttempt, matcher);
        return failureAttemptDao.findAll(attemptExample);
    }


    @Cacheable
    public List< FailureAttempt > findByIdentifiedDateIsBetween(LocalDate startDate, LocalDate endDate) {
        return failureAttemptDao.findByIdentifiedDateIsBetween(startDate, endDate);
    }

}
