package com.example.clinside.domain.user.service;

import com.example.clinside.domain.auth.domain.repository.UserRepository;
import com.example.clinside.domain.auth.exception.UserNotFoundException;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.clinside.domain.user.presentation.dto.response.UserProfileResponse;
import com.example.clinside.domain.user.presentation.dto.response.UserProfilePostResponse;
import com.example.clinside.domain.image.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public UserProfilePostResponse execute(Integer id) {
        UserInfoResponse userInfo = userRepository.findById(id)
                .map(user -> {
                    UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                            .userId(id)
                            .email(user.getEmail())
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
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl).orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());

        return new UserProfilePostResponse(userInfo, posts);
    }
}
