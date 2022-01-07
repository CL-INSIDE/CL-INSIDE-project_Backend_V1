package com.example.sns.domain.comment.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.comment.presentation.dto.request.CommentRequest;
import com.example.sns.domain.comment.domain.repository.CommentRepository;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void execute(Integer postId, CommentRequest request) {

        commentRepository.save(Comment.builder()
                        .content(request.getContent())
                        .user(UserFacade.getUser())
                        .post(postRepository.findById(postId)
                                     .orElseThrow(() -> PostNotFoundException.EXCEPTION))
                        .build());
    }
}
