package com.example.sns.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserInfoResponse {

    private Integer postId;

    private String title;

    private String image;
}
