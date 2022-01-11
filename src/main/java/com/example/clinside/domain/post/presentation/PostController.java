package com.example.clinside.domain.post.presentation;

import com.example.clinside.domain.post.domain.types.Category;
import com.example.clinside.domain.post.presentation.dto.request.PostRequest;
import com.example.clinside.domain.post.presentation.dto.response.*;
import com.example.clinside.domain.post.service.*;
import com.example.clinside.domain.user.service.GetUserLikePosts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final CreatePostService createService;
    private final ModifyPostService modifyPostService;
    private final RemovePostService removePostService;
    private final SearchPostService searchPostService;
    private final EachPostService getEachPostService;
    private final ManyLikePostsService manyLikePostsService;
    private final RandomPostsSrevice randomPostsSrevice;
    private final CategoryListService categoryListService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(
            @RequestPart(value = "file", required = false) MultipartFile image,
            @RequestPart(value = "postReq") PostRequest postRequest) {
        createService.execute(postRequest, image);
    }

    @PatchMapping("/{post-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyPost(@PathVariable(name = "post-id") Integer id,
                           @RequestPart(value = "postReq") PostRequest postRequest,
                           @RequestPart(value = "file", required = false) MultipartFile image) {
        modifyPostService.execute(id, postRequest, image);
    }

    @DeleteMapping("/{post-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable(name = "post-id") Integer id) {
        removePostService.execute(id);
    }

    @GetMapping("/search")
    public PostResultResponse searchPost(@RequestParam(name = "keyword") String keyword, Pageable pageable) {
        return searchPostService.execute(keyword, pageable);
    }

    @GetMapping("/{post-id}")
    public EachPostResponse eachPost(@PathVariable(name = "post-id") Integer id) {
        return getEachPostService.execute(id);
    }

    @GetMapping("/many/like")
    public List<ManyLikePostsResponse> manyLikePosts(){
        return manyLikePostsService.execute();
    }

    @GetMapping("/random")
    public List<RandomPostsResponse> randomPosts(){
        return randomPostsSrevice.execute();
    }

    @GetMapping("/category")
    public List<PostResponse> categoryListPost(@RequestParam(name = "category-name") Category category, Pageable pageable) {
        return categoryListService.execute(category, pageable);
    }



    /*생성
     * 수정
     * 삭제
     * 검색
     * 좋아요 많은순 나열
     * 카테고리 띄우기*/
}
