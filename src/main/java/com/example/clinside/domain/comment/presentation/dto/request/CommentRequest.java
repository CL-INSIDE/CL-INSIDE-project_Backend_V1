package com.example.clinside.domain.comment.presentation.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CommentRequest {

    @NotBlank
    @Size(min = 1, max = 200, message = "댓글은 1자 이상 200자 이하입니다.")
    private String content;
}
