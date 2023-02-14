package com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class FailureAttempt extends CommonDataFromHTTPRequest {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer id;

  private String username;

  private LocalDateTime triedDateTime;


  public FailureAttempt(HttpServletRequest request) {
    super(request);
  }
}
