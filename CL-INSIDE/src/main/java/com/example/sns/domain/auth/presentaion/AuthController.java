package com.example.sns.domain.auth.presentaion;

import com.example.sns.domain.auth.payload.request.LoginRequest;
import com.example.sns.domain.auth.payload.request.SignupRequest;
import com.example.sns.domain.auth.payload.response.TokenResponse;
import com.example.sns.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid SignupRequest request){
        authService.signup(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }

    @PutMapping("/reissue")
    public TokenResponse reissue(@RequestHeader(name = "x-refresh-token")String token){
        return authService.reissue(token);
    }
}
