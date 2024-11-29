package com.pbl6.music.dto.response;

import com.pbl6.music.entity.Category;
import com.pbl6.music.entity.Purchase;
import com.pbl6.music.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MusicResponseDTO {
      Long id;
      String title;
      String demoUrl;
      String fullUrl;
      BigDecimal price;
      boolean isApproved;
      boolean isPurchased;
      String imageUrl;
      Long composerId; // Change this to Long
      String composerFullName; // Keep if needed
      Long categoryId; // Change this to Long
      String categoryName; // Keep if needed
}

