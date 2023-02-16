package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class LogInOutHistory extends HTTPCommonData{
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer id;

  private LocalDateTime loginTime;

  private LocalDateTime logoutTime;


  public LogInOutHistory(HttpServletRequest request) {
    super(request);
    setLogoutTime(null);
    setLoginTime(LocalDateTime.now());
  }

  public LogInOutHistory() {
    super();
  }
}
