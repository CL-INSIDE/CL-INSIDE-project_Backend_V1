package com.example.clinside.domain.post.presentation.dto.response;

import com.example.clinside.domain.post.domain.types.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ManyLikePostsResponse {

    private Integer postId;
    private Integer userId;

    private String title;
    private String image;

    private Category category;

    private Integer likeCounts;
}
