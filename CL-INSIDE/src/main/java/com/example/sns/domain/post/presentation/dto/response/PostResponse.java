package com.example.sns.domain.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostResponse {

    private Integer id;

    private String title;

    private String content;

    private boolean isLike;
    private boolean isHate;
}
