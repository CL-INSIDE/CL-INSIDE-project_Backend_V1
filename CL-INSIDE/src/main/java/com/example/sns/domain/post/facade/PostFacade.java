package com.example.sns.domain.post.facade;

import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostFacade {

    private final PostRepository postRepository;

    public static Post getPost(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Post) authentication.getPrincipal();
    }

    public Post getPostId(Integer postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

}
