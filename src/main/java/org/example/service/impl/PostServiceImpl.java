package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PostRequest;
import org.example.dto.PostResponse;
import org.example.entity.Post;
import org.example.mapper.PostMapper;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        log.info("Post sent from the client: {}", postRequest);

        log.info("Trying to map PostRequest: {} to Post", postRequest);
        Post post = PostMapper.MAPPER.toEntity(postRequest);
        log.info("Mapped successfully: {}", post);

        log.info("Setting creation date and update date");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        log.info("Setted successfully: {}", post);

        log.info("Trying to save new post: {}", post);
        Post savedPost = postRepository.save(post);
        log.info("Post: {} is saved", savedPost);

        log.info("Trying to map Post: {} to PostResponse", savedPost);
        PostResponse postResponse = PostMapper.MAPPER.toDto(savedPost);
        log.info("Mapped successfully: {}", postResponse);

        return postResponse;
    }
}