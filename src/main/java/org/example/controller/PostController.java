package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PostRequest;
import org.example.dto.PostResponse;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest postRequest) {
        return new ResponseEntity<>(postService.createPost(postRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable("postId") Long id, @RequestBody @Valid PostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePostById(id, postRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long id)  {
        return ResponseEntity.ok(postService.deletePostById(id));
    }
}