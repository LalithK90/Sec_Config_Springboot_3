package com.springsecurity3withthymeleaf.configuration.session_log.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionLog {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    private String username;
    private String sessionId;
    private String browser;
    private String operatingSystem;
    private String device;
    private String ipAddress;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String userAgent;
    private String language;
    private String referrer;
    private String requestMethod;
    private String requestURI;
    private String queryString;
    private String cookies;



}
