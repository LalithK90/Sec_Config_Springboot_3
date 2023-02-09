package com.springsecurity3withthymeleaf.asset.attempt.entity;


import com.springsecurity3withthymeleaf.util.audit.AuditEntity;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attempt extends AuditEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate identifiedDate;

    private String sheetName, author, title, lastAuthor,remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

}
