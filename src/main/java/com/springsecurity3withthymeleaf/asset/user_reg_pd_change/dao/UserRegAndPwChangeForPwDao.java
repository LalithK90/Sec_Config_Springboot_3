package com.springsecurity3withthymeleaf.asset.user_reg_pd_change.dao;


import com.springsecurity3withthymeleaf.asset.user_reg_pd_change.entity.UserRegAndPwChangeForPw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegAndPwChangeForPwDao extends JpaRepository< UserRegAndPwChangeForPw, Integer > {
  UserRegAndPwChangeForPw findByToken(String token);

  UserRegAndPwChangeForPw findByEmail(String email);
}
