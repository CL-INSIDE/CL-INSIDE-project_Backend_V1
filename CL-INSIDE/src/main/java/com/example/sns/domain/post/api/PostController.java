package com.example.sns.domain.post.api;

import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.payload.PostRequest;
import com.example.sns.domain.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public void createPost(@RequestBody PostRequest request){
        postService.createPost(request);
    }

    @PatchMapping("/modify/{post-id}")
    public void modifyPost(@PathVariable(name = "post-id") Integer id, @RequestBody PostRequest request){
        postService.modifyPost(id, request);
    }

    @DeleteMapping("/remove/{post-id}")
    public void removePost(@PathVariable(name = "post-id") Integer id){
        postService.removePost(id);
    }

    @GetMapping("/like/des")
    public List<Post> likeDes(){
        return postService.likeDes();
    }
}
