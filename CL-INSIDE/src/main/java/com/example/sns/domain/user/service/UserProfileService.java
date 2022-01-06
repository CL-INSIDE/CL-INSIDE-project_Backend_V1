package com.example.sns.domain.user.service;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.user.presentation.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserProfileResponse execute(Integer id){
        return userRepository.findById(id)
                .map(user -> {
                    UserProfileResponse response = UserProfileResponse.builder()
                            .userId(id)
                            .name(user.getName())
                            .getLikeCount(user.getEveryLikeCounts())
                            .posts(postRepository.findPostByUserId())
                            .build();
                    return response;
                })
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }
}
