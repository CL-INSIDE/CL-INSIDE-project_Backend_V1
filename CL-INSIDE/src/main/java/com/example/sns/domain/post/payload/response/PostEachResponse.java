package com.example.sns.domain.post.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostEachResponse {
    private String title;
    private String content;
    private boolean isLiked;
    private boolean isHate;
    private boolean isMine;
    private Integer getLike;
    private Integer getHate;
    private String image;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private UserInfo userInfo;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserInfo {
        private Integer userId;
        private String name;
        private Integer postsCounts;
        private Integer everyLikeCounts;
        private Integer everyHateCounts;
    }
}
