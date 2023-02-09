package com.springsecurity3withthymeleaf.asset.common_asset.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordChange {

    private String username;

    @Size( min = 4, message = "password should include four characters or symbols" )
    private String oldPassword;

    @Size( min = 4, message = "password should include four characters or symbols" )
    private String newPassword;

    @Size( min = 4, message = "password should include four characters or symbols" )
    private String newPasswordConform;

}