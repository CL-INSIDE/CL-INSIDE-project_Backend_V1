package com.example.sns.domain.post.presentaion;

import com.example.sns.domain.post.domain.dto.request.PostRequest;
import com.example.sns.domain.post.domain.dto.response.EachPostResponse;
import com.example.sns.domain.post.domain.dto.response.OtherPostResponse;
import com.example.sns.domain.post.domain.dto.response.PostResultResponse;
import com.example.sns.domain.post.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final CreatePostService createService;
    private final ModifyPostService modifyPostService;
    private final RemovePostService removePostService;
    private final SearchPostService searchPostService;
    private final OtherPostService getPostService;
    private final GetEachPostService getEachPostService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public void createPost(@RequestBody @Valid PostRequest postRequest){
        createService.execute(postRequest);
    }

    @PatchMapping("/{post-id}")
    public void modifyPost(@PathVariable(name = "post-id") Integer id, @RequestBody @Valid PostRequest postRequest){
        modifyPostService.execute(id, postRequest);
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

    @GetMapping("/other")
    public List<OtherPostResponse> getOtherPosts(){
        return getPostService.execute();
    }


    /*생성
    * 수정
    * 삭제
    * 검색
    * 좋아요 많은순 나열
    * 카테고리 띄우기*/
}
