package com.pbl6.music.service.impl;

import com.pbl6.music.dto.response.CategoryResponeDTO;
import com.pbl6.music.entity.Category;
import com.pbl6.music.util.AppException;
import com.pbl6.music.util.ErrorCode;
import com.pbl6.music.mapper.CategoryMapper;
import com.pbl6.music.repository.CategoryRepository;
import com.pbl6.music.service.ICategoryService;
import com.pbl6.music.dto.response.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Get all categories and map to DTOs
     public PageResponse<?> getAllCategory(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<CategoryResponeDTO> categories = categoryRepository.findAllCategory(pageable);

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(categories.getTotalPages())
                .items(categories.getContent())
                .build();
    }

    // Find a category by its ID
    public CategoryResponeDTO findByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)); // Assuming ApiException exists
        return categoryMapper.toCategoryResponseDTO(category);
    }

}

