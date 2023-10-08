package org.example.service;

import org.example.dto.category.CategoryRequest;
import org.example.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategoryById(Long id, CategoryRequest categoryRequest);

    void deleteCategoryById(Long id);

    void deleteAllCategories();
}