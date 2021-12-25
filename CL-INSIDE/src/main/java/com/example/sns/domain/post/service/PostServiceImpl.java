package com.example.sns.domain.post.service;

import com.example.sns.domain.hate.entity.Hate;
import com.example.sns.domain.hate.entity.HateRepository;
import com.example.sns.domain.image.entity.Image;
import com.example.sns.domain.image.entity.ImageRepository;
import com.example.sns.domain.image.service.ImageService;
import com.example.sns.domain.like.entity.Like;
import com.example.sns.domain.like.entity.LikeRepository;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.entity.PostRepository;
import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.post.payload.request.PostRequest;
import com.example.sns.domain.post.payload.response.OtherPostResponse;
import com.example.sns.domain.post.payload.response.PostEachResponse;
import com.example.sns.domain.post.payload.response.PostResponse;
import com.example.sns.domain.post.payload.response.PostResultResponse;
import com.example.sns.global.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final LikeRepository likeRepository;
    private final HateRepository hateRepository;
    private final ImageRepository imageRepository;

    @Override
    public void createPost(PostRequest request) {
        try{
            Post post = postRepository.save(
                    Post.builder()
                            .title(request.getTitle())
                            .content(request.getContent())
                            .category(request.getCategory())
                            .user(UserFacade.getUser())
                            .likeCounts(0)
                            .hateCounts(0)
                            .build());

            imageService.uploadFile(request.getImage(), post);
        } catch (Exception e){
            throw AuthenticationNotFoundException.EXCEPTION;
        }
    }

    @Override
    public void modifyPost(Integer id, PostRequest request) {
        Post post = postRepository.findById(id)
                .map(newPost -> newPost.updatePost(
                        request.getTitle(),
                        request.getContent(),
                        request.getCategory()
                ))
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
        imageService.updateFile(request.getImage(), post);
    }

    @Override
    public void removePost(Integer id) {
        if(postRepository.findById(id).isEmpty())
            throw PostNotFoundException.EXCEPTION;

        imageService.removeFile(id);
    }

    @Override
    public void addLike(Integer id){
        if(likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent())
            throw LikeAlreadyExistsException.EXCEPTION;

        likeRepository.save(
                Like.builder()
                        .post(postRepository.findById(id).orElseThrow(() -> PostNotFoundException.EXCEPTION))
                        .build());

        postRepository.findById(id)
                .map(post -> {
                    post.getUser().addLikeCounts();
                    post.addPostLikeCounts();
                    return post;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Override
    public void removeLike(Integer id) {
        likeRepository.delete(
                likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id)
                        .orElseThrow(() -> LikeNotFoundException.EXCEPTION)
        );
        postRepository.findById(id)
                .map(post -> post.removeLikeCounts())
                .map(post -> post.getUser().removeLikeCounts())
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Override
    public void addHate(Integer id) {
        if (hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent())
            throw HateAlreadyExistsException.EXCEPTION;

        hateRepository.save(
                Hate.builder()
                        .post(postRepository.findById(id).orElseThrow(() -> HateNotFoundException.EXCEPTION))
                        .build());

        postRepository.findById(id)
                .map(post -> {
                    post.getUser().addHateCounts();
                    post.addPostHateCounts();
                    return post;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Override
    public void removeHate(Integer id) {
        likeRepository.delete(
                likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id)
                        .orElseThrow(() -> HateNotFoundException.EXCEPTION)
        );
        postRepository.findById(id)
                .map(post -> post.removeHateCounts())
                .map(post -> post.getUser().removeHateCounts())
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Override
    public List<Post> likeDes() {
        return postRepository.findAllLikeDes();
    }

    @Override
    public List<Post> randomPost() {
        return postRepository.findRandomPost();
    }

    @Override
    public PostResultResponse searchPost(String keyword, Pageable pageable) {

        List<PostResponse> posts = postRepository.findByTitleContaining(keyword,pageable)
                .stream()
                .map(post -> {
                    PostResponse response = PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .category(post.getCategory())
                            .createdDate(post.getCreatedDate())
                            .updatedDate(post.getUpdatedDate())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl).orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .isLiked(checkLiked(post.getId()))
                            .isHated(checkHate(post.getId()))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());

        return new PostResultResponse(UserFacade.getUserId(), posts);
    }

    @Override
    public PostEachResponse getEachPost(Integer id) {
        return postRepository.findById(id)
                .map(post -> {
                    PostEachResponse response = PostEachResponse.builder()
                            .title(post.getTitle())
                            .content(post.getContent())
                            .isLiked(checkLiked(id))
                            .createdDate(post.getCreatedDate())
                            .updatedDate(post.getUpdatedDate())
                            .image(imageRepository.findByPostId(post.getId())
                                    .map(Image::getImageUrl).orElseThrow(() -> ImageNotFoundException.EXCEPTION))
                            .getLike(post.getLikeCounts())
                            .getHate(post.getHateCounts())
                            .userInfo(
                                    PostEachResponse.UserInfo.builder()
                                            .userId(post.getUser().getUid())
                                            .name(post.getUser().getName())
                                            .postsCounts(postRepository.countByUserId(post.getUser().getUid()))
                                            .everyLikeCounts(post.getUser().getEveryLikeCounts())
                                            .everyHateCounts(post.getUser().getEveryHateCounts())
                                            .build())
                            .isMine(checkMine(id))
                            .build();
                    return response;
                })
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Override
    public List<OtherPostResponse> getOtherPost() {
        return postRepository.getOtherPosts()
                .stream()
                .map(post -> {
                    OtherPostResponse response = OtherPostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .image(post.getImage().getImageUrl())
                            .isLiked(checkLiked(post.getId()))
                            .isHate(checkHate(post.getId()))
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }

    private boolean checkLiked(Integer id) {
        return likeRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkHate(Integer id){
        return hateRepository.findByUserIdAndPostId(UserFacade.getUserId(), id).isPresent();
    }

    private boolean checkMine(Integer id) {
        Integer userId = UserFacade.getUserId();

        return likeRepository.findById(id)
                .filter(u -> userId != null)
                .map(post -> post.getUser().getId().equals(userId))
                .isPresent();
    }
}
