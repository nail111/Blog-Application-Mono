package org.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.dto.post.PostRequest;
import org.example.entity.Post;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("createPostDelegate")
public class CreatePostDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PostRequest postRequest = (PostRequest) delegateExecution.getVariable("postRequest");

        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);

        delegateExecution.setVariable("post", post);
    }
}