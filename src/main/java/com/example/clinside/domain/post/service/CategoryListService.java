package com.example.clinside.domain.post.service;

import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.domain.types.Category;
import com.example.clinside.domain.post.presentation.dto.response.PostResponse;
import com.example.clinside.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryListService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public List<PostResponse> execute(Category category, Pageable pageable){
        return postRepository.findByCategoryContaining(category, pageable)
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .category(post.getCategory())
                            .userId(post.getUser().getId())
                            .isLike(post.isLike())
                            .isHate(post.isHate())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                }).collect(Collectors.toList());
    }
}
