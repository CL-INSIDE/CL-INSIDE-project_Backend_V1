package com.example.clinside.domain.post.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.emotion.domain.repository.LikeRepository;
import com.example.clinside.domain.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUserLikePosts {

    private final LikeRepository likeRepository;

    public List<PostResponse> execute() {
        return likeRepository.findByUserId(UserFacade.getUserId())
                .stream()
                .map(like -> {
                    PostResponse response = PostResponse.builder()
                            .id(like.getPost().getId())
                            .title(like.getPost().getTitle())
                            .content(like.getPost().getContent())
                            .isLike(true)
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
