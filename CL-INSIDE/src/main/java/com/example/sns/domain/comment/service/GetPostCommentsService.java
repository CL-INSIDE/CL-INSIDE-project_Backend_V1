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
        return commentRepository.findById(id)
                .stream()
                .map(comment -> {
                    CommentResponse response = CommentResponse.builder()
                            .content(comment.getContent())
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
