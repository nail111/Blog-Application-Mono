package org.example.service;

import org.example.dto.PostRequest;
import org.example.dto.PostResponse;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);
}