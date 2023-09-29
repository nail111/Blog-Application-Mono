package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.comment.CommentRequest;
import org.example.dto.comment.CommentResponse;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest, Long postId) {
        Post post = getPostIfExistsById(postId);

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentRequest, comment);

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(savedComment, commentResponse);
        commentResponse.setPostId(post.getId());

        return commentResponse;
    } // fixed

    @Override
    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        Post post = getPostIfExistsById(postId);

        List<Comment> comments = commentRepository.findByPost(post);
        List<CommentResponse> commentResponseList = new ArrayList<>();

        comments.forEach(comment -> {
            CommentResponse commentResponse = new CommentResponse();
            BeanUtils.copyProperties(comment, commentResponse);
            commentResponse.setPostId(post.getId());
            commentResponseList.add(commentResponse);
        });

        return commentResponseList;
    }

    @Override
    public CommentResponse getCommentByCommentId(Long commentId) {
        Comment comment = getCommentIfExistsById(commentId);

        CommentResponse commentResponse = new CommentResponse();

        BeanUtils.copyProperties(comment, commentResponse);
        commentResponse.setPostId(comment.getPost().getId());

        return commentResponse;
    }

    @Override
    public CommentResponse updateCommentByCommentId(Long commentId, CommentRequest commentRequest) {
        Comment comment = getCommentIfExistsById(commentId);

        BeanUtils.copyProperties(commentRequest, comment, "id", "post");
        commentRepository.save(comment);

        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(comment, commentResponse);
        commentResponse.setPostId(comment.getPost().getId());

        return commentResponse;
    }

    @Override
    public String deleteCommentByCommentId(Long commentId) {
        Comment comment = getCommentIfExistsById(commentId);

        commentRepository.delete(comment);
        return "Comment: " + comment + " is deleted!!!";
    }

    private Post getPostIfExistsById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(postId)));
        return post;
    }

    private Comment getCommentIfExistsById(Long commentId) {
        log.info("getting comment by comment_id: {}", commentId);
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", String.valueOf(commentId)));
        log.info("comment found: {}", comment);
        return comment;
    }
}