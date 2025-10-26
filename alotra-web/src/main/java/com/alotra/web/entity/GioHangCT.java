package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "GioHangCT")
public class GioHangCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTGH") // Updated column name to match database
    private Integer maGHCT;

    @Column(name = "MaKH")
    private Integer maKH;

    @Column(name = "MaSP")
    private Integer maSP;

    @Column(name = "MaBT")
    private Integer maBT;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 10, scale = 2)
    private BigDecimal donGia;

    @Column(name = "NgayThem")
    private LocalDateTime ngayThem;

    @Column(name = "GhiChu", length = 500)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKH", insertable = false, updatable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSP", insertable = false, updatable = false)
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maBT", insertable = false, updatable = false)
    private BienTheSanPham bienTheSanPham;

    @PrePersist
    protected void onCreate() {
        ngayThem = LocalDateTime.now();
    }

    // Additional getters for compatibility
    public Integer getId() {
        return maGHCT;
    }

    // Variant getter - placeholder for compatibility
    public BienTheSanPham getVariant() {
        return bienTheSanPham;
    }
}