package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.comment.CommentResponse;
import org.example.dto.post.PostPageResponse;
import org.example.dto.post.PostRequest;
import org.example.dto.post.PostResponse;
import org.example.entity.Post;
import org.example.exception.ResourceNotFoundException;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        log.info("Post sent from the client: {}", postRequest);

        log.info("Trying to map PostRequest: {} to Post", postRequest);
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);
        log.info("Mapped successfully: {}", post);

        log.info("Trying to save new post: {}", post);
        Post savedPost = postRepository.save(post);
        log.info("Post: {} is saved", savedPost);

        log.info("Trying to map Post: {} to PostResponse", savedPost);
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);
        log.info("Mapped successfully: {}", postResponse);

        return postResponse;
    }

    @Override
    public PostPageResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        log.info("getting all posts");
        Page<Post> allPosts = postRepository.findAll(pageable);

        PostPageResponse postPageResponse = new PostPageResponse();
        List<Post> posts = allPosts.getContent();
        List<PostResponse> postResponseList = new ArrayList<>();

        posts.forEach(post -> {
            PostResponse postResponse = new PostResponse();
            List<CommentResponse> allCommentsByPostId = commentService.getAllCommentsByPostId(post.getId());
            Set<CommentResponse> commentResponseSet = new HashSet<>(allCommentsByPostId);

            BeanUtils.copyProperties(post, postResponse, "comments");
            postResponse.setComments(commentResponseSet);
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
        postResponse.setComments(commentResponseSet);
        return postResponse;
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

        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(savedPost, postResponse);

        return postResponse;
    }

    @Override
    public String deletePostById(Long id) {
        Post post = getPost(id);
        log.info("deleting post: {}", post);
        postRepository.delete(post);
        log.info("Post: {} is deleted", post);
        return "Post: " + post.getTitle() + " is deleted!!!";
    }

    @Override
    public void deleteAllPosts() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    private Post getPost(Long id) {
        log.info("getting a post with id: {}", id);
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        log.info("post with id: {} is found - {}", id, post);
        return post;
    }
}