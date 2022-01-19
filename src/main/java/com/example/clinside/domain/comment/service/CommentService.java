package com.example.clinside.domain.comment.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.comment.domain.Comment;
import com.example.clinside.domain.comment.exception.CommentNotFoundException;
import com.example.clinside.domain.comment.presentation.dto.request.CommentRequest;
import com.example.clinside.domain.comment.domain.repository.CommentRepository;
import com.example.clinside.domain.comment.presentation.dto.response.CommentResponse;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void addComment(Integer postId, CommentRequest request) {

        commentRepository.save(Comment.builder()
                .content(request.getContent())
                .user(UserFacade.getUser())
                .post(postRepository.findById(postId)
                        .orElseThrow(() -> PostNotFoundException.EXCEPTION))
                .build());
    }

    public List<CommentResponse> getPostComments(Integer id) {
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

    public CommentResponse getComment(Integer id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    CommentResponse response = CommentResponse.builder()
                            .commentId(id)
                            .userId(comment.getUser().getId())
                            .content(comment.getContent())
                            .build();
                    return response;
                })
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }
}
