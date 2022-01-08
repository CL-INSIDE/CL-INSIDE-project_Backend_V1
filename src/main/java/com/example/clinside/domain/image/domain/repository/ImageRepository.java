package com.example.clinside.domain.image.domain.repository;

import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByImagePath(String imagePath);

    Optional<Image> findByPostId(Integer id);

    Optional<Image> findByUserId(Integer id);

    Optional<Image> findImageByUserId(Integer id);

    void deleteByPost(Post post);
}
