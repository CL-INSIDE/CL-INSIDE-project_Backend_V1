package com.example.clinside.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserProfilePostResponse {

    private final UserInfoResponse userInfo;

    private final List<UserProfileResponse> posts;

}
