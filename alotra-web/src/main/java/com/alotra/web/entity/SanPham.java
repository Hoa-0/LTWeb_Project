package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "SanPham")
@EqualsAndHashCode(exclude = {"bienTheSanPhams", "khuyenMaiSanPhams"})
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maSP")
    private Integer maSP;

    @Column(name = "tenSP", nullable = false, length = 255)
    private String tenSP;

    @Column(name = "moTa", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "giaSP", precision = 10, scale = 2)
    private BigDecimal giaSP;

    @Column(name = "hinhAnh", length = 500)
    private String hinhAnh;

    @Column(name = "trangThai")
    private Byte trangThai = 1; // 1: Đang bán, 0: Ngừng bán

    @Column(name = "maDM")
    private Integer maDM;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BienTheSanPham> bienTheSanPhams;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<KhuyenMaiSanPham> khuyenMaiSanPhams;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Compatibility methods for service layer
    public String getUrlAnh() {
        return hinhAnh;
    }
    
    public void setUrlAnh(String urlAnh) {
        this.hinhAnh = urlAnh;
    }
    
    public DanhMucSanPham getDanhMuc() {
        // This should be implemented with proper relationship
        return null; // Placeholder
    }
    
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.trangThai = 0;
    }
}