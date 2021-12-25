package com.example.sns.domain.post.service;

import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.payload.request.PostRequest;
import com.example.sns.domain.post.payload.response.OtherPostResponse;
import com.example.sns.domain.post.payload.response.PostEachResponse;
import com.example.sns.domain.post.payload.response.PostResponse;
import com.example.sns.domain.post.payload.response.PostResultResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void createPost(PostRequest request);
    void modifyPost(Integer id, PostRequest request);
    void removePost(Integer id);
    void addLike(Integer id);
    void removeLike(Integer id);
    void addHate(Integer id);
    void removeHate(Integer id);
    List<Post> likeDes();
    List<Post> randomPost();
    PostResultResponse searchPost(String keyword, Pageable pageable);
    PostEachResponse getEachPost(Integer id);
    List<OtherPostResponse> getOtherPost();
}
