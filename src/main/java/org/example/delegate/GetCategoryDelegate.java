package org.example.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.dto.post.PostRequest;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("getCategoryDelegate")
@RequiredArgsConstructor
public class GetCategoryDelegate implements JavaDelegate {
    private final CategoryRepository categoryRepository;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PostRequest postRequest = (PostRequest) delegateExecution.getVariable("postRequest");

        Category category = getCategoryById(postRequest.getCategoryId());

        delegateExecution.setVariable("category", category);
    }

    private Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.orElse(null);
    }
}