package com.alotra.web.entity;

import jakarta.persistence.*;
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
    
    @Column(name = "Username", nullable = false, length = 50, unique = true)
    private String username;
    
    @Column(name = "MatKhauHash", nullable = false, length = 255)
    private String matKhauHash;
    
    @Column(name = "Email", nullable = false, length = 255, unique = true)
    private String email;
    
    @Column(name = "TenNV", nullable = false, length = 255)
    private String tenNV;
    
    @Column(name = "VaiTro", nullable = false)
    private Byte vaiTro; // 1: Admin, 2: Manager, 3: Staff
    
    @Column(name = "SoDienThoai", length = 20)
    private String soDienThoai;
    
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive
    
    @Column(name = "AnhDaiDien", length = 500)
    private String anhDaiDien;
    
    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;
    
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

    // Additional getter methods for compatibility
    public Integer getId() {
        return maNV;
    }

    public String getFullName() {
        return tenNV;
    }

    public String getPasswordHash() {
        return matKhauHash;
    }

    public Byte getStatus() {
        return trangThai;
    }

    public String getRole() {
        return getVaiTroText();
    }

    // Additional compatibility methods
    public void setId(Integer id) {
        this.maNV = id;
    }

    public void setPasswordHash(String passwordHash) {
        this.matKhauHash = passwordHash;
    }

    // Phone field compatibility methods
    public String getPhone() {
        return this.soDienThoai;
    }

    public void setPhone(String phone) {
        this.soDienThoai = phone;
    }

    // Transient fields for form handling
    @Transient
    private String plainPassword;

    @Transient
    private String confirmPassword;

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // Role and status setter methods
    public void setRole(int role) {
        this.vaiTro = (byte) role;
    }

    public void setStatus(int status) {
        this.trangThai = (byte) status;
    }
}