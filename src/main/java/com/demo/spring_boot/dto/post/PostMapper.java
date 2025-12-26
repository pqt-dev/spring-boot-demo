package com.demo.spring_boot.dto.post;

import com.demo.spring_boot.entity.post.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toResponse(Post post);
}
