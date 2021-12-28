package com.example.sns.domain.auth.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String email;

    private String password;
}
