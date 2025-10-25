package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BienTheSanPham")
public class BienTheSanPham {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBT")
    private Integer maBT;
    
    @Column(name = "MaSP", nullable = false)
    private Integer maSP;
    
    @Column(name = "MaSize", nullable = false)
    private Integer maSize;
    
    @Column(name = "GiaBan", nullable = false, precision = 10, scale = 2)
    private BigDecimal giaBan;
    
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private SanPham sanPham;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSize", insertable = false, updatable = false)
    private SizeSanPham sizeSanPham;
    
    // Helper methods
    public boolean isActive() {
        return trangThai != null && trangThai == 1;
    }
    
    public void setActive(boolean active) {
        this.trangThai = active ? (byte) 1 : (byte) 0;
    }
}