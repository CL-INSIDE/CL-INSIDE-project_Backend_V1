package com.example.sns.domain.image.service;

import com.example.sns.domain.image.domain.Image;
import com.example.sns.domain.image.domain.repository.ImageRepository;
import com.example.sns.global.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadFileService {

    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    public ResponseEntity<byte[]> downloadFile(Integer postId) {
        String storedFileName = imageRepository.findByPostId(postId)
                .map(Image::getImagePath)
                .orElseThrow(() -> ImageNotFoundException.EXCEPTION);

        return s3Service.download(storedFileName);
    }
}
