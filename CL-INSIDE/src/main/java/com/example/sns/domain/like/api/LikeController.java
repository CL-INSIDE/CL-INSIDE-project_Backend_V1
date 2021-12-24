package com.example.sns.domain.like.api;

import com.example.sns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final PostService postService;

    @PostMapping("/{post-id}")
    public void addLike(@PathVariable(name = "post-id") Integer id){
        postService.addLike(id);
    }

    @DeleteMapping("/{post-id}")
    public void removeLike(@PathVariable(name = "post-id") Integer id){
        postService.removeLike(id);
    }
}
