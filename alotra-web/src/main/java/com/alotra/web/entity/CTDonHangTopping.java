package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CTDonHang_Topping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CTDonHangTopping {

    @EmbeddedId
    private CTDonHangToppingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maCT")
    @JoinColumn(name = "MaCT", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CTDonHang chiTietDonHang;

    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Column(name = "DonGia", precision = 10, scale = 2, nullable = false)
    private BigDecimal donGia;

    @Column(name = "ThanhTien", precision = 10, scale = 2, nullable = false)
    private BigDecimal thanhTien;

    // Compatibility methods
    public Integer getQuantity() {
        return soLuong;
    }

    public BigDecimal getUnitPrice() {
        return donGia;
    }

    public BigDecimal getLineTotal() {
        return thanhTien;
    }

    public Topping getTopping() {
        // This would need to be loaded separately or via @ManyToOne
        return null; // Placeholder - will be handled by service layer
    }
}
