package com.example.clinside.domain.emotion.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.emotion.domain.Hate;
import com.example.clinside.domain.emotion.domain.repository.HateRepository;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.domain.emotion.exception.HateAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddHatePostService {

    private final PostRepository postRepository;
    private final HateRepository hateRepository;

    public void execute(Integer id) {
        if (hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent())
            throw HateAlreadyExistsException.EXCEPTION;

        hateRepository.save(
                Hate.builder()
                        .post(postRepository.findById(id)
                                .orElseThrow(() -> PostNotFoundException.EXCEPTION))
                        .user(UserFacade.getUser())
                        .build());

        postRepository.findById(id)
                .map(post -> {
                    post.getUser().addHateCounts();
                    post.addHateCounts();
                    return post;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
