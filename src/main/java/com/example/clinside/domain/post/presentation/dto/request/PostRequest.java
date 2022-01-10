package com.example.clinside.domain.post.presentation.dto.request;

import com.example.clinside.domain.post.domain.types.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

    private Category category;

    @NotNull
    private MultipartFile image;
}
