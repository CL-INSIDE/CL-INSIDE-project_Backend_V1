package com.example.sns.domain.post.presentation.dto.response;

import com.example.sns.domain.image.domain.Image;
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
    private Integer userId;

    private String title;

    private String content;

    private boolean isLike;
    private boolean isHate;

    private String image;
}
