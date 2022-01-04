package com.example.sns.domain.comment.service;

import com.example.sns.domain.comment.domain.dto.response.CommentResponse;
import com.example.sns.domain.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPostCommentsService {

    private final CommentRepository commentRepository;

    public List<CommentResponse> execute(Integer id){
        return commentRepository.findByPostId(id)
                .stream()
                .map(comment -> {
                    CommentResponse response = CommentResponse.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .userInfo(comment.getUsers()
                                    .stream()
                                    .map(newUser -> newUser.userList(
                                            newUser.getId(),
                                            newUser.getName()
                                    ))
                                    .collect(Collectors.toList()))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
