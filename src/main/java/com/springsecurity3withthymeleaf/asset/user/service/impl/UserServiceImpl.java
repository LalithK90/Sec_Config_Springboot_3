package com.springsecurity3withthymeleaf.asset.user.service.impl;


import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import com.springsecurity3withthymeleaf.asset.user.dao.UserDao;
import com.springsecurity3withthymeleaf.asset.user.dto.UserRegisteredDTO;
import com.springsecurity3withthymeleaf.asset.user.entity.User;
import com.springsecurity3withthymeleaf.asset.user.service.UserService;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.util.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"user"} )
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final CommonService commonService;

    @Cacheable
    public List< User > findAll() {
        return userDao.findAll();
    }

    @Cacheable
    public User findById(Long id) {
        return userDao.getReferenceById(id);
    }

    @Caching(evict = {@CacheEvict(value = "user", allEntries = true)},
            put = {@CachePut(value = "user", key = "#user.id")})
    public User persist(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDao.getReferenceById(user.getId()).getPassword());
        }
        return userDao.save(user);
    }

    @Caching(evict = {@CacheEvict(value = "user", allEntries = true)})
    public boolean delete(Long id) {
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
    public Long findByUserIdByUserName(String userName) {
        return userDao.findUserIdByUserName(userName);
    }

    @Cacheable
    @Transactional(readOnly = true)
    public User findByUserName(String name) {
        return userDao.findByUsername(name);
    }

    @Cacheable
    public User findUserByEmployee(UserProfile userProfile) {
        return userDao.findByUserProfile(userProfile);
    }

    @Cacheable
    public boolean findByEmployee(UserProfile userProfile) {
        return userDao.findByUserProfile(userProfile) == null;
    }

    public boolean existsUsername(String username) {
        return userDao.existsByUsername(username);
    }



    public User persist(UserRegisteredDTO userRegisteredDTO) {
        commonService.printAttributesInObject(userRegisteredDTO);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities((Set<Role>) user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}