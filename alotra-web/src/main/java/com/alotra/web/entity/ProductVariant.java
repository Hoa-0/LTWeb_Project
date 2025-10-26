package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ProductVariants")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Compatibility methods
    public Integer getStatus() {
        return isActive ? 1 : 0;
    }

    public void setStatus(int status) {
        this.isActive = status == 1;
    }

    public void setStatus(Integer status) {
        this.isActive = status != null && status == 1;
    }

    public void setSize(SizeSanPham sizeSanPham) {
        if (sizeSanPham != null) {
            this.size = sizeSanPham.getTenSize();
        }
    }
}