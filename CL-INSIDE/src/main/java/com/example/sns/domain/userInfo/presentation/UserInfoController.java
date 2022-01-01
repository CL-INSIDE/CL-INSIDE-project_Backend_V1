package com.example.sns.domain.userInfo.presentation;

import com.example.sns.domain.post.domain.dto.response.PostResponse;
import com.example.sns.domain.post.service.GetUserLikePosts;
import com.example.sns.domain.userInfo.domain.dto.response.UserProfileResponse;
import com.example.sns.domain.userInfo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserProfileService userProfileService;

    @GetMapping("/userinfo/{user-id}")
    public UserProfileResponse getUserProfile(@PathVariable(name = "user-id") Integer id){
        return userProfileService.execute(id);
    }
}
