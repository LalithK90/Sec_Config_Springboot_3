package com.springsecurity3withthymeleaf.asset.role.service;


import com.springsecurity3withthymeleaf.asset.role.entity.Role;

import java.util.List;

public interface RoleService {
    List< Role > findAll();
    Role findById(Integer id);
    Role persist(Role role);
    boolean delete(Integer id);
    List< Role > search(Role role);
    Role findByRoleName(String roleName);

}
