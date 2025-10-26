package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CTDonHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CTDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCT")
    private Integer maCT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDH", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private DonHang donHang;

    @Column(name = "MaBT", nullable = false)
    private Integer maBT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaBT", insertable = false, updatable = false)
    private BienTheSanPham bienTheSanPham;

    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Column(name = "DonGia", precision = 10, scale = 2, nullable = false)
    private BigDecimal donGia;

    @Column(name = "GiamGiaDong", precision = 10, scale = 2, nullable = false)
    private BigDecimal giamGiaDong;

    @Column(name = "ThanhTien", precision = 10, scale = 2, nullable = false)
    private BigDecimal thanhTien;

    @Column(name = "GhiChu", length = 500)
    private String ghiChu;

    // Compatibility methods

    public Integer getQuantity() {
        return soLuong;
    }

    public BigDecimal getUnitPrice() {
        return donGia;
    }

    public BigDecimal getLineDiscount() {
        return giamGiaDong != null ? giamGiaDong : BigDecimal.ZERO;
    }

    public BigDecimal getLineTotal() {
        return thanhTien;
    }

    public String getNote() {
        return ghiChu;
    }

    public DonHang getOrder() {
        return donHang;
    }
    public BienTheSanPham getVariant() {
        return bienTheSanPham;
    }
    public Integer getId() {
        return maCT;
    }
}
