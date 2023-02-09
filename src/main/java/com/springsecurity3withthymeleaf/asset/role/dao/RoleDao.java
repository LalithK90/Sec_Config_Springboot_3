package com.springsecurity3withthymeleaf.asset.role.dao;


import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository< Role, Integer > {
    Role findByRoleName(String roleName);
}
