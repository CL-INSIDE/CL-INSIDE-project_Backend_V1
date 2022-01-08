package com.example.clinside.domain.post.service;

import com.example.clinside.domain.auth.facade.UserFacade;
import com.example.clinside.domain.image.service.ImageService;
import com.example.clinside.domain.post.domain.Post;
import com.example.clinside.domain.post.presentation.dto.request.PostRequest;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostRepository postRepository;
    private final ImageService uploadFileService;

    public void execute(PostRequest request, MultipartFile image) {
        Post post = postRepository.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(UserFacade.getUser())
                .likeCounts(0)
                .hateCounts(0)
                .build());

        uploadFileService.addImage(image, post);
    }
}
