package org.example.delegate.post;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Category;
import org.example.entity.Post;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component("savePostDelegate")
@RequiredArgsConstructor
public class SavePostDelegate implements JavaDelegate {
    private final PostRepository postRepository;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Post post = (Post) delegateExecution.getVariable("post");
        Category category = (Category) delegateExecution.getVariable("category");

        post.setCategory(category);

        postRepository.save(post);

        delegateExecution.setVariable("isCreated", true);
    }
}