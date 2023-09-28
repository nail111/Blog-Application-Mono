package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.category.CategoryRequest;
import org.example.dto.category.CategoryResponse;
import org.example.dto.post.PostResponse;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.repository.CategoryRepository;
import org.example.repository.PostRepository;
import org.example.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();

        log.info("mapping: {} to {}", categoryRequest, category);
        BeanUtils.copyProperties(categoryRequest, category);
        log.info("mapped: {}", category);

        Category savedCategory = categoryRepository.save(category);
        log.info("category saved: {}", savedCategory);

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

        categoryResponse.setPosts(postResponseSet);
        return categoryResponse;
    }
}