package com.springsecurity3withthymeleaf.configuration.session_log.service.impl;


import com.springsecurity3withthymeleaf.configuration.session_log.dao.UserSessionLogDao;
import com.springsecurity3withthymeleaf.configuration.session_log.entity.UserSessionLog;
import com.springsecurity3withthymeleaf.configuration.session_log.service.UserSessionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"userSessionLog"} )
public class UserUserSessionLogServiceImpl implements UserSessionLogService {
  private final UserSessionLogDao userSessionLogDao;

  @Autowired
  public UserUserSessionLogServiceImpl(UserSessionLogDao userSessionLogDao) {
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

  @Cacheable
  public UserSessionLog findBySessionId(String sessionId) {
    return userSessionLogDao.findBySessionId(sessionId);
  }

  @Cacheable
  public UserSessionLog findByUserName(String username) {
    return userSessionLogDao.findByUsername(username);
  }


  @Caching( evict = {@CacheEvict( value = "userSessionLog", allEntries = true )},
      put = {@CachePut( value = "userSessionLog", key = "#userSessionLog.id" )} )
  public UserSessionLog persist(UserSessionLog userSessionLog) {
    return userSessionLogDao.save(userSessionLog);
  }


}
