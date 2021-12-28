package com.example.sns.domain.post.service;

import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemovePostService {

    private final PostRepository postRepository;

    public void execute(Integer id){
        if (postRepository.findById(id).isEmpty())
            throw PostNotFoundException.EXCEPTION;

        postRepository.deleteById(id);
    }
}
