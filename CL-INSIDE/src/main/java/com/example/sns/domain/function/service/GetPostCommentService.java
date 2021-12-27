package com.example.sns.domain.function.service;

import com.example.sns.domain.function.entity.Comment;
import com.example.sns.domain.function.entity.CommentRepository;
import com.example.sns.domain.function.entity.HotPost;
import com.example.sns.domain.function.entity.LikeRepository;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.domain.post.entity.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<Comment> execute(Integer id){
        Post post = postRepository.findById(id).get();
        return commentRepository.findCommentsByPost();
    }
}
