package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

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
    
    private int status = 1; // 1: Active, 0: Inactive

    // Nhiều sản phẩm thuộc về một danh mục
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;
    
    // Timestamp fields
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;
    
    // Compatibility methods for service layer
    public String getSlug() {
        return name != null ? name.toLowerCase().replaceAll("[^a-z0-9]", "-") : "";
    }
    
    public Double getAverageRating() {
        // Default implementation - should be calculated from reviews
        return 0.0;
    }
    
    public Integer getReviewCount() {
        // Default implementation - should be calculated from reviews
        return 0;
    }
    
    public void setSlug(String slug) {
        // Implementation for slug setting if needed
    }
    
    public boolean getActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}