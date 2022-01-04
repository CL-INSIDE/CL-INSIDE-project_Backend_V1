package com.example.sns.domain.user.service;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.dto.response.PostResponse;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.user.domain.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUserProfileService {

    private final UserRepository userRepository;

    public UserProfileResponse execute(Integer id){
        return userRepository.findById(id)
                .map(user -> {
                    UserProfileResponse response = UserProfileResponse.builder()
                            .userId(id)
                            .name(user.getName())
                            .allPosts(user.getPosts()
                                    .stream()
                                    .map(newPost -> newPost.postList(
                                            newPost.getId(),
                                            newPost.getTitle()
                                    ))
                                    .collect(Collectors.toList())
                            )
                            .build();
                    return response;
                })
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        }
}
