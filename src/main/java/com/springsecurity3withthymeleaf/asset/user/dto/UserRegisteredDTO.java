package com.springsecurity3withthymeleaf.asset.user.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegisteredDTO {

    String role;
    private String name;
    private String email_id;
    private String password;


}
