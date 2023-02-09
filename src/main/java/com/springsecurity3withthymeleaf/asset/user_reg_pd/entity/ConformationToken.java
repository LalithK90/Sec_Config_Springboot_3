package com.springsecurity3withthymeleaf.asset.user_reg_pd.entity;


import com.springsecurity3withthymeleaf.asset.user_reg_pd.entity.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ConformationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String token;

    @Column(unique = true, nullable = false)
    private String email;
    private TokenStatus tokenStatus;

    private LocalDateTime createDate;

    private LocalDateTime endDate;

    public ConformationToken(String email,TokenStatus tokenStatus) {
        this.email = email;
        this.token = UUID.randomUUID().toString();
        this.createDate = LocalDateTime.now();
        this.endDate = createDate.plusDays(1);
        this.tokenStatus = tokenStatus;
    }
}
