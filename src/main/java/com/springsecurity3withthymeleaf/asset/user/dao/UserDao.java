package com.springsecurity3withthymeleaf.asset.user.dao;


import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long > {

    @Query( value = "select id from User where user_details_id=?1", nativeQuery = true )
    Long findByUserDetailsId(@Param("user_details_id") Integer id);

    @Query( "select id from User where username=?1" )
    Long findUserIdByUserName(String userName);

    User findByUsername(String name);

    User findByUserProfile(UserProfile userProfile);

    boolean existsByUsername(String username);
}
