package com.example.sns.domain.comment.presentation;

import com.example.sns.domain.comment.presentation.dto.request.CommentRequest;
import com.example.sns.domain.comment.presentation.dto.response.CommentResponse;
import com.example.sns.domain.comment.service.AddCommentService;
import com.example.sns.domain.comment.service.GetPostCommentsService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final GetPostCommentsService getPostCommentsService;
    private final AddCommentService addCommentService;

    @PostMapping("/comment/{post-id}")
    public void addComment(@PathVariable(name = "post-id") Integer postId, @RequestBody @Valid CommentRequest request){
        addCommentService.execute(postId, request);
    }


    @GetMapping("/comment/{post-id}")
    public List<CommentResponse> getPostComments(@PathVariable(name = "post-id") Integer id){
        return getPostCommentsService.execute(id);
    }
}
