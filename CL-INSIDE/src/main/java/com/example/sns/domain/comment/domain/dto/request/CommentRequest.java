package com.example.sns.domain.comment.domain.dto.request;

import com.example.sns.domain.auth.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor
public class CommentRequest {

    private String content;
}
