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
}
