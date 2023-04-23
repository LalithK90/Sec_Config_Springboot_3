package com.springsecurity3withthymeleaf.asset.user_profile.service.impl;


import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.StopActive;
import com.springsecurity3withthymeleaf.asset.user_profile.dao.UserProfileDao;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileFilesService;
import com.springsecurity3withthymeleaf.asset.user_profile.service.UserProfileService;
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
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileDao userProfileDao;
  private final CommonService commonService;
  private final UserProfileFilesService userProfileFilesService;

  @Cacheable
  public List<UserProfile> findAll() {
    List<UserProfile> userProfileList = new ArrayList<>();
    for (UserProfile userProfile : userProfileDao.findAll(Sort.by(Sort.Direction.DESC, "id"))
        .stream()
        .filter(x -> StopActive.ACTIVE.equals(x.getStopActive())).toList()
    ) {
      userProfile.setFileInfo(userProfileFilesService.userDetailsFileDownloadLinks(userProfile));
      userProfileList.add(userProfile);
    }
    return userProfileList;
  }

  @Cacheable
  public UserProfile findById(Long id) {
    return userProfileDao.getReferenceById(id);
  }

  @Caching( evict = {@CacheEvict( value = "userDetails", allEntries = true )},
      put = {@CachePut( value = "userDetails", key = "#userProfile.id" )} )
  public UserProfile persist(UserProfile userProfile) {
    if ( userProfile.getId() == null ) {
      if ( userProfileDao.findFirstByOrderByIdDesc() != null ) {
        userProfile.setStopActive(StopActive.ACTIVE);
      }
    }
    if (userProfile.getId() == null) {
      //if there is not item in db
      if (userProfileDao.findFirstByOrderByIdDesc() == null) {
        //need to generate new one
        userProfile.setNumber("LKCU" + commonService.numberAutoGen(null).toString());
      } else {
        //if there is item in db need to get that item's code and increase its value
        String previousCode = userProfileDao.findFirstByOrderByIdDesc().getNumber().substring(4);
        userProfile.setNumber("LKCU" + commonService.numberAutoGen(previousCode).toString());
      }
    }
    if(userProfile.getMobileOne() != null){
      userProfile.setMobileOne(commonService.phoneNumberLengthValidator(userProfile.getMobileOne()));
    }
    if(userProfile.getMobileTwo() != null) {
      System.out.println("mcn value"+ userProfile.getMobileTwo());
      userProfile.setMobileTwo(commonService.phoneNumberLengthValidator(userProfile.getMobileTwo()));
    }
    if(userProfile.getLand() != null) {
    userProfile.setLand(commonService.phoneNumberLengthValidator(userProfile.getLand()));
    }

    return userProfileDao.save(userProfile);
  }

  @CacheEvict( allEntries = true )
  public boolean delete(Long id) {
    UserProfile userProfile = userProfileDao.getReferenceById(id);
    userProfile.setStopActive(StopActive.STOP);
    userProfileDao.save(userProfile);
    return false;
  }

  @Cacheable
  public List<UserProfile> search(UserProfile userProfile) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example<UserProfile> employeeExample = Example.of(userProfile, matcher);
    return userProfileDao.findAll(employeeExample);
  }

  @Cacheable
  public UserProfile findByNic(String nic) {
    return userProfileDao.findByNic(nic);
  }

  @Cacheable
  public UserProfile findLastUserDetails() {
    return userProfileDao.findFirstByOrderByIdDesc();
  }
}
