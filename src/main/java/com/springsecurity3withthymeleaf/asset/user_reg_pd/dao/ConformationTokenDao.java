package com.springsecurity3withthymeleaf.asset.user_reg_pd.dao;


import com.springsecurity3withthymeleaf.asset.user_reg_pd.entity.ConformationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConformationTokenDao extends JpaRepository< ConformationToken, Integer> {
    ConformationToken findByToken(String token);

    ConformationToken findByEmail(String email);
}
