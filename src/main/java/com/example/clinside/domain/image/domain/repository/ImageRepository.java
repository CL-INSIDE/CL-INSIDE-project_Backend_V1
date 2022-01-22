package com.example.clinside.domain.image.domain.repository;

import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByPostId(Integer id);
    void deleteByPost(Post post);
}
