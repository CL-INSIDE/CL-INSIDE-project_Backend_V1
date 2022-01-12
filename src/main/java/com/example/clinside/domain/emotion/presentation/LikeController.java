package com.example.clinside.domain.emotion.presentation;

import com.example.clinside.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final PostService postService;

    @PostMapping("/{post-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLike(@PathVariable(name = "post-id") Integer id) {
        postService.addLike(id);
    }

    @DeleteMapping("/{post-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable(name = "post-id") Integer id) {
        postService.removeLike(id);
    }
}
