package com.pbl6.music.service.impl;

import com.pbl6.music.dto.response.MusicResponseDTO;
import com.pbl6.music.dto.response.PageResponse;
import com.pbl6.music.entity.Music;
import com.pbl6.music.entity.UserEntity;
import com.pbl6.music.mapper.MusicMapper;
import com.pbl6.music.repository.MusicRepository;
import com.pbl6.music.service.MusicService;
import com.pbl6.music.util.AppException;
import com.pbl6.music.util.ErrorCode;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MusicServiceImpl implements MusicService {

     MusicRepository musicRepository;


     MusicMapper musicMapper;
    @Override
    public PageResponse<?> getAll(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<MusicResponseDTO> musics = musicRepository.findAllMusic(pageable);

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(musics.getTotalPages())
                .items(musics.getContent())
                .build();
    }

    @Override
    public MusicResponseDTO getById(Long id) {
        Music music = musicRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MUSIC_NOT_FOUND));
       return musicMapper.toResponseDTO(music);
    }
}
