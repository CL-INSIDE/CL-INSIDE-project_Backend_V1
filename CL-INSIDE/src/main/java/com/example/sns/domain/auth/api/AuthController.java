package com.example.sns.domain.auth.api;

import com.example.sns.domain.auth.payload.request.LoginRequest;
import com.example.sns.domain.auth.payload.request.RegisterRequest;
import com.example.sns.domain.auth.payload.response.TokenResponse;
import com.example.sns.domain.auth.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest request){
        authService.register(request);
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
