package com.example.sns.domain.post.domain.dto.response;

import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.comment.domain.dto.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
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

    private List<Comment> comments;
}
