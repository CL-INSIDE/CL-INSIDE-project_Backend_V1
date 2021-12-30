package com.example.sns.domain.comment.domain.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentRequest {

    private String content;

    private Integer postId;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
