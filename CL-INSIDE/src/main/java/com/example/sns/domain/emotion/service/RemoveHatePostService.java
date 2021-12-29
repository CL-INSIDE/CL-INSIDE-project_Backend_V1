package com.example.sns.domain.emotion.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.emotion.domain.repository.HateRepository;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.domain.emotion.exception.HateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveHatePostService {

    private final PostRepository postRepository;
    private final HateRepository hateRepository;

    public void execute(Integer id){
        hateRepository.delete(
                hateRepository.findByUserIdAndPostId(UserFacade.getUser().getId(), id)
                        .orElseThrow(() -> HateNotFoundException.EXCEPTION)
        );
        postRepository.findById(id)
                .map(post -> post.removeHateCounts())
                .map(post -> post.getUser().removeHateCounts())
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
