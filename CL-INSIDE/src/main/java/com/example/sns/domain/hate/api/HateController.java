package com.example.sns.domain.hate.api;

import com.example.sns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HateController {
    private final PostService postService;

    @PostMapping("/{post-id}")
    public void addHate(@PathVariable(name = "post-id") Integer id){
        postService.addHate(id);
    }

    @DeleteMapping("/{post-id}")
    public void removeHate(@PathVariable(name = "post-id") Integer id){
        postService.removeHate(id);
    }
}
