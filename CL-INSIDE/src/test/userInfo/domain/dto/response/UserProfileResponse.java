package com.example.sns.domain.userInfo.domain.dto.response;

import com.example.sns.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Integer userId;
    private String name;
    private Integer getLikeCount;

    private List<Post> allPosts;
}
