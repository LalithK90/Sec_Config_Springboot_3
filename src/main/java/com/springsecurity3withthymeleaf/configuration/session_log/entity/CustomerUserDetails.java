package com.springsecurity3withthymeleaf.configuration.session_log.entity;


import com.springsecurity3withthymeleaf.asset.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomerUserDetails implements UserDetails, Serializable {

    private User user;


    @Override
    @Transactional(readOnly = true)
    public Collection<? extends GrantedAuthority > getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof final CustomerUserDetails other) )
            return false;
        if ( !other.canEqual(this) ) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        return Objects.equals(this$user, other$user);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CustomerUserDetails;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }
}