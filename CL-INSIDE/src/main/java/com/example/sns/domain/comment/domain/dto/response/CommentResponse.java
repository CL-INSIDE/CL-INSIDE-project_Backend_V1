package com.example.sns.domain.comment.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {

    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
