package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DonHang") // Map đúng tên bảng trong DB
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDH")
    private Integer maDH;

    @Column(name = "NgayLap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "TongThanhToan", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongThanhToan;

    @Column(name = "TrangThaiDonHang", nullable = false, length = 30)
    private String trangThaiDonHang; // Ví dụ: "ChoXuLy", "DaGiao"...
    
    @Column(name = "PaymentStatus", nullable = false, length = 30)
    private String paymentStatus; // "ChuaThanhToan", "DaThanhToan"

    // Thêm các cột khác nếu cần cho các service khác sau này
    // Ví dụ: liên kết đến KhachHang
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "MaKH", nullable = false)
    // private KhachHang khachHang;
}