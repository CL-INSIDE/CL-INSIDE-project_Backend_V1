package com.example.sns.domain.image.service;

import com.example.sns.domain.image.domain.repository.ImageRepository;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UpdateFileService {

    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    public String updateFile(MultipartFile image, Post post){
        String imagePath = s3Service.upload(image, "static");
        Integer postId = post.getId();

        removeFile(postId);
        imageRepository.findByPostId(postId)
                .map(newImage -> newImage.updateImage(imagePath, s3Service.getFileUrl(imagePath)))
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION);

        return imagePath;
    }

    public void removeFile(Integer postId) {
        s3Service.removeFile(imageRepository.findByPostId(postId)
                .map(image -> image.getImagePath())
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION));
    }
}
