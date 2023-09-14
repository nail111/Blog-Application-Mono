package org.example.mapper;

import org.example.dto.PostRequest;
import org.example.dto.PostResponse;
import org.example.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper MAPPER = Mappers.getMapper(PostMapper.class);

    Post toEntity(PostRequest postRequest);

    PostResponse toDto(Post post);
}