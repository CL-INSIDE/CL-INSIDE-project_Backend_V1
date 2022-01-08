package com.example.clinside.domain.emotion.presentation;

import com.example.clinside.domain.emotion.service.AddLikePostService;
import com.example.clinside.domain.emotion.service.RemoveLikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final AddLikePostService addLikePostService;
    private final RemoveLikePostService removeLikePostService;

    @PostMapping("/like/{post-id}")
    public void addLike(@PathVariable(name = "post-id") Integer id) {
        addLikePostService.execute(id);
    }

    @DeleteMapping("/like/{post-id}")
    public void removeLike(@PathVariable(name = "post-id") Integer id) {
        removeLikePostService.execute(id);
    }
}
