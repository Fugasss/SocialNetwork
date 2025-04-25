package com.socialnetwork.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 32)
    private String password;

    @NotBlank
    @Email
    private String email;
}
