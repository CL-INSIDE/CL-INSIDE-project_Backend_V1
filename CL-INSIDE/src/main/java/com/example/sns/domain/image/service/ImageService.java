package com.example.sns.domain.image.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.image.domain.Image;
import com.example.sns.domain.image.domain.repository.ImageRepository;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Util s3Util;

    public void addImage(MultipartFile image, Post post) {

            String fileName = s3Util.upload(image);

            imageRepository.save(Image.builder()
                    .user(UserFacade.getUser())
                    .imagePath(fileName)
                    .imageUrl(s3Util.getFileUrl(fileName))
                    .post(post)
                    .build());
    }

    public String modifyImage(MultipartFile image, Post post){

        String fileName = s3Util.upload(image);
        Integer postId = post.getId();

        removeImage(postId);
        imageRepository.findByPostId(postId)
                .map(newImage -> newImage.updateImage(fileName, s3Util.getFileUrl(fileName)))
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION);

        return fileName;
    }

    public void removeImage(Integer postId){
        s3Util.removeFile(imageRepository.findByPostId(postId)
                .map(image -> image.getImagePath())
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION));
    }
}
