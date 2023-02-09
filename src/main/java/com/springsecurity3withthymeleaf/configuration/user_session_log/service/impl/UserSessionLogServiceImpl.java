package com.springsecurity3withthymeleaf.configuration.user_session_log.service.impl;


import com.springsecurity3withthymeleaf.configuration.user_session_log.dao.UserSessionLogDao;
import com.springsecurity3withthymeleaf.configuration.user_session_log.entity.UserSessionLog;
import com.springsecurity3withthymeleaf.configuration.user_session_log.service.UserSessionLogService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"userSessionLog"} )
public class UserSessionLogServiceImpl implements UserSessionLogService {
  private final UserSessionLogDao userSessionLogDao;

  public UserSessionLogServiceImpl(UserSessionLogDao userSessionLogDao) {
    this.userSessionLogDao = userSessionLogDao;
  }


  @Cacheable
  public List< UserSessionLog > findAll() {
    return userSessionLogDao.findAll();
  }

  @Cacheable
  public UserSessionLog findById(Integer id) {
    return userSessionLogDao.getReferenceById(id);
  }




  @Caching( evict = {@CacheEvict( value = "userSessionLog", allEntries = true )},
      put = {@CachePut( value = "userSessionLog", key = "#userSessionLog.id" )} )
  public UserSessionLog persist(UserSessionLog userSessionLog) {
    return userSessionLogDao.save(userSessionLog);
  }

@Cacheable
  public UserSessionLog findByUserNameAndBrowser(String name, String browserName) {
    return userSessionLogDao.findByUsernameAndBrowserLastRecord(name, browserName);
  }


}
