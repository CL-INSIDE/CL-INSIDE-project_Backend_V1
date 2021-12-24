package com.example.sns.domain.hate.entity;

import com.example.sns.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HateRepository extends JpaRepository<Hate, Integer> {
    Optional<Like> findByUserIdAndPostId(Integer userId, Integer postId);
}
