package com.pbl6.music.service;

import com.pbl6.music.dto.response.CategoryResponeDTO;
import com.pbl6.music.dto.response.PageResponse;

import java.util.Optional;


public interface ICategoryService {
    PageResponse<?> getAllCategory(int page, int pageSize);
    CategoryResponeDTO findByCategoryId(Long categoryId);
}
