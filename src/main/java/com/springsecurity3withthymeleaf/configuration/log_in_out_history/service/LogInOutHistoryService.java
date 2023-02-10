package com.springsecurity3withthymeleaf.configuration.log_in_out_history.service;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;

import java.util.List;

public interface LogInOutHistoryService {
  List< LogInOutHistory > findAll();

  LogInOutHistory persist(LogInOutHistory logInOutHistory);

  LogInOutHistory findByUserNameAndBrowser(String name, String browserName);
}
