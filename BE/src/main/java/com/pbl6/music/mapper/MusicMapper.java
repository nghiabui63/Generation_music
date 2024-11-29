package com.pbl6.music.mapper;

import com.pbl6.music.dto.response.MusicResponseDTO;
import com.pbl6.music.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicMapper {
    @Mapping(source = "composer.fullName", target = "composerFullName")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "composer.id", target = "composerId")
    @Mapping(source = "category.id", target = "categoryId")
    MusicResponseDTO toResponseDTO(Music music);

    Music toEntity(MusicResponseDTO musicDTO);
}
