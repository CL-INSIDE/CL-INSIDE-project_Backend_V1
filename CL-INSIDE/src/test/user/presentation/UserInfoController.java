package com.example.sns.domain.user.presentation;

import com.example.sns.domain.user.domain.dto.response.UserProfileResponse;
import com.example.sns.domain.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserProfileService userProfileService;

    @GetMapping("/info/{user-id}")
    public UserProfileResponse getUserProfile(@PathVariable(name = "user-id") Integer id){
        return userProfileService.execute(id);
    }
}
