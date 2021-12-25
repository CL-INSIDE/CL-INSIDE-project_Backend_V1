package com.example.sns.domain.post.api;

import com.example.sns.domain.image.service.ImageService;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.payload.request.PostRequest;
import com.example.sns.domain.post.payload.response.OtherPostResponse;
import com.example.sns.domain.post.payload.response.PostEachResponse;
import com.example.sns.domain.post.payload.response.PostResultResponse;
import com.example.sns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ImageService imageService;

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

    @GetMapping("/random/post")
    public List<Post> randomPost(){
        return postService.randomPost();
    }

    @GetMapping("/search")
    public PostResultResponse searchPost(@RequestParam(value = "keyword") String keyword, Pageable pageable){
        return postService.searchPost(keyword, pageable);
    }

    @GetMapping("/download/{post-id}")
    public ResponseEntity<byte[]>downloadFile(@PathVariable(name = "post-id") Integer id){
        return imageService.downloadFile(id);
    }

    @GetMapping("/get/{post-id}")
    public PostEachResponse getEachPost(@PathVariable(name = "post-id") Integer id) {
        return postService.getEachPost(id);
    }

    @GetMapping("/other")
    public List<OtherPostResponse> getOtherPost() {
        return postService.getOtherPost();
    }
}
