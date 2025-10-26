package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "DonHang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDH")
    private Integer maDH;

    @Column(name = "MaKH", nullable = false)
    private Integer maKH;

    @Column(name = "MaNV")
    private Integer maNV;

    @Column(name = "MaKM")
    private Integer maKM;

    @Column(name = "NgayLap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "TrangThaiDonHang", nullable = false, length = 30)
    private String trangThaiDonHang;

    @Column(name = "PaymentStatus", nullable = false, length = 30)
    private String paymentStatus;

    @Column(name = "PaymentMethod", length = 50)
    private String paymentMethod;

    @Column(name = "PaidAt")
    private LocalDateTime paidAt;

    @Column(name = "TongHang", precision = 12, scale = 2, nullable = false)
    private BigDecimal tongHang;

    @Column(name = "GiamGiaDon", precision = 12, scale = 2, nullable = false)
    private BigDecimal giamGiaDon;

    @Column(name = "PhiVanChuyen", precision = 12, scale = 2, nullable = false)
    private BigDecimal phiVanChuyen;

    @Column(name = "TongThanhToan", precision = 12, scale = 2, nullable = false)
    private BigDecimal tongThanhToan;

    @Column(name = "GhiChu", length = 500)
    private String ghiChu;

    @Column(name = "PhuongThucNhanHang", length = 50)
    private String phuongThucNhanHang;

    @Column(name = "DiaChiNhanHang")
    private String diaChiNhanHang;

    @Column(name = "TenNguoiNhan", length = 50)
    private String tenNguoiNhan;

    @Column(name = "SDTNguoiNhan", length = 10)
    private String sdtNguoiNhan;
}
