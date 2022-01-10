package com.example.clinside.domain.user.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.emotion.domain.repository.LikeRepository;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.post.presentation.dto.response.PostResponse;
import com.example.clinside.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUserLikePosts {

    private final LikeRepository likeRepository;
    private final ImageRepository imageRepository;

    public List<PostResponse> execute() {
        return likeRepository.findByUserId(UserFacade.getUserId())
                .stream()
                .map(like -> {
                    PostResponse response = PostResponse.builder()
                            .id(like.getPost().getId())
                            .userId(like.getUser().getId())
                            .title(like.getPost().getTitle())
                            .content(like.getPost().getContent())
                            .isLike(true)
                            .category(like.getPost().getCategory())
                            .image(imageRepository.findByPostId(like.getPost().getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
