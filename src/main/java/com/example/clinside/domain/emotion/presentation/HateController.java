package com.example.clinside.domain.emotion.presentation;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hate")
public class HateController {

    private final PostService postService;

    @PostMapping("/{post-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHate(@PathVariable(name = "post-id") Integer id) {
        postService.addHate(id);
    }

    @DeleteMapping("/{post-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeHate(@PathVariable(name = "post-id") Integer id) {
        postService.removeHate(id);
    }
}
