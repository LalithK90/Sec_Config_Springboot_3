package com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.impl;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.dao.FailureAttemptDao;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.FailureAttempt;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.FailureAttemptService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = "failureAttempt" )
public class FailureAttemptServiceImpl implements FailureAttemptService {
  private final FailureAttemptDao failureAttemptDao;

  public FailureAttemptServiceImpl(FailureAttemptDao failureAttemptDao) {
    this.failureAttemptDao = failureAttemptDao;
  }

  @Caching( evict = {@CacheEvict( value = "failureAttempt", allEntries = true )},
      put = {@CachePut( value = "failureAttempt", key = "#failureAttempt.id" )} )
  public FailureAttempt persist(FailureAttempt failureAttempt) {
    return failureAttemptDao.save(failureAttempt);
  }

  @CacheEvict( allEntries = true )
  public void deleteByUsername(String username) {
    failureAttemptDao.deleteAll(failureAttemptDao.findByUsername(username));
  }


  @Cacheable
  public List< FailureAttempt > findByUsername(String username) {
    return failureAttemptDao.findByUsername(username);
  }

}
