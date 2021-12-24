package com.example.sns.domain.image.service;

import com.example.sns.domain.post.entity.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadFile(MultipartFile image, Post post);
    String updateFile(MultipartFile image, Post post);
    ResponseEntity<byte[]> downloadFile(Integer postId);
    void removeFile(Integer id);
}
