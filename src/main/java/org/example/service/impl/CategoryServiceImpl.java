package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.category.CategoryRequest;
import org.example.dto.category.CategoryResponse;
import org.example.dto.comment.CommentResponse;
import org.example.dto.post.PostResponse;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.CategoryRepository;
import org.example.repository.PostRepository;
import org.example.service.CategoryService;
import org.example.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryRequest, category);

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(savedCategory, categoryResponse, "posts");

        Set<PostResponse> postResponseSet = new HashSet<>();

        List<Post> postList = postRepository.findAll();

        postList.forEach(post -> {
            if (post.getCategory() != null && post.getCategory().getId().equals(savedCategory.getId())) {
                PostResponse postResponse = new PostResponse();
                BeanUtils.copyProperties(post, postResponse);
                postResponseSet.add(postResponse);
            }
        });

        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        List<Post> postList = postRepository.findAll();

        for (Category category : categoryList) {
            CategoryResponse categoryResponse = createCategoryResponse(category, postList);
            categoryResponseList.add(categoryResponse);
        }

        return categoryResponseList;
    }

    private CategoryResponse createCategoryResponse(Category category, List<Post> postList) {
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(category, categoryResponse, "posts");

        Set<PostResponse> postResponseSet = new HashSet<>();

        for (Post post : postList) {
            if (post.getCategory().getId().equals(category.getId())) {
                PostResponse postResponse = createPostResponse(post);
                postResponseSet.add(postResponse);
            }
        }

        if (!postResponseSet.isEmpty()) {
            categoryResponse.setPosts(postResponseSet);
        }

        return categoryResponse;
    }

    private PostResponse createPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse, "comments");

        Set<CommentResponse> commentResponseSet = new HashSet<>();
        List<CommentResponse> allCommentsByPostId = commentService.getAllCommentsByPostId(post.getId());

        for (CommentResponse comment : allCommentsByPostId) {
            CommentResponse commentResponse = new CommentResponse();
            BeanUtils.copyProperties(comment, commentResponse, "post", "postId");
            commentResponseSet.add(commentResponse);
        }

        postResponse.setComments(commentResponseSet);

        return postResponse;
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = getCategory(id);

        List<Post> postList = postRepository.findAll();


        CategoryResponse categoryResponse = createCategoryResponse(category, postList);
        return categoryResponse;
    }

    @Override
    public CategoryResponse updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category category = getCategory(id);

        BeanUtils.copyProperties(categoryRequest, category);
        categoryRepository.save(category);

        CategoryResponse categoryResponse = getCategoryById(category.getId());
        return categoryResponse;
    }

    private Category getCategory(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", String.valueOf(id)));

        return category;
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = getCategory(id);

        disassociateCategoryFromPosts(category);

        categoryRepository.delete(category);
    }

    private void disassociateCategoryFromPosts(Category category) {
        List<Post> postList = postRepository.findAllByCategory(category);
        postList.forEach(post -> post.setCategory(null));
        postRepository.saveAll(postList);
    }


    @Override
    public void deleteAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        categoryList.forEach(this::disassociateCategoryFromPosts);

        categoryRepository.deleteAll();
    }
}