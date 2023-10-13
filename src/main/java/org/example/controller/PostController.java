package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.camunda.ResponseOutput;
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
@Tag(
        name = "Post REST API"
)
@CrossOrigin(origins = "http://localhost:8080")
public class PostController {
    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 CREATED"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseOutput> createPost(@RequestBody @Valid PostRequest postRequest) {
        return postService.createPost(postRequest);
    }

    @GetMapping
    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST API is used to get all posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    public ResponseEntity<PostPageResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = "0", name = "pageNo") int pageNo,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(required = false, defaultValue = "id", name = "sortBy") String sortBy,
            @RequestParam(required = false, defaultValue = "asc", name = "sortDir") String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{postId}")
    @Operation(
            summary = "Get Post by ID REST API",
            description = "Get Post by ID REST API is used to get a single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update post in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    public ResponseEntity<PostResponse> updatePostById(@PathVariable("postId") Long id, @RequestBody @Valid PostRequest postRequest) {
        return ResponseEntity.ok(postService.updatePostById(id, postRequest));
    }

    @DeleteMapping("/{postId}")
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    @DeleteMapping("/delete-all-posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Delete All Posts REST API",
            description = "Delete All Posts REST API is used to delete all posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    public ResponseEntity<String> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.ok("All data in table: [posts] is deleted!!!");
    }
}