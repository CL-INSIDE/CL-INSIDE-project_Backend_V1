package com.example.sns.domain.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class EachPostResponse {

    private String title;
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private boolean isLike;
    private boolean isHate;
    private boolean isMine;

    private Integer getLikes;
    private Integer getHates;
}
