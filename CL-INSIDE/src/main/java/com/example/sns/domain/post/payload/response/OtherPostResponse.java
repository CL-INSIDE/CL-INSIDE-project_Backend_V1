package com.example.sns.domain.post.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OtherPostResponse {
    private int id;
    private String title;
    private String image;
    private boolean isLiked;
    private boolean isHate;
}
