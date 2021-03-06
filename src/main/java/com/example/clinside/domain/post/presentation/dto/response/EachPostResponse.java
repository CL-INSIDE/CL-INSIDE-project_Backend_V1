package com.example.clinside.domain.post.presentation.dto.response;

import com.example.clinside.domain.comment.domain.Comment;
import com.example.clinside.domain.comment.presentation.dto.response.CommentResponse;
import com.example.clinside.domain.post.domain.types.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    private Category category;

    private List<CommentResponse> comments;

    private String image;
}
