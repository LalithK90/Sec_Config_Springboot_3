package com.springsecurity3withthymeleaf.asset.company.entity;

import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.StopActive;
import com.springsecurity3withthymeleaf.asset.company.entity.enums.PaidNot;
import com.springsecurity3withthymeleaf.util.audit.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company extends AuditEntity {
  private String name;
  private String registrationNumber;
  private String address;
  private String telephoneNumber;
  private String mobileNumber;
  @Enumerated( EnumType.STRING )
  private StopActive stopActive;
  @Enumerated( EnumType.STRING )
  private PaidNot paidNot;

}
