package com.example.sns.domain.post.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from post_tbl order by like_tbl - hate_tbl dse", nativeQuery = true)
    List<Post> findAllLikeDes();

    @Query(value = "select * from post_tbl order by rand() limit 4", nativeQuery = true)
    List<Post> findRandomPost();

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    List<Post> findByMemberId(Integer id);
    Integer countByUserId(Integer id);
    Long countByTitleContaining(String keyword);

    @Query(value = "select * from tbl_post order by id desc limit 8", nativeQuery = true)
    List<Post> getOtherPosts();
}
