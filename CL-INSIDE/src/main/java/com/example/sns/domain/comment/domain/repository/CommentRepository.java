package com.example.sns.domain.comment.domain.repository;

import com.example.sns.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findByPostId(Integer id);
}
