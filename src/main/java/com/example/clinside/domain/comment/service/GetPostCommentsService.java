package com.example.clinside.domain.comment.service;

import com.example.clinside.domain.comment.presentation.dto.response.CommentResponse;
import com.example.clinside.domain.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPostCommentsService {

    private final CommentRepository commentRepository;

    public List<CommentResponse> execute(Integer id) {
        return commentRepository.findByPostId(id)
                .stream()
                .map(comment -> {
                    CommentResponse response = CommentResponse.builder()
                            .commentId(comment.getId())
                            .userId(comment.getUser().getId())
                            .content(comment.getContent())
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}