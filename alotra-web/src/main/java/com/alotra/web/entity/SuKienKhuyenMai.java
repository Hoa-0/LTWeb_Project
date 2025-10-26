package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SuKienKhuyenMai")
public class SuKienKhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKM")
    private Integer maKM;

    @Column(name = "TenSuKien", nullable = false, length = 255)
    private String tenSuKien;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    @Column(name = "NgayBD", nullable = false)
    private LocalDate ngayBD;

    @Column(name = "NgayKT", nullable = false)
    private LocalDate ngayKT;

    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive

    @Column(name = "UrlAnh")
    private String urlAnh;

    @Column(name = "LuotXem")
    private Integer luotXem;

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    @Transient
    public boolean isExpired() {
        return ngayKT != null && ngayKT.isBefore(LocalDate.now());
    }

    @Transient
    public boolean isActiveNow() {
        LocalDate today = LocalDate.now();
        return (trangThai != null && trangThai == 1) && deletedAt == null && ngayBD != null && ngayKT != null
                && (!today.isBefore(ngayBD)) && (!today.isAfter(ngayKT));
    }
}
