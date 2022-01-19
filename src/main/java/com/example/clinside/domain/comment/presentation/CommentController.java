package com.example.clinside.domain.comment.presentation;

import com.example.clinside.domain.comment.presentation.dto.request.CommentRequest;
import com.example.clinside.domain.comment.presentation.dto.response.CommentResponse;
import com.example.clinside.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{post-id}")
    public void addComment(@PathVariable(name = "post-id") Integer postId, @RequestBody @Valid CommentRequest request) {
        commentService.addComment(postId, request);
    }

    @GetMapping("/comment/{post-id}")
    public List<CommentResponse> getPostComments(@PathVariable(name = "post-id") Integer id) {
        return commentService.getPostComments(id);
    }

    @GetMapping("/comment/{comment-id}")
    public CommentResponse getComment(@PathVariable(name = "comment-id") Integer id){
        return commentService.getComment(id);
    }
}
