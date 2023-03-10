package com.springsecurity3withthymeleaf.asset.user.service.impl;


import com.springsecurity3withthymeleaf.asset.user.dao.UserDao;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_details.entity.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"user"})
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Cacheable
    public List< User > findAll() {
        return userDao.findAll();
    }

    @Cacheable
    public User findById(Integer id) {
        return userDao.getReferenceById(id);
    }

    @Caching(evict = {@CacheEvict(value = "user", allEntries = true)},
            put = {@CachePut(value = "user", key = "#user.id")})
    @Transactional
    public User persist(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDao.getReferenceById(user.getId()).getPassword());
        }
        return userDao.save(user);
    }

    @Cacheable
    public boolean delete(Integer id) {
    User user = userDao.getReferenceById(id);
    user.setEnabled(false);
    userDao.save(user);
        return false;
    }

    @Cacheable
    public List<User> search(User user) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> userExample = Example.of(user, matcher);
        return userDao.findAll(userExample);
    }

    @Cacheable
    public Integer findByUserIdByUserName(String userName) {
        return userDao.findUserIdByUserName(userName);
    }

    @Cacheable
    @Transactional(readOnly = true)
    public User findByUserName(String name) {
        return userDao.findByUsername(name);
    }

    @Cacheable
    public User findUserByEmployee(UserDetails userDetails) {
        return userDao.findByUserDetails(userDetails);
    }

    @Cacheable
    public boolean findByEmployee(UserDetails userDetails) {
        return userDao.findByUserDetails(userDetails) == null;
    }


}