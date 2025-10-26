package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BienTheSanPham")
@EqualsAndHashCode(exclude = {"sanPham"})
public class BienTheSanPham {
    // Add getter for SizeSanPham for compatibility
    public SizeSanPham getSizeSanPham() {
        // No field, but can be loaded via maSize if needed
        return null;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maBT")
    private Integer maBT;

    @Column(name = "maSP")
    private Integer maSP;

    @Column(name = "maSize")
    private Integer maSize;

    @Column(name = "maTopping")
    private Integer maTopping;

    @Column(name = "giaBT", precision = 10, scale = 2)
    private BigDecimal giaBT;

    @Column(name = "soLuongTon")
    private Integer soLuongTon = 0;

    @Column(name = "trangThai")
    private Byte trangThai = 1; // 1: Đang bán, 0: Ngừng bán

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSP", insertable = false, updatable = false)
    private SanPham sanPham;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTopping", insertable = false, updatable = false)
    private Topping topping;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Compatibility methods
    public SanPham getProduct() {
        return sanPham;
    }

    public Integer getVariantId() {
        return maBT;
    }
    
    public Integer getId() {
        return maBT;
    }
    
    public void setId(Integer id) {
        this.maBT = id;
    }
    
    public BigDecimal getPrice() {
        return giaBT;
    }
    
    public void setPrice(BigDecimal price) {
        this.giaBT = price;
    }
    
    public Integer getQuantity() {
        return soLuongTon;
    }
    
    public void setQuantity(Integer quantity) {
        this.soLuongTon = quantity;
    }
    
    public BigDecimal getGiaBan() {
        return giaBT;
    }
    
    public void setGiaBan(BigDecimal giaBan) {
        this.giaBT = giaBan;
    }

    public String getSizeName() {
    // sizeSanPham field removed; update logic if needed
    return null;
    }
}