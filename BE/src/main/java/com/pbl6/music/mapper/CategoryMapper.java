package com.pbl6.music.mapper;

import com.pbl6.music.dto.response.CategoryResponeDTO;
import com.pbl6.music.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "id", target = "categoryIdResponseDTO")
    @Mapping(source = "name", target = "categoryNameResponseDTO")
    CategoryResponeDTO toCategoryResponseDTO(Category category);
    Category toCategoryEntity(CategoryResponeDTO dto);
}
