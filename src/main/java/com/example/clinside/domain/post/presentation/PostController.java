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

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "postReq") PostRequest postRequest) {
        postService.createPost(postRequest, file);
    }

    @PatchMapping("/{post-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyPost(@PathVariable(name = "post-id") Integer id,
                           @RequestPart(value = "postReq") PostRequest postRequest,
                           @RequestPart(value = "file", required = false) MultipartFile file) {
        postService.modifyPost(postRequest, file, id);
    }

    @DeleteMapping("/{post-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable(name = "post-id") Integer id) {
        postService.removePost(id);
    }

    @GetMapping("/{post-id}")
    public EachPostResponse eachPost(@PathVariable(name = "post-id") Integer id) {
        return postService.eachPost(id);
    }

    @GetMapping("/search")
    public PostResultResponse searchPost(@RequestParam(name = "keyword") String keyword, Pageable pageable) {
        return postService.searchPost(keyword, pageable);
    }

    @GetMapping("/many/like")
    public List<ManyLikePostsResponse> manyLikePosts(){
        return postService.manyLikePosts();
    }

    @GetMapping("/random")
    public List<RandomPostsResponse> randomPosts(){
        return postService.randomPosts();
    }

    @GetMapping("/category")
    public List<PostResponse> categoryListPost(@RequestParam(name = "category-name") Category category, Pageable pageable) {
        return postService.categoryListPost(category, pageable);
    }

    @GetMapping("/category/sport")
    public List<PostResponse> categorySportPosts(){
        return postService.categorySportPosts();
    }

    @GetMapping("/category/trip")
    public List<PostResponse> categoryTripPosts(){
        return postService.categoryTripPosts();
    }

    @GetMapping("/category/it")
    public List<PostResponse> categoryItPosts(){
        return postService.categoryItPosts();
    }

    @GetMapping("/category/board-cast")
    public List<PostResponse> categoryBoardCastPosts(){
        return postService.categoryBoardCastPosts();
    }

    @GetMapping("/category/hobby")
    public List<PostResponse> categoryHobbyPosts(){
        return postService.categoryHobbyPosts();
    }

    @GetMapping("/category/game")
    public List<PostResponse> categoryGamePosts(){
        return postService.categoryGamePosts();
    }







    /*생성
     * 수정
     * 삭제
     * 검색
     * 좋아요 많은순 나열
     * 카테고리 띄우기*/
}
