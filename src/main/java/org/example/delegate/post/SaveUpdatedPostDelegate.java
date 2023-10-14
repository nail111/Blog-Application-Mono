package org.example.delegate.post;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Post;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component("saveUpdatedPostDelegate")
@RequiredArgsConstructor
public class SaveUpdatedPostDelegate implements JavaDelegate {
    private final PostRepository postRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Post post = (Post) delegateExecution.getVariable("post");

        postRepository.save(post);
        delegateExecution.setVariable("isUpdated", true);
    }
}
