package org.example.service;

import org.example.dto.comment.CommentRequest;
import org.example.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest commentRequest, Long postId);

    List<CommentResponse> getAllCommentsByPostId(Long postId);

    CommentResponse getCommentByCommentId(Long commentId);

    CommentResponse updateCommentByCommentId(Long commentId, CommentRequest commentRequest);

    String deleteCommentByCommentId(Long commentId);
}