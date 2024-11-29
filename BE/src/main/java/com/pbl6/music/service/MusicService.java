package com.pbl6.music.service;

import com.pbl6.music.dto.response.MusicResponseDTO;
import com.pbl6.music.dto.response.PageResponse;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicService {
    PageResponse<?> getAll(int page, int pageSize);

    MusicResponseDTO getById(Long id);
}
