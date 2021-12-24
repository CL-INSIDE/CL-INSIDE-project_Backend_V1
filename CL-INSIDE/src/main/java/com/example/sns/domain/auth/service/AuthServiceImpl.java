package com.example.sns.domain.auth.service;

import com.example.sns.domain.auth.entity.user.Role;
import com.example.sns.domain.auth.entity.refreshToken.RefreshToken;
import com.example.sns.domain.auth.entity.refreshToken.RefreshTokenRepository;
import com.example.sns.domain.auth.entity.user.User;
import com.example.sns.domain.auth.entity.user.UserRepository;
import com.example.sns.domain.auth.payload.request.LoginRequest;
import com.example.sns.domain.auth.payload.request.RegisterRequest;
import com.example.sns.domain.auth.payload.response.TokenResponse;
import com.example.sns.global.exception.*;
import com.example.sns.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 1 * 1000L;

    @Override
    public void register(RegisterRequest request){
        if (userRepository.findById(request.getId()).isPresent())
            throw UserIdAlreadyExistException.EXCEPTION;

        userRepository.save(User.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.ROLE_USER)
                        .build());
    }

    @Override
    public TokenResponse login(LoginRequest request){
        User user =
        userRepository.findById(request.getId())
                .orElseThrow(() ->UserNotFoundException.EXCEPTION);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw InvalidPasswordException.EXCEPTION;

        return createToken(request.getId());
    }

    @Override
    public TokenResponse createToken(String name){
        String accessToken = jwtTokenProvider.createJwtAccessToken(name);
        String refreshToken = jwtTokenProvider.createJwtRefreshToken(name);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .name(name)
                        .refreshToken(refreshToken)
                        .refreshExpiration(REFRESH_TOKEN_VALID_TIME)
                        .build());

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public TokenResponse reissue(String refreshToken) {
        if(!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }

        RefreshToken newRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(refresh -> refreshTokenRepository.save(
                        refresh.update(REFRESH_TOKEN_VALID_TIME)
                ))
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        return new TokenResponse(jwtTokenProvider.createJwtAccessToken(newRefreshToken.getName()), refreshToken);
    }

}
