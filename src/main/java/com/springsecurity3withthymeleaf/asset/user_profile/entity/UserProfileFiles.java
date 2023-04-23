package com.springsecurity3withthymeleaf.asset.user_profile.entity;


import com.springsecurity3withthymeleaf.util.audit.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileFiles extends AuditEntity {

    private String name, mimeType,newName;

    @Column(unique = true)
    private String newId;

    @Lob
    private byte[] pic;
    @ManyToOne
    private UserProfile userProfile;

    public UserProfileFiles(String name, String mimeType, byte[] pic, String newName, String newId) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
        this.newName = newName;
        this.newId = newId;
    }

}
