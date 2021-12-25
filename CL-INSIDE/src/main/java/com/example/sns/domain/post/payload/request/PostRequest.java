package com.example.sns.domain.post.payload.request;

import com.example.sns.domain.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PostRequest {

    private String title;

    private String content;

    private Category category;

    private MultipartFile image;
}
