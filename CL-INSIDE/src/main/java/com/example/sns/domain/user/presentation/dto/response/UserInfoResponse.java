package com.example.sns.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    private Integer userId;
    private String name;

}
