package com.example.sns.domain.user.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Integer userId;
    private String name;
    private Integer getLikeCount;
    private PostInfo postInfo;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostInfo {

        private String title;
        private Integer postId;
    }
}
