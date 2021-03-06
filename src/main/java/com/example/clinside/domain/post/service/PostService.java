package com.example.clinside.domain.post.service;

import com.example.clinside.domain.auth.domain.User;
import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.comment.domain.repository.CommentRepository;
import com.example.clinside.domain.comment.presentation.dto.response.CommentResponse;
import com.example.clinside.domain.emotion.domain.Hate;
import com.example.clinside.domain.emotion.domain.Like;
import com.example.clinside.domain.emotion.domain.repository.HateRepository;
import com.example.clinside.domain.emotion.domain.repository.LikeRepository;
import com.example.clinside.domain.emotion.exception.LikeAlreadyExistsException;
import com.example.clinside.domain.emotion.exception.LikeNotFoundException;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.image.service.ImageService;
import com.example.clinside.domain.image.service.S3Util;
import com.example.clinside.domain.post.domain.Post;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import com.example.clinside.domain.post.domain.types.Category;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.domain.post.presentation.dto.request.PostRequest;
import com.example.clinside.domain.post.presentation.dto.response.*;
import com.example.clinside.global.error.exception.SnsException;
import com.example.clinside.domain.image.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final HateRepository hateRepository;
    private final S3Util s3Util;

    @Transactional
    public void createPost(PostRequest request, MultipartFile file) {
        Post post = postRepository.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(UserFacade.getUser())
                .category(request.getCategory())
                .likeCounts(0)
                .hateCounts(0)
                .build());

        imageService.addImage(post, file);
    }

    @Transactional
    public void modifyPost(PostRequest request, MultipartFile file, Integer id) {
        Post post = postRepository.findById(id)
                .map(newPost -> newPost.updatePost(
                        request.getTitle(),
                        request.getContent(),
                        request.getCategory()
                ))
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);

        if (file != null) {
            imageService.modifyImage(post, file);
        }
    }

    @Transactional
    public void removePost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);

        postRepository.delete(post);
        imageService.removeImage(post);

        s3Util.removeFile(imageRepository.findByPostId(id)
                .map(image -> image.getImagePath())
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION));

    }

    @Transactional
    public EachPostResponse eachPost(Integer id) {
        return postRepository.findById(id)
            .map(post -> {
                EachPostResponse response = EachPostResponse.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdDate(post.getCreatedDate())
                        .updatedDate(post.getUpdatedDate())
                        .isLike(checkLike(id))
                        .isHate(checkHate(id))
                        .getLikes(post.getLikeCounts())
                        .category(post.getCategory())
                        .comments(commentRepository.findByPostId(id)
                                .stream()
                                .map(comment -> {
                                    CommentResponse commentResponse = CommentResponse.builder()
                                            .commentId(comment.getId())
                                            .userId(comment.getUser().getId())
                                            .content(comment.getContent())
                                            .build();
                                    return commentResponse;
                                }).collect(Collectors.toList()))
                        //.isMine(checkMine(id))
                        .image(imageRepository.findByPostId(post.getId())
                                .map(Image::getImageUrl)
                                .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                        .build();
                return response;
            })
            .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Transactional
    public PostResultResponse searchPost(String keyword, Pageable pageable) {
        List<PostResponse> posts = postRepository.findByTitleContaining(keyword, pageable)
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .id(post.getId())
                            .userId(post.getUser().getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());

        return new PostResultResponse(posts);
    }

    @Transactional
    public List<ManyLikePostsResponse> manyLikePosts() {
        return postRepository.findManyLikePosts()
                .stream()
                .map(post -> {
                    ManyLikePostsResponse manyLikePostsResponse = ManyLikePostsResponse.builder()
                            .postId(post.getId())
                            .userId(post.getUser().getId())
                            .title(post.getTitle())
                            .likeCounts(post.getLikeCounts())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return manyLikePostsResponse;
                }).collect(Collectors.toList());
    }

    @Transactional
    public List<RandomPostsResponse> randomPosts() {
        return postRepository.findRandomPosts()
                .stream()
                .map(post -> {
                    RandomPostsResponse response = RandomPostsResponse.builder()
                            .postId(post.getId())
                            .userId(post.getUser().getId())
                            .title(post.getTitle())
                            .category(post.getCategory())
                            .likeCounts(post.getLikeCounts())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                }).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> categoryListPost(Category category, Pageable pageable) {
        return postRepository.findByCategoryContaining(category, pageable)
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .category(post.getCategory())
                            .userId(post.getUser().getId())
                            .isLike(post.isLike())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void addLike(Integer id) {

        User user = UserFacade.getUser();

        Post post = postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION);

        try {
            likeRepository.save(
                    Like.builder()
                            .user(user)
                            .post(post)
                            .build()
            );
            post.addPostLikeCounts();
            post.getUser().addLikeCounts();

        } catch (SnsException e) {
            throw LikeAlreadyExistsException.EXCEPTION;
        }
    }

    @Transactional
    public void removeLike(Integer id){
        Post post = postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION);

        likeRepository.delete(
                likeRepository
                        .findByUserIdAndPostId(UserFacade.getUserId(), id)
                        .orElseThrow(() -> LikeNotFoundException.EXCEPTION)
        );
        post.removeLikeCounts();
        post.getUser().removeLikeCounts();
    }

    @Transactional
    public void addHate(Integer id) {

        User user = UserFacade.getUser();

        Post post = postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION);

        try {
            hateRepository.save(
                    Hate.builder()
                            .user(user)
                            .post(post)
                            .build()
            );
            post.addPostHateCounts();
            post.getUser().addHateCounts();

        } catch (SnsException e) {
            throw LikeAlreadyExistsException.EXCEPTION;
        }
    }

    @Transactional
    public void removeHate(Integer id){
        Post post = postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION);

        likeRepository.delete(
                likeRepository
                        .findByUserIdAndPostId(UserFacade.getUserId(), id)
                        .orElseThrow(() -> LikeNotFoundException.EXCEPTION)
        );
        post.removeHateCounts();
        post.getUser().removeHateCounts();
    }

    private boolean checkLike(Integer id) {
        return likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkHate(Integer id) {
        return hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }
/*
    private boolean checkMine(Integer id) {
        Integer userId = UserFacade.getUserId();

        return postRepository.findById(id)
                .filter(u -> userId != null)
                .map(post -> post.getUser().getId().equals(userId))
                .isPresent();
    }*/

    public List<CategoryPostResponse> categorySportPosts(){
        return postRepository.findPostByCategorySport()
                .stream()
                .map(post -> {
                    CategoryPostResponse response = CategoryPostResponse.builder()
                            .title(post.getTitle())
                            .userId(post.getUser().getId())
                            .id(post.getId())
                            .name(post.getUser().getName())
                            .likeCounts(post.getLikeCounts())
                            .hateCounts(post.getHateCounts())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<CategoryPostResponse> categoryItPosts(){
        return postRepository.findPostByCategoryIt()
                .stream()
                .map(post -> {
                    CategoryPostResponse response = CategoryPostResponse.builder()
                            .title(post.getTitle())
                            .userId(post.getUser().getId())
                            .name(post.getUser().getName())
                            .likeCounts(post.getLikeCounts())
                            .hateCounts(post.getHateCounts())
                            .id(post.getId())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<CategoryPostResponse> categoryTripPosts(){
        return postRepository.findPostByCategoryTrip()
                .stream()
                .map(post -> {
                    CategoryPostResponse response = CategoryPostResponse.builder()
                            .title(post.getTitle())
                            .userId(post.getUser().getId())
                            .id(post.getId())
                            .name(post.getUser().getName())
                            .likeCounts(post.getLikeCounts())
                            .hateCounts(post.getHateCounts())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<CategoryPostResponse> categoryBoardCastPosts(){
        return postRepository.findPostByCategoryBoardCast()
                .stream()
                .map(post -> {
                    CategoryPostResponse response = CategoryPostResponse.builder()
                            .title(post.getTitle())
                            .userId(post.getUser().getId())
                            .name(post.getUser().getName())
                            .likeCounts(post.getLikeCounts())
                            .hateCounts(post.getHateCounts())
                            .id(post.getId())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<CategoryPostResponse> categoryHobbyPosts(){
        return postRepository.findPostByCategoryHobby()
                .stream()
                .map(post -> {
                    CategoryPostResponse response = CategoryPostResponse.builder()
                            .title(post.getTitle())
                            .name(post.getUser().getName())
                            .likeCounts(post.getLikeCounts())
                            .hateCounts(post.getHateCounts())
                            .userId(post.getUser().getId())
                            .id(post.getId())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<CategoryPostResponse> categoryGamePosts(){
        return postRepository.findPostByCategoryGame()
                .stream()
                .map(post -> {
                    CategoryPostResponse response = CategoryPostResponse.builder()
                            .title(post.getTitle())
                            .userId(post.getUser().getId())
                            .id(post.getId())
                            .name(post.getUser().getName())
                            .likeCounts(post.getLikeCounts())
                            .hateCounts(post.getHateCounts())
                            .category(post.getCategory())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl)
                                    .orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
