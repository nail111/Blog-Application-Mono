package org.example.service;

import org.example.dto.category.CategoryRequest;
import org.example.dto.category.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
