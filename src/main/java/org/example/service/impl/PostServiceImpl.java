package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.comment.CommentResponse;
import org.example.dto.post.PostPageResponse;
import org.example.dto.post.PostRequest;
import org.example.dto.post.PostResponse;
import org.example.entity.Category;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.CategoryRepository;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.service.CommentService;
import org.example.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);

        Category category = categoryRepository
                .findById(postRequest.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("category", "id", String.valueOf(postRequest.getCategoryId())));

        post.setCategory(category);

        postRepository.save(post);

        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);

        postResponse.setCategoryId(post.getCategory().getId());
        return postResponse;
    }

    @Override
    public PostPageResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> allPosts = postRepository.findAll(pageable);

        PostPageResponse postPageResponse = new PostPageResponse();
        List<Post> posts = allPosts.getContent();
        List<PostResponse> postResponseList = new ArrayList<>();

        posts.forEach(post -> {
            PostResponse postResponse = new PostResponse();
            List<CommentResponse> allCommentsByPostId = commentService.getAllCommentsByPostId(post.getId());
            Set<CommentResponse> commentResponseSet = new HashSet<>(allCommentsByPostId);

            BeanUtils.copyProperties(post, postResponse, "comments");
            if (!commentResponseSet.isEmpty()) {
                postResponse.setComments(commentResponseSet);
            }

            postResponse.setCategoryId(
                    Optional.ofNullable(post.getCategory())
                            .map(Category::getId)
                            .orElse(null)
            );

            postResponseList.add(postResponse);
        });

        postPageResponse.setContent(postResponseList);
        postPageResponse.setPageNo(allPosts.getNumber());
        postPageResponse.setPageSize(allPosts.getSize());
        postPageResponse.setTotalElements(allPosts.getTotalElements());
        postPageResponse.setTotalPages(allPosts.getTotalPages());
        postPageResponse.setLast(allPosts.isLast());

        return postPageResponse;
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = getPost(id);
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse, "comments");
        List<CommentResponse> allCommentsByPostId = commentService.getAllCommentsByPostId(post.getId());
        Set<CommentResponse> commentResponseSet = new HashSet<>(allCommentsByPostId);

        if (!commentResponseSet.isEmpty()) {
            postResponse.setComments(commentResponseSet);
        }

        postResponse.setCategoryId(
                Optional.ofNullable(post.getCategory())
                        .map(Category::getId)
                        .orElse(null)
        );

        return postResponse;
    }

    @Override
    public PostResponse updatePostById(Long id, PostRequest postRequest) {
        Post post = getPost(id);

        BeanUtils.copyProperties(postRequest, post);

        Category category = categoryRepository
                .findById(postRequest.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("category", "id", String.valueOf(postRequest.getCategoryId())));

        post.setCategory(category);

        Post savedPost = postRepository.save(post);

        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(savedPost, postResponse);

        List<Comment> allCommentsByPostId = commentRepository.findAllCommentsByPostId(post.getId());
        Set<CommentResponse> commentResponseList = new HashSet<>();

        allCommentsByPostId.forEach(comment -> {
            CommentResponse commentResponse = new CommentResponse();
            BeanUtils.copyProperties(comment, commentResponse);
            commentResponse.setPostId(post.getId());
            commentResponseList.add(commentResponse);
        });

        if (!commentResponseList.isEmpty()) {
            postResponse.setComments(commentResponseList);
        }

        postResponse.setCategoryId(category.getId());

        return postResponse;
    }

    @Override
    public String deletePostById(Long id) {
        Post post = getPost(id);
        postRepository.delete(post);
        return "Post: " + post.getTitle() + " is deleted!!!";
    }

    @Override
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    private Post getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        return post;
    }
}