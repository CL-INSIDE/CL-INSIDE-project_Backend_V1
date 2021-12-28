package com.example.sns.domain.auth.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignupRequest {

    private String name;

    private String email;

    private String password;
}
