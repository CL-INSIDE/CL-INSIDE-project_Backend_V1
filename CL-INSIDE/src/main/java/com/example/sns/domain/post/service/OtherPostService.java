package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.emotion.domain.repository.HateRepository;
import com.example.sns.domain.emotion.domain.repository.LikeRepository;
import com.example.sns.domain.post.domain.dto.response.GetPostResponse;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final HateRepository hateRepository;

    public List<GetPostResponse> execute(){
        return postRepository.calculateDesc()
                .stream()
                .map(post -> {
                    GetPostResponse response = GetPostResponse.builder()
                            .title(post.getTitle())
                            .getLikes(post.getLikeCounts())
                            .getHates(post.getHateCounts())
                            .isLike(checkLike(post.getId()))
                            .isHate(checkHate(post.getId()))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
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
