package com.example.clinside.domain.post.presentation.dto.response;

import com.example.clinside.domain.post.domain.types.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CategoryPostResponse {

    private Integer id;
    private Integer userId;

    private String title;

    private String name;

    private Integer likeCounts;
    private Integer hateCounts;

    private String image;

    private Category category;
}
