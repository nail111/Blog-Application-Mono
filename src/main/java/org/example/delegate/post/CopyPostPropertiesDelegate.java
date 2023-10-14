package org.example.delegate.post;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.dto.post.PostRequest;
import org.example.entity.Post;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("copyPostPropertiesDelegate")
public class CopyPostPropertiesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Post post = (Post) delegateExecution.getVariable("post");
        PostRequest postRequest = (PostRequest) delegateExecution.getVariable("postRequest");

        BeanUtils.copyProperties(postRequest, post, "categoryId");
        delegateExecution.setVariable("post", post);
    }
}