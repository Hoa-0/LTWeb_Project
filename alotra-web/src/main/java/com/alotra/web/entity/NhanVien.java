package com.alotra.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NhanVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private Integer maNV;
 
    @NotBlank
    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "MatKhauHash", nullable = false)
    private String matKhauHash;

    @NotBlank
    @Email
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "TenNV", nullable = false)
    private String tenNV;

    // 1: Chủ cửa hàng, 2: Nhân viên
    @Column(name = "VaiTro", nullable = false)
    private Byte vaiTro; 

    @Column(name = "SoDienThoai", unique = true)
    private String soDienThoai;

    // 0: Khóa, 1: Hoạt động
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai = 1;

    @Column(name = "AnhDaiDien", length = 500)
    private String anhDaiDien;

    // Soft delete timestamp (null = active)
    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    @Transient
    private String plainPassword; // for form input only

    @Transient
    private String confirmPassword; // for form input only
    
    // Helper methods for role checking
    public boolean isAdmin() {
        return vaiTro != null && vaiTro == 1;
    }
    
    public boolean isManager() {
        return vaiTro != null && vaiTro == 2;
    }
    
    public boolean isStaff() {
        return vaiTro != null && vaiTro == 3;
    }
    
    public boolean isActive() {
        return trangThai != null && trangThai == 1 && deletedAt == null;
    }
    
    public String getVaiTroText() {
        if (vaiTro == null) return "Unknown";
        return switch (vaiTro) {
            case 1 -> "Quản trị viên";
            case 2 -> "Quản lý";
            case 3 -> "Nhân viên";
            default -> "Unknown";
        };
    }
}