package org.example.delegate.post;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Category;
import org.example.entity.Post;
import org.springframework.stereotype.Component;

@Component("setCategoryIntoPostDelegate")
public class SetCategoryIntoPostDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Post post = (Post) delegateExecution.getVariable("post");
        Category category = (Category) delegateExecution.getVariable("category");

        post.setCategory(category);
    }
}