package com.example.sns.domain.post.domain.repository;

import com.example.sns.domain.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByTitleContaining(String keyword, Pageable pageable);
    List<Post> findPostByUserId(Integer id);
}
