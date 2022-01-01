package com.example.sns.domain.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {

    private Integer id;

    private String title;

    private String content;

    private boolean isLike;
    private boolean isHate;
}
