package org.example.delegate.post;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.entity.Post;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("getPostDelegate")
@RequiredArgsConstructor
public class GetPostDelegate implements JavaDelegate {
    private final PostRepository postRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long id = (Long) delegateExecution.getVariable("id");
        Post post = getPostById(id);
        delegateExecution.setVariable("post", post);
    }

    private Post getPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.orElse(null);
    }
}