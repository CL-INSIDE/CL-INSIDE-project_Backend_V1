package com.example.clinside.domain.post.presentation.dto.response;

import com.example.clinside.domain.post.domain.types.Category;
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
    private Category category;
    private Integer likeCounts;
    private String image;
}
