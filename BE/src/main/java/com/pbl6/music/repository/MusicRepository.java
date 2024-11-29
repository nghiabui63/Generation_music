package com.pbl6.music.repository;

import com.pbl6.music.dto.response.MusicResponseDTO;
import com.pbl6.music.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    @Query("""
        select new com.pbl6.music.dto.response.MusicResponseDTO(  
            m.id, 
            m.title, 
            m.demoUrl, 
            m.fullUrl, 
            m.price, 
            m.isApproved, 
            m.isPurchased, 
            m.imageUrl,
            m.composer.id, 
            m.composer.fullName, 
            m.category.id, 
            m.category.name)
        from Music m where m.isPurchased = false and m.isApproved=true
        """)
    Page<MusicResponseDTO> findAllMusic(Pageable pageable);
}
