package com.springsecurity3withthymeleaf.configuration.user_session_log.service.impl;


import com.springsecurity3withthymeleaf.configuration.user_session_log.dao.FailureAttemptDao;
import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.FailureAttemptService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "failureAttempt")
public class FailureAttemptServiceImpl implements FailureAttemptService {
    private final FailureAttemptDao failureAttemptDao;

    public FailureAttemptServiceImpl(FailureAttemptDao failureAttemptDao) {
        this.failureAttemptDao = failureAttemptDao;
    }

    @Caching(evict = {@CacheEvict(value = "failureAttempt", allEntries = true)},
            put = {@CachePut(value = "failureAttempt", key = "#failureAttempt.id")})
    public FailureAttempt persist(FailureAttempt failureAttempt) {
        return failureAttemptDao.save(failureAttempt);
    }

    @CacheEvict(allEntries = true)
    public void deleteByUsername(String username) {
        failureAttemptDao.deleteByUsername(username);
    }



    @Cacheable
    public List< FailureAttempt > findByUsername(String username) {
        return failureAttemptDao.findByUsername(username);
    }

}
