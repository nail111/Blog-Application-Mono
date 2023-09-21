package org.example.mapper;

import org.example.dto.post.PostRequest;
import org.example.dto.post.PostResponse;
import org.example.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper MAPPER = Mappers.getMapper(PostMapper.class);

    Post toEntity(PostRequest postRequest);

    PostResponse toDto(Post post);
}