package com.example.clinside.domain.user.presentation;

import com.example.clinside.domain.post.presentation.dto.response.PostResponse;
import com.example.clinside.domain.user.service.GetUserLikePosts;
import com.example.clinside.domain.user.presentation.dto.response.UserProfilePostResponse;
import com.example.clinside.domain.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final GetUserLikePosts getUserLikePosts;
    private final UserProfileService userProfileService;

    @GetMapping("/info/{user-id}")
    public UserProfilePostResponse getUserProfile(@PathVariable(name = "user-id") Integer id) {
        return userProfileService.execute(id);
    }

    @GetMapping("/like")
    public List<PostResponse> getLikePosts(){
        return getUserLikePosts.execute();
    }
}
