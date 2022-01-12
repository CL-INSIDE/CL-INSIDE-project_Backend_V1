package com.example.clinside.domain.post.domain.repository;

import com.example.clinside.domain.post.domain.Post;
import com.example.clinside.domain.post.domain.types.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByTitleContaining(String keyword, Pageable pageable);

    List<Post> findPostByUserId(Integer id);

    @Query(value = "select * from tbl_post order by like_counts desc limit 20", nativeQuery = true)
    List<Post> findManyLikePosts();

    @Query(value = "select * from tbl_post order by rand() limit 3", nativeQuery = true)
    List<Post> findRandomPosts();

    List<Post> findByCategoryContaining(Category category, Pageable pageable);
}
