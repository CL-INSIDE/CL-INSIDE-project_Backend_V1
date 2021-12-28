package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.emotion.domain.repository.HateRepository;
import com.example.sns.domain.emotion.domain.repository.LikeRepository;
import com.example.sns.domain.post.domain.dto.response.EachPostResponse;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEachPostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final HateRepository hateRepository;

    public EachPostResponse execute(Integer id) {
        return postRepository.findById(id)
                .map(post -> {
                    EachPostResponse response = EachPostResponse.builder()
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdDate(post.getCreatedDate())
                            .updatedDate(post.getUpdatedDate())
                            .isLike(checkLike(id))
                            .isHate(checkHate(id))
                            .getLikes(post.getLikeCounts())
                            .getHates(post.getHateCounts())
                            .isMine(checkMine(id))
                            .build();
                    return response;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    private boolean checkLike(Integer id){
        return likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkHate(Integer id){
        return hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkMine(Integer id){
        Integer userId = UserFacade.getUserId();

        return postRepository.findById(id)
                .filter(u -> userId != null)
                .map(post -> post.getUser().getId().equals(userId))
                .isPresent();
    }
}

