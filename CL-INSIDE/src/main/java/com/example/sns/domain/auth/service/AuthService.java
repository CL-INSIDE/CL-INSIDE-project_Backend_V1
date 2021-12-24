package com.example.sns.domain.auth.service;

import com.example.sns.domain.auth.payload.request.LoginRequest;
import com.example.sns.domain.auth.payload.request.RegisterRequest;
import com.example.sns.domain.auth.payload.response.TokenResponse;

public interface AuthService {
    void register(RegisterRequest request);
    TokenResponse login(LoginRequest request);
    TokenResponse createToken(String name);
    TokenResponse reissue(String refreshToken);
}
