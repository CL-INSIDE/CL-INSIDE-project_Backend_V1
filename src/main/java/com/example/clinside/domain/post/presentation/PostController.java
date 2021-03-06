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

    @GetMapping("/category/sport")
    public List<CategoryPostResponse> categorySportPosts(){
        return postService.categorySportPosts();
    }

    @GetMapping("/category/trip")
    public List<CategoryPostResponse> categoryTripPosts(){
        return postService.categoryTripPosts();
    }

    @GetMapping("/category/it")
    public List<CategoryPostResponse> categoryItPosts(){
        return postService.categoryItPosts();
    }

    @GetMapping("/category/board-cast")
    public List<CategoryPostResponse> categoryBoardCastPosts(){
        return postService.categoryBoardCastPosts();
    }

    @GetMapping("/category/hobby")
    public List<CategoryPostResponse> categoryHobbyPosts(){
        return postService.categoryHobbyPosts();
    }

    @GetMapping("/category/game")
    public List<CategoryPostResponse> categoryGamePosts(){
        return postService.categoryGamePosts();
    }







    /*??????
     * ??????
     * ??????
     * ??????
     * ????????? ????????? ??????
     * ???????????? ?????????*/
}
