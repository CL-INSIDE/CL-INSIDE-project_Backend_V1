package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.image.domain.Image;
import com.example.sns.domain.image.domain.repository.ImageRepository;
import com.example.sns.domain.post.presentation.dto.response.PostResponse;
import com.example.sns.domain.post.presentation.dto.response.PostResultResponse;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public PostResultResponse execute(String keyword, Pageable pageable) {

        List<PostResponse> posts  = postRepository.findByTitleContaining(keyword, pageable)
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .id(post.getId())
                            .userId(post.getUser().getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());

        return new PostResultResponse(posts);
    }
}
