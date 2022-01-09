package com.example.clinside.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RandomPostsResponse {

    private Integer userId;
    private Integer postId;

    private String title;
    private Integer likeCounts;
    private String image;
}
