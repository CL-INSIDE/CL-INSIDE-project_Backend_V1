package com.example.sns.domain.post.service;

import com.example.sns.domain.post.domain.dto.request.PostRequest;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModifyPostService {

    private final PostRepository postRepository;

    public void execute(Integer id, PostRequest request){
        postRepository.findById(id)
                .map(newPost -> newPost.updatePost(
                        request.getTitle(),
                        request.getContent()
                ))
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
