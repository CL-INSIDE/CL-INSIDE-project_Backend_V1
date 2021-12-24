package com.example.sns.domain.image.service;

import com.example.sns.domain.image.entity.Image;
import com.example.sns.domain.image.entity.ImageRepository;
import com.example.sns.domain.post.entity.Post;
import com.example.sns.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    @Override
    public String uploadFile(MultipartFile image, Post post) {
        String imagePath = s3Service.upload(image, "static");

        imageRepository.save(Image.builder()
                        .imagePath(imagePath)
                        .imageUrl(s3Service.getFileUrl(imagePath))
                        .post(post)
                        .build());
                return imagePath;
    }

    @Override
    public String updateFile(MultipartFile image, Post post) {
        String imagePath = s3Service.upload(image, "static");
        Integer postId = post.getId();

        removeFile(postId);
        imageRepository.findByPostId(postId)
                .map(newImage -> newImage.updateImage(imagePath, s3Service.getFileUrl(imagePath)))
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION);

        return imagePath;
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Integer postId) {
        String storedFileName = imageRepository.findByPostId(postId)
                .map(Image::getImagePath)
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION);

        return s3Service.download(storedFileName);
    }

    @Override
    public void removeFile(Integer id) {
        s3Service.removeFile(imageRepository.findByPostId(id)
                .map(image -> image.getImagePath())
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION));
    }
}
