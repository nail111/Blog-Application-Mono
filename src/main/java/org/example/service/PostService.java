package org.example.service;

import org.example.dto.PostRequest;
import org.example.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);

    List<PostResponse> getAllPosts();

    PostResponse getPostById(Long id);

    PostResponse updatePostById(Long id, PostRequest postRequest);

    String deletePostById(Long id);
}