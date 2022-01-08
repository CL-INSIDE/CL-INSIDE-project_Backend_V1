package com.example.clinside.domain.emotion.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.emotion.domain.Like;
import com.example.clinside.domain.emotion.domain.repository.LikeRepository;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.domain.emotion.exception.LikeAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddLikePostService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void execute(Integer id) {
        if (likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent())
            throw LikeAlreadyExistsException.EXCEPTION;

        likeRepository.save(
                Like.builder()
                        .post(postRepository.findById(id)
                                .orElseThrow(() -> PostNotFoundException.EXCEPTION))
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
