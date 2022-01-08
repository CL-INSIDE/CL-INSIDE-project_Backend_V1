package com.example.sns.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class OtherPostResponse {

    private String title;

    private String content;

    private Integer getLikes;
    private Integer getHates;
    private Integer calculate;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private boolean isLike;
    private boolean isHate;
}
