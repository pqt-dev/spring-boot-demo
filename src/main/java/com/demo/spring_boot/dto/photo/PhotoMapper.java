package com.demo.spring_boot.dto.photo;

import com.demo.spring_boot.entity.photo.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    @Mapping(target = "userId", source = "author.id")
    PhotoResponse toResponse(Photo photo);
}
