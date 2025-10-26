package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "KhachHang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;

    @Column(name = "TenKH", nullable = false, length = 100)
    private String tenKH;

    @Column(name = "Email", unique = true, length = 100)
    private String email;

    @Column(name = "SoDienThoai", unique = true, length = 20)
    private String soDienThoai;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "TenDangNhap", unique = true, length = 50)
    private String tenDangNhap;

    @Column(name = "MatKhau", length = 255)
    private String matKhau;

    @Column(name = "TrangThai")
    private Integer trangThai = 1; // 1: Active, 0: Inactive

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NgayCapNhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "AnhDaiDien", length = 500)
    private String anhDaiDien;

    // Constructor cho việc tìm kiếm (cho KhachHangService.search)
    public KhachHang() {}

    // Convenience methods
    public String getUsername() {
        return tenDangNhap;
    }

    public void setUsername(String username) {
        this.tenDangNhap = username;
    }

    public String getPhone() {
        return soDienThoai;
    }

    public void setPhone(String phone) {
        this.soDienThoai = phone;
    }

    public Integer getId() {
        return maKH;
    }

    public String getFullName() {
        return tenKH;
    }

    public void setFullName(String fullName) {
        this.tenKH = fullName;
    }

    public String getPasswordHash() {
        return matKhau;
    }

    public void setPasswordHash(String passwordHash) {
        this.matKhau = passwordHash;
    }

    public Integer getStatus() {
        return trangThai;
    }

    public void setStatus(Integer status) {
        this.trangThai = status;
    }

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
        ngayCapNhat = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }
}