package com.example.clinside.domain.post.service;

import com.example.clinside.domain.image.service.ImageService;
import com.example.clinside.domain.post.domain.Post;
import com.example.clinside.domain.post.presentation.dto.request.PostRequest;
import com.example.clinside.domain.post.exception.PostNotFoundException;
import com.example.clinside.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class ModifyPostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    @Transactional
    public void execute(Integer id, PostRequest request, MultipartFile image) {

        Post post = postRepository.findById(id)
                .map(newPost -> newPost.updatePost(
                        request.getTitle(),
                        request.getContent()
                ))
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);

        if (image != null) {
            imageService.modifyImage(image, post);
        }
    }
}
