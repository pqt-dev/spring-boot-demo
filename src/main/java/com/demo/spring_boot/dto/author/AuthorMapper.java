package com.demo.spring_boot.dto.author;

import com.demo.spring_boot.entity.author.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse toResponse(Author author);
}
