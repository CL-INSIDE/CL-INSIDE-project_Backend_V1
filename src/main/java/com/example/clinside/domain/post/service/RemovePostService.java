package com.example.clinside.domain.post.service;

import com.example.clinside.domain.image.service.ImageService;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RemovePostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    public void execute(Integer id) {
        if (postRepository.findById(id).isEmpty())
            throw PostNotFoundException.EXCEPTION;

        imageService.removeImage(id);
        postRepository.deleteById(id);
    }
}
