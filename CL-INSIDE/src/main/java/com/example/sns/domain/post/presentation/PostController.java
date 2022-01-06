package com.example.sns.domain.post.presentation;

import com.example.sns.domain.post.presentation.dto.request.PostRequest;
import com.example.sns.domain.post.presentation.dto.response.EachPostResponse;
import com.example.sns.domain.post.presentation.dto.response.PostResultResponse;
import com.example.sns.domain.post.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final CreatePostService createService;
    private final ModifyPostService modifyPostService;
    private final RemovePostService removePostService;
    private final SearchPostService searchPostService;
    private final EachPostService getEachPostService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createPost(
            @RequestPart(value = "file", required = false) MultipartFile image,
            @RequestPart(value = "postReq") PostRequest postRequest){
        createService.execute(postRequest, image);
    }

    @PatchMapping("/{post-id}")
    public void modifyPost(@PathVariable(name = "post-id") Integer id,
                           @RequestPart(value = "postReq") PostRequest postRequest,
                           @RequestPart(value = "file", required = false) MultipartFile image){
        modifyPostService.execute(id, postRequest, image);
    }

    @DeleteMapping("/{post-id}")
    public void removePost(@PathVariable(name = "post-id") Integer id){
        removePostService.execute(id);
    }

    @GetMapping("/search")
    public PostResultResponse searchPost(@RequestParam(name = "keyword") String keyword, Pageable pageable){
        return searchPostService.execute(keyword, pageable);
    }

    @GetMapping("/{post-id}")
    public EachPostResponse eachPost(@PathVariable(name = "post-id") Integer id){
        return getEachPostService.execute(id);
    }

    /*생성
    * 수정
    * 삭제
    * 검색
    * 좋아요 많은순 나열
    * 카테고리 띄우기*/
}
