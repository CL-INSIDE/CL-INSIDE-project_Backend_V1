package com.example.clinside.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = 1, max = 12, message = "이름은 1자 이상 12자 이하입니다.")
    private String name;

    @Email
    @NotBlank
    @Size(min = 1, max = 36, message = "이메일은 1자 이상 36자 이하입니다.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 16, message = "비밀번호는 1자 이상 36자 이하입니다.")
    private String password;
}
