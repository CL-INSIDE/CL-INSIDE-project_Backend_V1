package com.example.clinside.domain.user.presentation.dto.response;

import com.example.clinside.domain.post.domain.types.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserProfileResponse {

    private Integer postId;

    private String title;

    private String image;

    private Category category;
}
