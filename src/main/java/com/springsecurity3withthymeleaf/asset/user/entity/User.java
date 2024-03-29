package com.springsecurity3withthymeleaf.asset.user.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springsecurity3withthymeleaf.asset.role.entity.Role;
import com.springsecurity3withthymeleaf.asset.user.entity.enums.AProvider;
import com.springsecurity3withthymeleaf.asset.user_profile.entity.UserProfile;
import com.springsecurity3withthymeleaf.util.audit.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "createdDate", allowGetters = true)
public class User extends AuditEntity {

    @Column(nullable = false,unique = true)
    @Email(message="Please provide valid email for this process.")
    private String username;

    @Column(nullable = false)
    @Size(min = 4, message = "Password should include four characters or symbols")
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private AProvider aProvider;

    @Column(nullable = false)
    private boolean enabled;

    @OneToOne
    private UserProfile userProfile;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch( FetchMode.SUBSELECT)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


}
