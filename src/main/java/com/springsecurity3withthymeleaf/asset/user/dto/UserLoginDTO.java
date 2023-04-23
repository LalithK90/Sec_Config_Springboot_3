package com.springsecurity3withthymeleaf.asset.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDTO {
	
private String username;
	
	private String password;
	
	private int otp;


}
