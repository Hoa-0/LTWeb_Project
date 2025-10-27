package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhachHang") // Map đúng tên bảng
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;

    @Column(name = "TenKH", nullable = false, length = 255)
    private String tenKH;

    @Column(name = "Email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "SoDienThoai", nullable = false)
    private String soDienThoai;
    
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive

    // Helper method (giữ lại từ bản gốc nếu có)
    public boolean isActive() {
        return trangThai != null && trangThai == 1;
    }
}