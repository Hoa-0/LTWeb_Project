package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double price;

    private String imageUrl; // Sẽ chứa URL từ Cloudinary

    private boolean isActive = true; // Mặc định là đang bán

    // Nhiều sản phẩm thuộc về một danh mục
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;
}