package com.example.clinside.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResultResponse {

    private final List<PostResponse> posts;
}
