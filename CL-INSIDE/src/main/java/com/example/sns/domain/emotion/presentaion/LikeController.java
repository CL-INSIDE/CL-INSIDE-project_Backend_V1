package com.example.sns.domain.emotion.presentaion;

import com.example.sns.domain.emotion.service.AddLikePostService;
import com.example.sns.domain.emotion.service.RemoveLikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final AddLikePostService addLikePostService;
    private final RemoveLikePostService removeLikePostService;


    @PostMapping("/{post-id}")
    public void addLike(@PathVariable(name = "post-id") Integer id){
        addLikePostService.execute(id);
    }

    @DeleteMapping("/{post-id}")
    public void removeLike(@PathVariable(name = "post-id") Integer id){
        removeLikePostService.execute(id);
    }
}
