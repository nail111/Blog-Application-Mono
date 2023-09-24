package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.comment.CommentRequest;
import org.example.dto.comment.CommentResponse;
import org.example.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody @Valid CommentRequest commentRequest,
            @PathVariable("postId") Long postId
    ) {
        return new ResponseEntity<>(commentService.createComment(commentRequest, postId), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponse> getCommentByCommentId(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentByCommentId(commentId));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponse> updateCommentByCommentId(
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(commentService.updateCommentByCommentId(commentId, commentRequest));
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<String> deleteCommentByCommentId(
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(commentService.deleteCommentByCommentId(commentId));
    }
}