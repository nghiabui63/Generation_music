package com.pbl6.music.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "composer_id")
    private UserEntity composer;

    private String demoUrl;
    private String fullUrl;

    private BigDecimal price;

    private boolean isApproved;
    private boolean isPurchased;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;  // Field for storing the image URL

    @OneToMany(mappedBy = "music")
    private Set<Purchase> purchases;
}
