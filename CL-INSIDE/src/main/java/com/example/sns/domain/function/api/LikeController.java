package com.example.sns.domain.sympathy.api;

import com.example.sns.domain.post.service.AddLikeService;
import com.example.sns.domain.post.service.RemoveLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final AddLikeService addLikeService;
    private final RemoveLikeService removeLikeService;

    @PostMapping("/like/{post-id}")
    public void addLike(@PathVariable(name = "post-id") Integer id){
        addLikeService.addLike(id);
    }

    @DeleteMapping("/like/{post-id}")
    public void removeLike(@PathVariable(name = "post-id") Integer id){
        removeLikeService.removeLike(id);
    }
}
