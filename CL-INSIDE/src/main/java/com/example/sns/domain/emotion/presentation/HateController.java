package com.example.sns.domain.emotion.presentation;

import com.example.sns.domain.emotion.service.AddHatePostService;
import com.example.sns.domain.emotion.service.RemoveHatePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HateController {

    private final AddHatePostService addHatePostService;
    private final RemoveHatePostService removeHatePostService;

    @PostMapping("/hate/{post-id}")
    public void addHate(@PathVariable(name = "post-id") Integer id){
        addHatePostService.execute(id);
    }

    @DeleteMapping("/hate/{post-id}")
    public void removeHate(@PathVariable(name = "post-id") Integer id){
        removeHatePostService.execute(id);
    }
}
