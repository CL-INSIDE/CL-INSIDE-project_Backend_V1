package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.post.domain.dto.response.PostResponse;
import com.example.sns.domain.post.domain.dto.response.PostResultResponse;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPostService {

    private final PostRepository postRepository;

    public PostResultResponse execute(String keyword, Pageable pageable) {

        List<PostResponse> posts  = postRepository.findByTitleContaining(keyword, pageable)
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
                            .content(post.getContent())
                            .build();
                    return response;
                })
                .collect(Collectors.toList());

        return new PostResultResponse(UserFacade.getUser().getId(), posts);
    }
}
