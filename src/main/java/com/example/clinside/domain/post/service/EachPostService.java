package com.example.clinside.domain.post.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.comment.domain.repository.CommentRepository;
import com.example.clinside.domain.emotion.domain.repository.HateRepository;
import com.example.clinside.domain.emotion.domain.repository.LikeRepository;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.post.presentation.dto.response.EachPostResponse;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EachPostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final HateRepository hateRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;

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
                            .comments(commentRepository.findByPostId(id))
                            .isMine(checkMine(id))
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    private boolean checkLike(Integer id) {
        return likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkHate(Integer id) {
        return hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkMine(Integer id) {
        Integer userId = UserFacade.getUserId();

        return postRepository.findById(id)
                .filter(u -> userId != null)
                .map(post -> post.getUser().getId().equals(userId))
                .isPresent();
    }
}

