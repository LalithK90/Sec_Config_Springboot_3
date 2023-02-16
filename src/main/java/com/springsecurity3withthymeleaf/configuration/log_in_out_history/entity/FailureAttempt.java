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
public class FailureAttempt extends HTTPCommonData{
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer id;

  private LocalDateTime triedDateTime;

  public FailureAttempt(HttpServletRequest request) {
    super(request);
    setTriedDateTime(LocalDateTime.now());
  }
  public FailureAttempt() {
    super();
  }
}
