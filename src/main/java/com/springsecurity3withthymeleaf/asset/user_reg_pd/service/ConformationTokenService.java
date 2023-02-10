package com.springsecurity3withthymeleaf.asset.user_reg_pd.service;


import com.springsecurity3withthymeleaf.asset.user_reg_pd.dao.ConformationTokenDao;
import com.springsecurity3withthymeleaf.asset.user_reg_pd.entity.ConformationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConformationTokenService {
  private final ConformationTokenDao conformationTokenDao;

  public ConformationToken createToken(ConformationToken conformationToken) {
    return conformationTokenDao.save(conformationToken);
  }

  public ConformationToken findByToken(String token) {
    return conformationTokenDao.findByToken(token);
  }

  public ConformationToken findByEmail(String email) {
    return conformationTokenDao.findByEmail(email);
  }

  public void deleteByConformationToken(ConformationToken conformationToken) {
    conformationTokenDao.delete(conformationToken);
  }
}
