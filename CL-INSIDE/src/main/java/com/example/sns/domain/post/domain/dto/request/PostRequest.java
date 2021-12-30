package com.example.sns.domain.post.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostRequest {

    @NotBlank
    @Size(min = 1, max = 30, message = "제목은 1자 이상 30자 이하입니다.")
    private String title;

    @NotBlank
    @Size(min = 1, max = 500, message = "내용은 1자 이상 500자 이하입니다.")
    private String content;

    private boolean isLike;

    private boolean isHate;
}
