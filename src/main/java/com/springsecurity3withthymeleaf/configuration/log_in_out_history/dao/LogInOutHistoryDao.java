package com.springsecurity3withthymeleaf.configuration.log_in_out_history.dao;


import com.springsecurity3withthymeleaf.configuration.log_in_out_history.entity.LogInOutHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogInOutHistoryDao extends JpaRepository< LogInOutHistory, Long > {
  @Query( value = "select * from log_in_out_history where username = ?1 and browser = ?2 order by id desc limit 1",
      nativeQuery = true )
  LogInOutHistory findByUsernameAndBrowserLastRecord(@Param( "user_name" ) String username,
                                                     @Param( "browser_name" ) String browser);

}
