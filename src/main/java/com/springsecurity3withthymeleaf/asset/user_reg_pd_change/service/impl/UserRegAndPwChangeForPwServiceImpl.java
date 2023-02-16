package com.springsecurity3withthymeleaf.asset.user_reg_pd_change.service.impl;


import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.dao.UserRegAndPwChangeForPwDao;
import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.entity.UserRegAndPwChangeForPw;
import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.service.UserRegAndPwChangeForPwService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegAndPwChangeForPwServiceImpl implements UserRegAndPwChangeForPwService {
  private final UserRegAndPwChangeForPwDao userRegAndPwChangeForPwDao;

  public UserRegAndPwChangeForPw createToken(UserRegAndPwChangeForPw userRegAndPwChangeForPw) {
    return userRegAndPwChangeForPwDao.save(userRegAndPwChangeForPw);
  }

  public UserRegAndPwChangeForPw findByToken(String token) {
    return userRegAndPwChangeForPwDao.findByToken(token);
  }

  public UserRegAndPwChangeForPw findByEmail(String email) {
    return userRegAndPwChangeForPwDao.findByEmail(email);
  }

  public void deleteByConformationToken(UserRegAndPwChangeForPw userRegAndPwChangeForPw) {
    userRegAndPwChangeForPwDao.delete(userRegAndPwChangeForPw);
  }
}
