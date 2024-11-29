package com.pbl6.music.controller;

import com.pbl6.music.util.ErrorCode;
import com.pbl6.music.service.ICategoryService;
import com.pbl6.music.util.ResponseData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryController {

    ICategoryService categoryService;

    @GetMapping
    public ResponseData<?> getAllCategory(@RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .data(categoryService.getAllCategory(page, pageSize))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<?> getCategoryById(@PathVariable Long id) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .data(categoryService.findByCategoryId(id))
                .build();
    }

}
