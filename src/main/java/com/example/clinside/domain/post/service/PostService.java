package com.example.clinside.domain.post.service;

import com.example.clinside.domain.auth.domain.User;
import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.comment.domain.repository.CommentRepository;
import com.example.clinside.domain.emotion.domain.Hate;
import com.example.clinside.domain.emotion.domain.Like;
import com.example.clinside.domain.emotion.domain.repository.HateRepository;
import com.example.clinside.domain.emotion.domain.repository.LikeRepository;
import com.example.clinside.domain.emotion.exception.LikeAlreadyExistsException;
import com.example.clinside.domain.emotion.exception.LikeNotFoundException;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.image.service.ImageService;
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

        imageService.addImage(file, post);
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
            imageService.modifyImage(file, post);
        }
    }

    @Transactional
    public void removePost(Integer id) {
        if (postRepository.findById(id).isEmpty())
            throw PostNotFoundException.EXCEPTION;

        imageService.removeImage(id);
        postRepository.deleteById(id);
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
                        .comments(commentRepository.findByPostId(id))
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

    public List<PostResponse> categorySportPosts(){
        return postRepository.findPostByCategorySport()
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
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

    public List<PostResponse> categoryItPosts(){
        return postRepository.findPostByCategoryIt()
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
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

    public List<PostResponse> categoryTripPosts(){
        return postRepository.findPostByCategoryTrip()
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
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

    public List<PostResponse> categoryBoardCastPosts(){
        return postRepository.findPostByCategoryBoardCast()
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
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

    public List<PostResponse> categoryHobbyPosts(){
        return postRepository.findPostByCategoryHobby()
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
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

    public List<PostResponse> categoryGamePosts(){
        return postRepository.findPostByCategoryGame()
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .title(post.getTitle())
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
}
