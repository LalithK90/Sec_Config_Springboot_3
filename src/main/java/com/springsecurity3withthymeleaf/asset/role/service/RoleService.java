package com.springsecurity3withthymeleaf.asset.role.service;


import com.springsecurity3withthymeleaf.asset.role.entity.Role;

import java.util.List;

public interface RoleService {
    List< Role > findAll();
    Role findById(Long id);
    Role persist(Role role);
    boolean delete(Long id);
    List< Role > search(Role role);
    Role findByRoleName(String roleName);

}
