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
@Table(name = "CTDonHang")
public class CTDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCT")
    private Integer maCT;

    @Column(name = "MaDH", nullable = false)
    private Integer maDH;

    @Column(name = "MaBT", nullable = false)
    private Integer maBT;

    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Column(name = "DonGia", nullable = false, precision = 10, scale = 2)
    private BigDecimal donGia;

    @Column(name = "GiamGiaDong", nullable = false, precision = 10, scale = 2)
    private BigDecimal giamGiaDong;

    @Column(name = "ThanhTien", nullable = false, precision = 10, scale = 2)
    private BigDecimal thanhTien; // Đây là cột chúng ta sẽ SUM để tính doanh thu SP

    @Column(name = "GhiChu", length = 500)
    private String ghiChu;

    // === Relationships ===

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDH", insertable = false, updatable = false)
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaBT", insertable = false, updatable = false)
    private BienTheSanPham bienTheSanPham;
}