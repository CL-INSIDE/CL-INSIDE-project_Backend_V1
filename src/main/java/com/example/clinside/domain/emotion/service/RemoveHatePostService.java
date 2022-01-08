package com.example.clinside.domain.emotion.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.emotion.domain.repository.HateRepository;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.domain.emotion.exception.HateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveHatePostService {

    private final PostRepository postRepository;
    private final HateRepository hateRepository;

    public void execute(Integer id) {
        hateRepository.delete(
                hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id)
                        .orElseThrow(() -> HateNotFoundException.EXCEPTION)
        );
        postRepository.findById(id)
                .map(post -> post.removeHateCounts())
                .map(post -> post.getUser().removeHateCounts())
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
