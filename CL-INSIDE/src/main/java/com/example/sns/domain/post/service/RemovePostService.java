package com.example.sns.domain.post.service;

import com.example.sns.domain.image.service.ImageService;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RemovePostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    public void execute(Integer id){
        if (postRepository.findById(id).isEmpty())
            throw PostNotFoundException.EXCEPTION;

        imageService.removeImage(id);
        postRepository.deleteById(id);
    }
}
