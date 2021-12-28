package com.example.sns.domain.emotion.domain.repository;

import com.example.sns.domain.emotion.domain.Hate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HateRepository extends JpaRepository<Hate, Integer> {
    Optional<Hate> findByUserIdAndPostId(Integer memberId, Integer postId);
}
