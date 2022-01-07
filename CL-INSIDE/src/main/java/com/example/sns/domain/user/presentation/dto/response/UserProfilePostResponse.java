package com.example.sns.domain.user.presentation.dto.response;

import com.example.sns.domain.image.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserProfilePostResponse {

    private final UserInfoResponse userInfo;

    private final List<UserProfileResponse> posts;

}
