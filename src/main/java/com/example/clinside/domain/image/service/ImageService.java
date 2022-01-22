package com.example.clinside.domain.image.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.image.domain.Image;
import com.example.clinside.domain.image.domain.repository.ImageRepository;
import com.example.clinside.domain.post.domain.Post;
import com.example.clinside.domain.image.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Util s3Util;

    public void addImage(Post post, MultipartFile image) {

        String fileName = s3Util.upload(image);

        imageRepository.save(Image.builder()
                .imagePath(fileName)
                .imageUrl(s3Util.getFileUrl(fileName))
                .post(post)
                .build());
    }

    public void modifyImage(Post post, MultipartFile image){
        removeImage(post);
        addImage(post, image);
    }

    public void removeImage(Post post) {
        imageRepository.deleteByPost(post);
    }
}
