package com.example.sns.domain.image.domain.repository;

import com.example.sns.domain.image.domain.Image;
import com.example.sns.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByImagePath(String imagePath);
    Optional<Image> findByPostId(Integer id);
    Optional<Image> findByUserId(Integer id);
    Optional<Image> findImageByUserId(Integer id);
    void deleteByPost(Post post);
}
