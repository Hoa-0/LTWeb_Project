package com.alotra.web.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

>>>>>>> e66511f5f76c02a6c7a92993afb90a3d8655037c
import java.time.LocalDateTime;

@Entity
@Table(name = "NhanVien")
<<<<<<< HEAD
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private Integer id;
 
    @NotBlank
    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "MatKhauHash", nullable = false)
    private String passwordHash;

    @NotBlank
    @Email
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "TenNV", nullable = false)
    private String fullName;

    // 1: Chủ cửa hàng, 2: Nhân viên
    @Column(name = "VaiTro", nullable = false)
    private Integer role; 

    @Column(name = "SoDienThoai", unique = true)
    private String phone;

    // 0: Khóa, 1: Hoạt động
    @Column(name = "TrangThai", nullable = false)
    private Integer status = 1;

    // Soft delete timestamp (null = active)
    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    @Transient
    private String plainPassword; // for form input only

    @Transient
    private String confirmPassword; // for form input only

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getPlainPassword() { return plainPassword; }
    public void setPlainPassword(String plainPassword) { this.plainPassword = plainPassword; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
=======
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
>>>>>>> e66511f5f76c02a6c7a92993afb90a3d8655037c
}