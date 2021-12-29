package com.example.sns.domain.comment.domain.dto.response;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {

    private String content;

}
