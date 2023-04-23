package com.springsecurity3withthymeleaf.asset.company.dao;


import com.springsecurity3withthymeleaf.asset.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository< Company, Long > {

//    @Query( value = "select id from User where user_details_id=?1", nativeQuery = true )
//    Integer findByUserDetailsId(@Param("user_details_id") Integer id);

    @Query( "select id from User where username=?1" )
    Integer findUserIdByUserName(String userName);

    Company findByName(String name);

//    Company findByUserDetails(UserDetails userDetails);

   }
