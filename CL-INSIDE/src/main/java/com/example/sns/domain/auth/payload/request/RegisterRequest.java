package com.example.sns.domain.auth.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String id;

    private String password;
}
