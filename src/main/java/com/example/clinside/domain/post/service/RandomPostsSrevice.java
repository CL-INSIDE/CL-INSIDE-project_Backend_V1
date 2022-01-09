package com.example.clinside.domain.post.service;

import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.presentation.dto.response.RandomPostsResponse;
import com.example.clinside.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RandomPostsSrevice {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public List<RandomPostsResponse> execute(){
        return postRepository.findRandomPosts()
                .stream()
                .map(post -> {
                    RandomPostsResponse response = RandomPostsResponse.builder()
                            .postId(post.getId())
                            .userId(post.getUser().getId())
                            .title(post.getTitle())
                            .likeCounts(post.getLikeCounts())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                }).collect(Collectors.toList());
    }
}
