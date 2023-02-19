package com.springsecurity3withthymeleaf.asset.user_details.service.impl;


import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.StopActive;
import com.springsecurity3withthymeleaf.asset.user_details.dao.UserDetailsDao;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import com.springsecurity3withthymeleaf.asset.user_details.service.UserDetailsFilesService;
import com.springsecurity3withthymeleaf.asset.user_details.service.UsersDetailsService;
import com.springsecurity3withthymeleaf.util.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "userDetails")
@RequiredArgsConstructor
public class UsersDetailsServiceImpl implements UsersDetailsService {

  private final UserDetailsDao userDetailsDao;
  private final CommonService commonService;
  private final UserDetailsFilesService userDetailsFilesService;

  @Cacheable
  public List< UserDetails > findAll() {
    List<UserDetails> userDetailsList = new ArrayList<>();
    for (UserDetails userDetails : userDetailsDao.findAll(Sort.by(Sort.Direction.DESC, "id"))
        .stream()
        .filter(x -> StopActive.ACTIVE.equals(x.getStopActive())).toList()
    ) {
      userDetails.setFileInfo(userDetailsFilesService.userDetailsFileDownloadLinks(userDetails));
      userDetailsList.add(userDetails);
    }
    return userDetailsList;
  }

  @Cacheable
  public UserDetails findById(Integer id) {
    return userDetailsDao.getReferenceById(id);
  }

  @Caching( evict = {@CacheEvict( value = "userDetails", allEntries = true )},
      put = {@CachePut( value = "userDetails", key = "#userDetails.id" )} )
  public UserDetails persist(UserDetails userDetails) {
    if ( userDetails.getId() == null ) {
      if ( userDetailsDao.findFirstByOrderByIdDesc() != null ) {
        userDetails.setStopActive(StopActive.ACTIVE);
      }
    }
    if (userDetails.getId() == null) {
      //if there is not item in db
      if (userDetailsDao.findFirstByOrderByIdDesc() == null) {
        //need to generate new one
        userDetails.setNumber("LKCU" + commonService.numberAutoGen(null).toString());
      } else {
        //if there is item in db need to get that item's code and increase its value
        String previousCode = userDetailsDao.findFirstByOrderByIdDesc().getNumber().substring(4);
        userDetails.setNumber("LKCU" + commonService.numberAutoGen(previousCode).toString());
      }
    }
    if(userDetails.getMobileOne() != null){
      userDetails.setMobileOne(commonService.phoneNumberLengthValidator(userDetails.getMobileOne()));
    }
    if(userDetails.getMobileTwo() != null) {
      System.out.println("mcn value"+userDetails.getMobileTwo());
      userDetails.setMobileTwo(commonService.phoneNumberLengthValidator(userDetails.getMobileTwo()));
    }
    if(userDetails.getLand() != null) {
    userDetails.setLand(commonService.phoneNumberLengthValidator(userDetails.getLand()));
    }

    return userDetailsDao.save(userDetails);
  }

  @CacheEvict( allEntries = true )
  public boolean delete(Integer id) {
    UserDetails userDetails = userDetailsDao.getReferenceById(id);
    userDetails.setStopActive(StopActive.STOP);
    userDetailsDao.save(userDetails);
    return false;
  }

  @Cacheable
  public List< UserDetails > search(UserDetails userDetails) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< UserDetails > employeeExample = Example.of(userDetails, matcher);
    return userDetailsDao.findAll(employeeExample);
  }

  @Cacheable
  public UserDetails findByNic(String nic) {
    return userDetailsDao.findByNic(nic);
  }

  @Cacheable
  public UserDetails findLastUserDetails() {
    return userDetailsDao.findFirstByOrderByIdDesc();
  }
}
