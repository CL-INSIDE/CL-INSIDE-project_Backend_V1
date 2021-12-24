package com.example.sns.domain.post.service;

import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.payload.PostRequest;

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
}
