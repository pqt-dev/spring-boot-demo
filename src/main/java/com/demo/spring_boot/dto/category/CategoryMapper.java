package com.demo.spring_boot.dto.category;

import com.demo.spring_boot.entity.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
}
