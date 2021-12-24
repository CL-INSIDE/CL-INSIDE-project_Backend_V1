package com.example.sns.domain.post.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from post_tbl order by like_tbl - hate_tbl dse", nativeQuery = true)
    List<Post> findAllLikeDes();
}
