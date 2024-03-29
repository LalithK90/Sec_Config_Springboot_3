package com.springsecurity3withthymeleaf.asset.user_profile.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.springsecurity3withthymeleaf.asset.common_asset.model.FileInfo;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Gender;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.StopActive;
import com.springsecurity3withthymeleaf.asset.common_asset.model.enums.Title;
import com.springsecurity3withthymeleaf.util.audit.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "UserProfile" )
public class UserProfile extends AuditEntity {

  @Size( min = 5, message = "Your name cannot be accepted" )
  private String name;

  @Column( nullable = false )
  private String number;

  @Size( min = 5, message = "At least 5 characters should be included calling name" )
  private String callingName;

  @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
  @Column( unique = true )
  private String nic;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String mobileOne;

  private String mobileTwo;

  private String land;

  @Column( unique = true )
  private String email;

  @Column( unique = true )
  private String officeEmail;

  @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL")
  private String address;

  @Enumerated( EnumType.STRING )
  private Title title;

  @Enumerated( EnumType.STRING )
  private Gender gender;

  @Enumerated( EnumType.STRING )
  private StopActive stopActive;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate dateOfBirth;


  @Transient
  private MultipartFile file;

  @Transient
  private FileInfo fileInfo;



}
