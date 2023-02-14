package com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.impl;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.dao.LogInOutHistoryDao;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import com.springsecurity3withthymeleaf.configuration.log_in_out_history.service.LogInOutHistoryService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"userSessionLog"} )
public class LogInOutHistoryServiceImpl implements LogInOutHistoryService {
  private final LogInOutHistoryDao logInOutHistoryDao;

  public LogInOutHistoryServiceImpl(LogInOutHistoryDao logInOutHistoryDao) {
    this.logInOutHistoryDao = logInOutHistoryDao;
  }


  @Cacheable
  public List< LogInOutHistory > findAll() {
    return logInOutHistoryDao.findAll();
  }

  @Cacheable
  public LogInOutHistory findById(Integer id) {
    return logInOutHistoryDao.getReferenceById(id);
  }


  @Caching( evict = {@CacheEvict( value = "userSessionLog", allEntries = true )},
      put = {@CachePut( value = "userSessionLog", key = "#logInOutHistory.id" )} )
  public LogInOutHistory persist(LogInOutHistory logInOutHistory) {
    return logInOutHistoryDao.save(logInOutHistory);
  }

  @Cacheable
  public LogInOutHistory findByUserNameAndBrowser(String name, String browserName) {
    LogInOutHistory logInOutHistory1 = logInOutHistoryDao.findByUsernameAndBrowserLastRecord(name, browserName);
    System.out.println(logInOutHistory1 +" in service class");
    return logInOutHistory1;
  }


}
