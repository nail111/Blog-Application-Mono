package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PostRequest;
import org.example.dto.PostResponse;
import org.example.entity.Post;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<PostResponse> getAllPosts() {
        log.info("getting all posts");
        return postRepository.findAll().stream().map(PostMapper.MAPPER::toDto).toList();
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = getPost(id);
        return PostMapper.MAPPER.toDto(post);
    }

    @Override
    public PostResponse updatePostById(Long id, PostRequest postRequest) {
        Post post = getPost(id);
        log.info("copying properties...");
        BeanUtils.copyProperties(postRequest, post);
        log.info("done: {}", post);
        log.info("saving updated post");
        Post savedPost = postRepository.save(post);
        log.info("updated post saved: {}", savedPost);
        return PostMapper.MAPPER.toDto(savedPost);
    }

    private Post getPost(Long id) {
        log.info("getting a post with id: {}", id);
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        log.info("post with id: {} is found - {}", id, post);
        return post;
    }

    @Override
    public String deletePostById(Long id) {
        Post post = getPost(id);
        log.info("deleting post: {}", post);
        postRepository.delete(post);
        log.info("Post: {} is deleted", post);
        return "Post: " + post.getTitle() + " is deleted!!!";
    }
}