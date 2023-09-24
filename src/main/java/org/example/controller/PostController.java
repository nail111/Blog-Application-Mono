package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.post.PostPageResponse;
import org.example.dto.post.PostRequest;
import org.example.dto.post.PostResponse;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest postRequest) {
        return new ResponseEntity<>(postService.createPost(postRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(required = false, defaultValue = "id", name = "sortBy") String sortBy,
            @RequestParam(required = false, defaultValue = "asc", name = "sortDir") String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable("postId") Long id, @RequestBody @Valid PostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePostById(id, postRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    @DeleteMapping("/delete-all-posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.ok("All data in table: [posts] is deleted!!!");
    }
}