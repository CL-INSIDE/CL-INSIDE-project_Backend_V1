package com.example.sns.domain.comment.service;

import com.example.sns.domain.auth.domain.repository.UserRepository;
import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.comment.domain.dto.request.CommentRequest;
import com.example.sns.domain.comment.domain.repository.CommentRepository;
import com.example.sns.domain.comment.facade.CommentFacade;
import com.example.sns.domain.post.facade.PostFacade;
import com.example.sns.global.exception.InvalidRoleException;
import com.example.sns.global.security.auth.UserDetails;
import com.example.sns.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AddCommentService {

    private final CommentRepository commentRepository;
    private final PostFacade postFacade;
    private final UserFacade userFacade;
    private final UserUtil util;
    private final CommentFacade commentFacade;

    public void execute(Integer postId, String content) {

        Object principal = util.getPrincipal();

        if (principal instanceof UserDetails) {
            commentFacade.createComment(postId, content, userFacade.getCurrentUser());
        } else throw InvalidRoleException.EXCEPTION;

    }
}
