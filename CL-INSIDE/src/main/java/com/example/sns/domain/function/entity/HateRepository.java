package com.example.sns.domain.sympathy.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HateRepository extends JpaRepository<Hate, Integer> {
    Optional<Hate> findByUserIdAndPostId(Integer userId, Integer postId);
}
