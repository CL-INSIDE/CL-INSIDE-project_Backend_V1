package com.example.clinside.domain.emotion.domain.repository;

import com.example.clinside.domain.emotion.domain.Hate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HateRepository extends JpaRepository<Hate, Integer> {
    Optional<Hate> findByUserIdAndPostId(Integer memberId, Integer postId);
}
