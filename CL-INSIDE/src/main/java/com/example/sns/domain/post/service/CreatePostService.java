package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.dto.request.PostRequest;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostRepository postRepository;

    public void execute(PostRequest request){
        postRepository.save(Post.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .user(UserFacade.getUser())
                        .likeCounts(0)
                        .hateCounts(0)
                        .build());
    }
}
