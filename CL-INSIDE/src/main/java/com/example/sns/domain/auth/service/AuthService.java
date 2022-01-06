package com.example.sns.domain.auth.service;

import com.example.sns.domain.auth.exception.RefreshTokenNotFoundException;
import com.example.sns.domain.auth.exception.UserIdAlreadyExistException;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import com.example.sns.domain.auth.domain.types.Role;
import com.example.sns.domain.auth.domain.RefreshToken;
import com.example.sns.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.presentation.dto.request.LoginRequest;
import com.example.sns.domain.auth.presentation.dto.request.SignupRequest;
import com.example.sns.domain.auth.presentation.dto.response.TokenResponse;
import com.example.sns.global.exception.*;
import com.example.sns.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.exp.refresh}")
    private Long REFRESH_TOKEN_VALID_TIME;

    public void signup(SignupRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw UserIdAlreadyExistException.EXCEPTION;

        userRepository.save(User.builder()
                        .email(request.getEmail())
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.ROLE_USER)
                        .build());
    }

    public TokenResponse login(LoginRequest request){
        User user =
        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw InvalidPasswordException.EXCEPTION;

        return createToken(request.getEmail());
    }

    public TokenResponse createToken(String name){
        String accessToken = jwtTokenProvider.createJwtAccessToken(name);
        String refreshToken = jwtTokenProvider.createJwtRefreshToken(name);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .username(name)
                        .refreshToken(refreshToken)
                        .refreshExpiration(REFRESH_TOKEN_VALID_TIME)
                        .build());

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse reissue(String refreshToken) {
        if(!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }

        RefreshToken newRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(refresh -> refreshTokenRepository.save(
                        refresh.update(REFRESH_TOKEN_VALID_TIME)
                ))
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        return new TokenResponse(jwtTokenProvider.createJwtAccessToken(newRefreshToken.getUsername()), refreshToken);
    }

}
