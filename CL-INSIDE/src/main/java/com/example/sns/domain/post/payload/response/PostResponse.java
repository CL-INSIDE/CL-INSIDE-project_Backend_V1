package com.example.sns.domain.post.payload.response;

import com.example.sns.domain.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {

    private Integer id;
    private String title;
    private String image;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isLiked;
    private boolean isHated;
    private Category category;
}
