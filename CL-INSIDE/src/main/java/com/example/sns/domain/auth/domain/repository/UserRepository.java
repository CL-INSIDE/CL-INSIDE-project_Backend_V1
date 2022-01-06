package com.example.sns.domain.auth.domain.repository;

import com.example.sns.domain.auth.domain.User;
import com.example.sns.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
