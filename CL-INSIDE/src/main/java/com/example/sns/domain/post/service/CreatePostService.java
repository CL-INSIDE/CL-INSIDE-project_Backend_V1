package com.example.sns.domain.post.service;

import com.example.sns.domain.auth.facade.UserFacade;
import com.example.sns.domain.image.service.ImageService;
import com.example.sns.domain.post.domain.Post;
import com.example.sns.domain.post.presentation.dto.request.PostRequest;
import com.example.sns.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostRepository postRepository;
    private final ImageService uploadFileService;

    public void execute(PostRequest request, MultipartFile image){
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
