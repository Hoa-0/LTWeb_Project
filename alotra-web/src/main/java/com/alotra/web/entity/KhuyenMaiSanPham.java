package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhuyenMaiSanPham")
@IdClass(KhuyenMaiSanPhamId.class)
public class KhuyenMaiSanPham {

    @Id
    @Column(name = "MaKM")
    private Integer maKM;

    @Id
    @Column(name = "MaSP")
    private Integer maSP;

    @Column(name = "PhanTramGiam", nullable = false)
    private Integer phanTramGiam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKM", insertable = false, updatable = false)
    private SuKienKhuyenMai suKien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private SanPham sanPham;

    // Compatibility methods
    public void setPromotion(SuKienKhuyenMai promotion) {
        this.suKien = promotion;
        if (promotion != null) {
            this.maKM = promotion.getMaKM();
        }
    }

    public void setProduct(Product product) {
        // Note: Product is mapped to SanPham - need conversion
        if (product != null) {
            this.maSP = product.getId().intValue();
        }
    }

    public void setId(KhuyenMaiSanPhamId id) {
        if (id != null) {
            this.maKM = id.getMaKM();
            this.maSP = id.getMaSP();
        }
    }

    public void setDiscountPercent(Integer percent) {
        this.phanTramGiam = percent;
    }

    public Product getProduct() {
        // This would need to be loaded separately or via mapping
        return null; // Placeholder - will be handled by service layer
    }
}
