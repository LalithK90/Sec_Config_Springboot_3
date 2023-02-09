package com.springsecurity3withthymeleaf.asset.user_details.dao;


import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsDao extends JpaRepository< UserDetails, Integer> {
    UserDetails findFirstByOrderByIdDesc();

    UserDetails findByNic(String nic);
}
