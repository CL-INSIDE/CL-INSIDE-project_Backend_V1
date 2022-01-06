package com.example.sns.domain.image.service;

import com.example.sns.domain.image.domain.Image;
import com.example.sns.domain.image.domain.repository.ImageRepository;
import com.example.sns.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;


    public String uploadFile(MultipartFile image, Post post){
        String imagePath = s3Service.upload(image, "static");

        imageRepository.save(Image.builder()
                .imagePath(imagePath)
                .imageUrl(s3Service.getFileUrl(imagePath))
                .post(post)
                .build());
        return imagePath;
    }
}
