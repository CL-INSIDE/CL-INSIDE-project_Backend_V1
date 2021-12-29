package com.example.sns.domain.comment.facade;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.comment.domain.Comment;
import com.example.sns.domain.comment.domain.dto.response.CommentResponse;
import com.example.sns.domain.comment.domain.repository.CommentRepository;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.domain.repository.PostRepository;
import com.example.sns.domain.post.facade.PostFacade;
import com.example.sns.global.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {


    private final CommentRepository commentRepository;
    private final PostFacade postFacade;

    public void createComment(Integer postId, String content, User user) {
        Post post = postFacade.getPostId(postId);
        commentRepository.save(new Comment(post, content, user));
    }

    public Comment getById(Integer id){
        return commentRepository.findById(id)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }

}
