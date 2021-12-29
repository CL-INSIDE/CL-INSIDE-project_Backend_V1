package com.example.sns.domain.emotion.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.emotion.domain.Hate;
import com.example.sns.domain.emotion.domain.repository.HateRepository;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import com.example.sns.domain.emotion.exception.HateAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddHatePostService {

    private final PostRepository postRepository;
    private final HateRepository hateRepository;

    public void execute(Integer id){
        if (hateRepository.findByUserIdAndPostId(UserFacade.getUser().getId(), id).isPresent())
            throw HateAlreadyExistsException.EXCEPTION;

        hateRepository.save(
                Hate.builder()
                        .post(postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION))
                        .user(UserFacade.getUser())
                        .build());

        postRepository.findById(id)
                .map(post -> {
                    post.getUser().addHateCounts();
                    post.addPostHateCounts();
                    return post;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
