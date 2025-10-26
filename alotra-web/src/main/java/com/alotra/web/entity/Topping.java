package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Topping")
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTopping")
    private Integer maTopping;

    @Column(name = "TenTopping", nullable = false, length = 255)
    private String tenTopping;

    @Column(name = "GiaThem", nullable = false, precision = 10, scale = 2)
    private BigDecimal giaThem;

    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive

    @Column(name = "UrlAnh", length = 255)
    private String urlAnh;

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    public boolean isActive() {
        return trangThai != null && trangThai == 1 && deletedAt == null;
    }
}
