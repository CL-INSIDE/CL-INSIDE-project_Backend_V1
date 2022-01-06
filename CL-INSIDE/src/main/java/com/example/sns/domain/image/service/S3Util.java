package com.example.sns.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.sns.global.exception.S3ConnectionFailedException;
import com.example.sns.global.security.config.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3Client amazonS3Client;
    private final S3Config s3Config;

    public String upload(MultipartFile image) {
        String fileName = "clinside/" + UUID.randomUUID() + image.getOriginalFilename();

        try {
            amazonS3Client.putObject(new PutObjectRequest(s3Config.getBucket(), fileName, image.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw S3ConnectionFailedException.EXCEPTION;
        }
        return fileName;
    }

    public String getFileUrl(String fileName) {
        return amazonS3Client.getUrl(s3Config.getBucket(), fileName).toString();
    }

    public void removeFile(String imagePath) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(s3Config.getBucket(), imagePath));
    }
}
