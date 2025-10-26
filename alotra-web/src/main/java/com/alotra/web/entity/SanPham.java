package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SanPham")
public class SanPham {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSP")
    private Integer maSP;
    
    @Column(name = "MaDM", nullable = false)
    private Integer maDM;
    
    @Column(name = "TenSP", nullable = false, length = 255)
    private String tenSP;
    
    @Column(name = "MoTa", length = 255)
    private String moTa;
    
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive
    
    @Column(name = "UrlAnh", length = 255)
    private String urlAnh;
    
    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDM", insertable = false, updatable = false)
    private DanhMucSanPham danhMuc;
    
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BienTheSanPham> bienThes;
    
    // Helper methods
    public boolean isActive() {
        return trangThai != null && trangThai == 1 && deletedAt == null;
    }
    
    public void setActive(boolean active) {
        this.trangThai = active ? (byte) 1 : (byte) 0;
    }
    
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.trangThai = (byte) 0;
    }
}