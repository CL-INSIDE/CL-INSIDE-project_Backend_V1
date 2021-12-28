package com.example.sns.domain.emotion.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.emotion.domain.repository.LikeRepository;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.global.exception.LikeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveLikePostService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void execute(Integer id){
        likeRepository.delete(
                likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id)
                        .orElseThrow(() -> LikeNotFoundException.EXCEPTION)
        );
        postRepository.findById(id)
                .map(post -> post.removeLikeCounts())
                .map(post -> post.getUser().removeLikeCounts())
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
