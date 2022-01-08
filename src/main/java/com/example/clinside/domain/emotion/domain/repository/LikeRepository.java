package com.example.clinside.domain.emotion.domain.repository;

import com.example.clinside.domain.emotion.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserIdAndPostId(Integer memberId, Integer postId);

    List<Like> findByUserId(Integer id);
}
