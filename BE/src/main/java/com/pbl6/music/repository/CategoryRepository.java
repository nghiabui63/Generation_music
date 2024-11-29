package com.pbl6.music.repository;

import com.pbl6.music.dto.response.CategoryResponeDTO;
import com.pbl6.music.entity.Category;
import com.pbl6.music.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Tìm Category theo ID
//    Optional<Category> findById(Long categoryId);
    // Lấy tất cả Category có phân trang
    @Query("""
        select new com.pbl6.music.dto.response.CategoryResponeDTO(
            c.id, 
            c.name)
        from Category c
        """)
    Page<CategoryResponeDTO> findAllCategory(Pageable pageable);

}
