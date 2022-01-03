package com.example.sns.domain.user.domain.dto;

import com.example.sns.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class UserProfileResponse {

    private Integer userId;
    private String name;
    private List<Post> allPosts;
}
