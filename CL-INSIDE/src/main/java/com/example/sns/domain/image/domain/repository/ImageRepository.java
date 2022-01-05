package com.example.sns.domain.image.domain.repository;

import com.example.sns.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByPostId(Integer id);
}
