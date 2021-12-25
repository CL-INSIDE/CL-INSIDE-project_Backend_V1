package com.example.sns.domain.post.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResultResponse {
    private final Integer userId;
    private final List<PostResponse> posts;
}
