package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.emotion.domain.Like;
import com.example.sns.domain.emotion.domain.repository.LikeRepository;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.global.exception.LikeAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddLikePostService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void execute(Integer id){
        if (likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent())
            throw LikeAlreadyExistsException.EXCEPTION;

        likeRepository.save(
                Like.builder()
                        .post(postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION))
                        .user(UserFacade.getUser())
                        .build());

        postRepository.findById(id)
                .map(post -> {
                    post.getUser().addLikeCounts();
                    post.addPostLikeCounts();
                    return post;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
