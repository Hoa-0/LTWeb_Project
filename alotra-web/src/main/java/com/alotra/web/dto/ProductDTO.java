package com.alotra.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private boolean isActive;
    private String categoryName;
    private String vendorName;
    private List<String> mediaUrls;
    
    // Additional fields for compatibility
    private BigDecimal originalPrice;
    private Integer discountPercent;
    
    // Constructor for service usage
    public ProductDTO(Long id, String name, String imageUrl, BigDecimal finalPrice) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = finalPrice != null ? finalPrice.doubleValue() : 0.0;
    }
    
    // Getters/Setters for compatibility
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
}