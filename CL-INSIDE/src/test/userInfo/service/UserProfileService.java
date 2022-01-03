package com.example.sns.domain.userInfo.service;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.emotion.domain.repository.LikeRepository;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.dto.response.PostResponse;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.domain.userInfo.domain.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public UserProfileResponse execute(Integer id){
        return userRepository.findById(id)
                .map(user -> {
                    UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                            .userId(id)
                            .name(user.getName())
                            .allPosts(postRepository.findPostByUserId(id)
                                    .stream()
                                    .map(post -> {
                                        Post posts = Post.builder()
                                                .title(post.getTitle())
                                                .likeCounts(post.getLikeCounts())
                                                .build();
                                        return posts;
                                    })
                                    .collect(Collectors.toList()))

                            .build();
                }
    }
}
