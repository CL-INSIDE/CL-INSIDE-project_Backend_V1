package com.example.sns.domain.user.service;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.exception.UserNotFoundException;
import com.example.sns.domain.user.domain.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileResponse execute(Integer id){
        return userRepository.findById(id)
                .map(user -> {
                    UserProfileResponse response = UserProfileResponse.builder()
                            .userId(id)
                            .name(user.getName())
                            .getLikeCount(user.getEveryLikeCounts())
                            .postInfo(
                                    UserProfileResponse.PostInfo.builder()
                                            .postId(user.getPost().getId())
                                            .title(user.getPost().getTitle())
                                            .build())
                            .build();
                    return response;
                })
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }
}
