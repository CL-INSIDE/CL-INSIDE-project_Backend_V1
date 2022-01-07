package com.example.sns.domain.user.service;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import com.example.sns.domain.image.domain.Image;
import com.example.sns.domain.image.domain.repository.ImageRepository;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.sns.domain.user.presentation.dto.response.UserProfileResponse;
import com.example.sns.domain.user.presentation.dto.response.UserProfilePostResponse;
import com.example.sns.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public UserProfilePostResponse execute(Integer id){
         UserInfoResponse userInfo = userRepository.findById(id)
                .map(user -> {
                    UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                            .userId(id)
                            .name(user.getName())
                            .build();
                    return userInfoResponse;
                })
                 .orElseThrow(() -> UserNotFoundException.EXCEPTION);

         List<UserProfileResponse> posts = postRepository.findPostByUserId(id)
                .stream()
                .map(post -> {
                    UserProfileResponse response = UserProfileResponse.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl).orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());

        return new UserProfilePostResponse(userInfo, posts);
    }
}
