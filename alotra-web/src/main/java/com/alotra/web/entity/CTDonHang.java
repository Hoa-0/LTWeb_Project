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
}
