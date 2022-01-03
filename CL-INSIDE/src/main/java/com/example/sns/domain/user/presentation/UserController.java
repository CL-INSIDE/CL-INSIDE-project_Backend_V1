package com.example.sns.domain.user.presentation;

import com.example.sns.domain.user.domain.dto.UserProfileResponse;
import com.example.sns.domain.user.service.GetUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final GetUserProfileService getUserProfileService;

    @GetMapping("/profile/{user-id}")
    public UserProfileResponse getUserProfile(@PathVariable (name = "user-id") Integer id){
        return getUserProfileService.execute(id);
    }
}
