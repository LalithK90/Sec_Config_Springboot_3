package com.springsecurity3withthymeleaf.asset.user_reg_pd_change.service;


import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.entity.UserRegAndPwChangeForPw;
import org.springframework.stereotype.Service;

@Service
public interface UserRegAndPwChangeForPwService {

  UserRegAndPwChangeForPw createToken(UserRegAndPwChangeForPw userRegAndPwChangeForPw);

  UserRegAndPwChangeForPw findByToken(String token) ;

  UserRegAndPwChangeForPw findByEmail(String email) ;

  void deleteByConformationToken(UserRegAndPwChangeForPw userRegAndPwChangeForPw) ;
}
